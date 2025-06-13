package com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.toolkit.SpringContentUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.pojo.LowCodeParam;
import com.joyintech.yuntai.framework.security.core.LoginUser;
import com.joyintech.yuntai.framework.security.core.util.SecurityFrameworkUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleCache;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleInfoRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node.Node;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulesql.ModuleSqlDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable.OutSystemTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageapi.PageApiDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.modulesql.ModuleSqlMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.outsystemtable.OutSystemTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageapi.PageApiMapper;
import com.joyintech.yuntai.module.cfg.loginfo.LogHelper;
import com.joyintech.yuntai.module.cfg.openapi.NoticeTypeController;
import com.joyintech.yuntai.module.cfg.openapi.dto.NoticeTypeSaveReqVO;
import com.joyintech.yuntai.module.cfg.service.lowcodeapi.LowCodeService;
import com.joyintech.yuntai.module.cfg.service.lowcodeapi.ModuleCacheComponent;
import com.joyintech.yuntai.module.cfg.service.moduleinfo.ModuleInfoService;
import com.joyintech.yuntai.module.cfg.service.processdesign.ProcessDesignService;
import com.joyintech.yuntai.module.cfg.service.processdesign.ProcessDesignServiceImpl;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.joyintech.yuntai.framework.common.exception.enums.GlobalErrorCodeConstants.DUPLICATE_KEY;
import static com.joyintech.yuntai.framework.common.exception.enums.GlobalErrorCodeConstants.PRODUCT_REPEAT;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/12
 */
@Tag(name = "管理后台 - 低代码接口定义")
@RestController
@RequestMapping("/cfg/low-code-api")
@Validated
public class LowCodeApiController {
    public static final String FLOW_ID = "flowId";
    public static final String NODE_ID = "nodeId";
    @Resource
    private LowCodeService lowCodeCacheService;
    @Resource
    private ModuleCacheComponent cacheComponent;

    @Resource
    private ProcessDesignService processDesignService;

    @Resource
    private OutSystemTableMapper outSystemTableMapper;

    @Resource
    private PageApiMapper pageApiMapper;

    @Resource
    private ModuleSqlMapper moduleSqlMapper;

    @Value("${ibps.pushBackModelDataUrl}")
    private String pushBackModelDataUrl;

    @Value("${ibps.pushBackModelDataIp}")
    private String pushBackModelDataIp;

    @Resource
    private ModuleInfoService moduleInfoService;

    @Operation(summary = "低代码通用服务接口,支持crud", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LowCodeParam.class))))
    @PostMapping(value = "/{serviceId}")
    public CommonResult<Object> handle(@PathVariable("serviceId") Long serviceId,
                                       @Valid @RequestBody LowCodeParam param) {
        param.setServiceId(serviceId);
        Object result = "";
        ModuleCache api = cacheComponent.getModuleCache(param.getServiceId(), true);
        try {//放在service中切换数据源居然失效了,所以放在这里
            String dataSource = String.valueOf(api.getDataSourceId());
            if (!dataSource.equalsIgnoreCase(DynamicDataSourceContextHolder.peek())) {
                DynamicDataSourceContextHolder.push(dataSource);
            }

            result = lowCodeCacheService.handle(param, api);
            OutSystemTableDO outSystemTableDO = null;
            if(param.getFlowInfo()!=null){
                // 外部流程节点对应的java类
                Object object = lowCodeCacheService.processMethods(param.getParams(), param.getFlowInfo(), api);
                //特殊处理批量创建产品接口,若同一产品下起始编号重复的话,流程终止
                if("false".equals(String.valueOf(object))){
                    return CommonResult.error(PRODUCT_REPEAT);
                }
                // 根据这三个条件来，flowId不为空、nodeId不为空、pageApiCode=form_create来判断，需要发起泛微流程
                if(param.getFlowInfo().containsKey(FLOW_ID)
                        && StringUtils.isNotBlank((String)param.getFlowInfo().get(FLOW_ID))
                        && param.getFlowInfo().containsKey(NODE_ID)
                        && StringUtils.isNotBlank((String)param.getFlowInfo().get(NODE_ID))
                        && "form-create".equals(param.getPageApiCode())){
                    outSystemTableDO = lowCodeCacheService.initiateDataProcess(param, result);
                }
            }
            
            //判断是否需要回推数据
            if("form-create".equals(param.getPageApiCode()) || "form-update".equals(param.getPageApiCode())){
                Map<String, Object> flowInfo = param.getFlowInfo();
                if(ObjectUtils.isNotEmpty(flowInfo)){
                    Node node = processDesignService.getNode(String.valueOf(flowInfo.get("nodeId")),
                            Long.valueOf(String.valueOf(flowInfo.get("pageId"))), String.valueOf(flowInfo.get("flowId")));
                    NoticeTypeController bean = SpringContentUtils.getBean(NoticeTypeController.class);
                    if(Optional.ofNullable(node).isPresent()){
                        if(node.getIsPushBack() != null && node.getIsPushBack()){
                            if(outSystemTableDO == null){
                                //非首节点发起,查询对应数据
                                String pageDataId = String.valueOf(flowInfo.get("pageDataId"));
                                outSystemTableDO = outSystemTableMapper.selectOne(OutSystemTableDO::getPageDataId, pageDataId);
                            }
                            //回推数据
                            if(Optional.ofNullable(outSystemTableDO).isPresent()){
                                NoticeTypeSaveReqVO noticeTypeSaveReqVO = new NoticeTypeSaveReqVO();
                                noticeTypeSaveReqVO.setSerialNum(outSystemTableDO.getBusinessId());
                                noticeTypeSaveReqVO.setPageInfoId(Long.valueOf(String.valueOf(flowInfo.get("pageId"))));
                                noticeTypeSaveReqVO.setNodeId(String.valueOf(flowInfo.get("nodeId")));
                                noticeTypeSaveReqVO.setWorkFlowId(String.valueOf(flowInfo.get("flowId")));
                                noticeTypeSaveReqVO.setNoticeType("2");
                                noticeTypeSaveReqVO.setFlowStatus("1");
                                noticeTypeSaveReqVO.setRequestid(outSystemTableDO.getFlowInstanceId());
                                noticeTypeSaveReqVO.setIsLocal(true);
                                LogHelper.outPutLog("调用回推接口参数:---%s---", noticeTypeSaveReqVO.toString());
                                bean.initiateNoticeType(noticeTypeSaveReqVO);
                            }
                        }
                        if(node.getIsPushBackModelData() != null && node.getIsPushBackModelData()) {
                            //回推模型数据
                            pushBackModelData(param, outSystemTableDO,node);
                        }
                    }
                }
            }
            return success(result);
        } finally {
            DynamicDataSourceContextHolder.clear();
        }
    }

    public void pushBackModelData(LowCodeParam param, OutSystemTableDO outSystemTableDO,Node node) {
        Map<String, Object> flowInfo = param.getFlowInfo();
        if(outSystemTableDO == null){
            //非首节点发起,查询对应数据
            String pageDataId = String.valueOf(flowInfo.get("pageDataId"));
            outSystemTableDO = outSystemTableMapper.selectOne(OutSystemTableDO::getPageDataId, pageDataId);
        }

        // 获取映射数据服务
        List<PageApiDO> pageApiDO = pageApiMapper.selectList(new QueryWrapper<PageApiDO>()
                .eq("page_id", Long.valueOf(String.valueOf(flowInfo.get("pageId"))))
        );
        Map<String, PageApiDO> map = pageApiDO.stream().collect(Collectors.toMap(PageApiDO::getApiCode, Function.identity()));
        Long serverId = map.get("form-detail").getServerId();
        ModuleCache api = cacheComponent.getModuleCache(serverId, true);
        Long updateServerId = map.get("form-update").getServerId();
        ModuleCache updateApi = cacheComponent.getModuleCache(updateServerId, true);
        // 获取数据
        // 获取MODULE_TABLE_ID
        ModuleSqlDO moduleSqlDO = moduleSqlMapper.selectOne(
                ModuleSqlDO::getModuleId, map.get("form-detail").getModuleId(),
                ModuleSqlDO::getActionType, "GET_BY_ID"
        );
        LowCodeParam lowCodeParam = new LowCodeParam();
        lowCodeParam.setServiceId(map.get("form-detail").getServerId());
        lowCodeParam.setPageId(Long.valueOf(String.valueOf(flowInfo.get("pageId"))));
        lowCodeParam.setPageApiCode(map.get("form-detail").getApiCode());
        Map<String, Object> map1 = new HashMap<>();
        map1.put("pageType", "edit");
        map1.put(LowCodeService.PK_PREFIX_ONE + moduleSqlDO.getModuleTableId(), outSystemTableDO.getPageDataId());
        map1.put(LowCodeService.PK_PREFIX_TWO + moduleSqlDO.getModuleTableId(), outSystemTableDO.getPageDataId());
        lowCodeParam.setParams(map1);
        lowCodeParam.setFlowInfo(param.getFlowInfo());
        LogHelper.outPutLog("组装业务回推数据参数-lowCodeParam:---%s---", lowCodeParam.toString());
        JSONObject jsonObject = lowCodeCacheService.getModelData(lowCodeParam, api,updateApi);
        String  modId = String.valueOf(jsonObject.get("modId"));
        jsonObject.remove("modId");
        NoticeTypeSaveReqVO noticeTypeSaveReqVO = new NoticeTypeSaveReqVO();
        if(node == null){
            noticeTypeSaveReqVO.setIsFinal("1");
        }else{
            noticeTypeSaveReqVO.setIsFinal("0");
        }
        noticeTypeSaveReqVO.setModId(modId);
        noticeTypeSaveReqVO.setSerialNum(outSystemTableDO.getBusinessId());
        noticeTypeSaveReqVO.setRequestid(outSystemTableDO.getFlowInstanceId());
        noticeTypeSaveReqVO.setNodeId(String.valueOf(flowInfo.get("nodeId")));
        noticeTypeSaveReqVO.setWorkFlowId(String.valueOf(flowInfo.get("flowId")));
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        noticeTypeSaveReqVO.setData(gson.toJson(jsonObject));
        //TODO 暂时传admin用户
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if(Optional.ofNullable(loginUser).isPresent()){
            noticeTypeSaveReqVO.setUserId(String.valueOf(loginUser.getId()));
        }else{
            noticeTypeSaveReqVO.setUserId("1356921330604666888");
        }

        //添加映射数据
        NoticeTypeController bean = SpringContentUtils.getBean(NoticeTypeController.class);
        noticeTypeSaveReqVO.setNoticeType("2");
        noticeTypeSaveReqVO.setFlowStatus("1");
        noticeTypeSaveReqVO.setPageInfoId(Long.valueOf(String.valueOf(flowInfo.get("pageId"))));
        CommonResult<?> data = bean.getData(noticeTypeSaveReqVO);
        //异步调用业务接口回推模型数据
        String jsonData = gson.toJson(data.getData());
        noticeTypeSaveReqVO.setMappingData(jsonData);
        String json = gson.toJson(noticeTypeSaveReqVO);
        LogHelper.outPutLog("调用业务系统回推接口返回数据为---%s---", json);
        String pushBackUrl = "";
        Long moduleId = map.get("form-detail").getModuleId();
        ModuleInfoRespVO moduleInfo = moduleInfoService.getModuleInfo(moduleId);
        //优先获取模型配置路径
        if(Optional.ofNullable(moduleInfo).isPresent()){
            pushBackUrl = moduleInfo.getPushBackUrl();
        }
        //模型配置路径为空时获取流程节点配置路径
        if(StringUtils.isEmpty(pushBackUrl) && node != null){
            pushBackUrl = node.getPushBackUrl();
        }
        //未指定接口路径时,以配置文件中为准
        if(StringUtils.isNotEmpty(pushBackUrl)){
            pushBackModelDataUrl = (pushBackModelDataIp + pushBackUrl).trim();
        }
        OkHttpClient client = new OkHttpClient();
        LogHelper.outPutLog("调用业务系统回推数据模型数据url为:---%s---", pushBackModelDataUrl);
        Observable<String> observable = Observable.fromCallable(() -> {
            Request request = new Request.Builder()
                    .url(pushBackModelDataUrl)
                    .post(okhttp3.RequestBody.create(json, MediaType.parse("application/json; charset=utf-8")))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new Exception("Unexpected code " + response);
                return response.body().string();
            }
        }).subscribeOn(Schedulers.io());

        observable.observeOn(Schedulers.newThread())
                .subscribe(
                        response -> LogHelper.outPutLog("发送成功---%s---", response),
                        throwable -> LogHelper.outPutLog("发送失败---%s---", throwable.getMessage())
                );
    }
}
