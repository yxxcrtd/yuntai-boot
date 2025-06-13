package com.joyintech.yuntai.module.cfg.openapi;

import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.pojo.LowCodeParam;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.LowCodeApiController;
import com.joyintech.yuntai.module.cfg.dal.dataobject.loginfo.LogInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable.OutSystemTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageapi.PageApiDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageinfo.PageInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistconfig.PageListConfigDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processdesign.ProcessDesignDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.outsystemtable.OutSystemTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageapi.PageApiMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageinfo.PageInfoMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pagelistconfig.PageListConfigMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.processdesign.ProcessDesignMapper;
import com.joyintech.yuntai.module.cfg.loginfo.LogHelper;
import com.joyintech.yuntai.module.cfg.openapi.dto.DataProcessSaveAgainReqVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.DataProcessSaveReqVO;
import com.joyintech.yuntai.module.cfg.service.lowcodeapi.LowCodeCacheServiceImpl;
import com.joyintech.yuntai.module.cfg.service.processdata.ProcessDataService;
import com.joyintech.yuntai.module.system.api.user.SysUserApi;
import com.joyintech.yuntai.module.system.api.user.dto.ApiSysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/12/12
 */

@Tag(name = "外部调用接口-发起流程")
@RestController
@RequestMapping("/api/dataProcess")
@Validated
@Slf4j
public class DataProcessController {

    @Resource
    private PageInfoMapper pageInfoMapper;

    @Resource
    private LowCodeApiController lowCodeApiController;

    @Resource
    private PageApiMapper pageApiMapper;

    @Resource
    private PageListConfigMapper pageListConfigMapper;

    @Resource
    private OutSystemTableMapper outSystemTableMapper;

    @Resource
    private ProcessDataService processDataService;

    @Resource
    private ProcessDesignMapper processDesignMapper;

    @Resource
    private SysUserApi sysUserApi;

    private static final String FHRGH = "fhrgh";

    @PostMapping("/initiateDataProcess")
    @Operation(summary = "发起流程", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DataProcessSaveReqVO.class))))
    @PermitAll
    public CommonResult<?> initiateDataProcess(@Valid @RequestBody DataProcessSaveReqVO dataProcessSaveReqVO) throws IllegalAccessException {
        LogHelper.outPutLog("表单发起---%s---", dataProcessSaveReqVO.toString());
        // 获取页面信息
        PageInfoDO pageInfoDO = pageInfoMapper.selectById(dataProcessSaveReqVO.getPageInfoId());

        // 获取映射数据服务
        List<PageApiDO> pageApiDO = pageApiMapper.selectList(new QueryWrapper<PageApiDO>()
                .eq("page_id", pageInfoDO.getId())
        );
        Map<String, PageApiDO> map = pageApiDO.stream().collect(Collectors.toMap(PageApiDO::getApiCode, Function.identity()));

        // 获取数据
        LowCodeParam lowCodeParam = new LowCodeParam();
        lowCodeParam.setServiceId(map.get("form-start-detail").getServerId());
        lowCodeParam.setPageId(pageInfoDO.getId());
        lowCodeParam.setPageApiCode(map.get("form-start-detail").getApiCode());
        Map<String, Object> map1 = new HashMap<>();
        if (dataProcessSaveReqVO.getBusinessParameter() != null) {
            map1 = JSON.parseObject(dataProcessSaveReqVO.getBusinessParameter());
        }
        map1.put("pageType", "edit");
        lowCodeParam.setParams(map1);
        if(CollUtil.isNotEmpty(dataProcessSaveReqVO.getChildIdList())){
            lowCodeParam.setChildIdList(dataProcessSaveReqVO.getChildIdList());
        }
        LogHelper.outPutLog("获取数据入参---%s---%s---", map.get("form-start-detail").getServerId().toString(), lowCodeParam.toString());
        CommonResult<Object> handle = lowCodeApiController.handle(map.get("form-start-detail").getServerId(), lowCodeParam);
        LogHelper.outPutLog("获取数据结果---%s---", handle.toString());
        System.out.println(handle.getData().toString());

        // 保存数据
        LowCodeParam lowCodeParamSave = new LowCodeParam();
        lowCodeParamSave.setServiceId(map.get("form-create").getServerId());
        lowCodeParamSave.setPageId(pageInfoDO.getId());
        lowCodeParamSave.setPageApiCode(map.get("form-create").getApiCode());
        JSONObject jsonObject = null;
        if (handle.getData() != null) {
            jsonObject = JSON.parseObject(JSON.toJSONString(handle.getData()));
        }
        lowCodeParamSave.setParams(setApprovalProcess(jsonObject,dataProcessSaveReqVO.getApprovalProcess()));
        LogHelper.outPutLog("保存数据入参---%s---%s---", map.get("form-create").getServerId(), lowCodeParamSave.toString());
        System.out.println(lowCodeParamSave.getParams());
        CommonResult<Object> handle1 = lowCodeApiController.handle(map.get("form-create").getServerId(), lowCodeParamSave);
        LogHelper.outPutLog("保存数据结果---%s---", handle1.toString());

        // 组装泛微字段
        QueryWrapper<PageListConfigDO> wrapper = new QueryWrapper<>();
        wrapper.eq("PAGE_ID", pageInfoDO.getId())
                .isNotNull("COLUMN_TAG_CODE");
        List<PageListConfigDO> pageListConfigDO = pageListConfigMapper.selectList(wrapper);
        Map<String, String> map2 = new HashMap<>();
        Object data = handle.getData();
        // 获取流程名称
        String processName = "";
        String requestName = "";
        List<String> nameList = new ArrayList<>();
        QueryWrapper<ProcessDesignDO> wrapperDesign = new QueryWrapper<>();
        wrapperDesign.eq("page_id",pageInfoDO.getId()).
                eq("flow_id",dataProcessSaveReqVO.getWorkFlowId()).eq("deleted",0);
        List<ProcessDesignDO> processDesignDOS = processDesignMapper.selectList(wrapperDesign);
        if (CollUtil.isNotEmpty(processDesignDOS)) {
            ProcessDesignDO processDesignDO = processDesignDOS.get(0);
            processName = processDesignDO.getProcessName();
            if(StringUtils.isNotEmpty(processName)) {
                nameList = getConfigName(processName);
                if(CollUtil.isNotEmpty(nameList)){
                    for (String name : nameList) {
                        for (Map.Entry<String, Object> entry : ((Map<String, Object>) data).entrySet()) {
                            Object value = entry.getValue();
                            if(value instanceof Map){
                                if (((Map<String, Object>) value).containsKey(name)) {
                                    Object targetValue = ((Map<String, Object>) value).get(name);
                                    if(ObjectUtils.isNotEmpty(targetValue)) {
                                        if(StringUtils.isNotEmpty(requestName)){
                                            requestName = requestName.toString().replace("${" +name +"}",String.valueOf(targetValue));
                                        }else{
                                            requestName = processName.replace("${" +name +"}",String.valueOf(targetValue));
                                        }

                                    }
                                }
                            }
                        }
                    }
                    if(StringUtils.isEmpty(requestName)) {
                        requestName = removeVariablePlaceholders(processName);
                    }else{
                        requestName = removeVariablePlaceholders(requestName);
                    }
                    dataProcessSaveReqVO.setRequestName(replaceRequestName(requestName));
                }else{
                    dataProcessSaveReqVO.setRequestName(replaceRequestName(processName));
                }
            }
        }
        if (CollUtil.isNotEmpty(pageListConfigDO)) {
            pageListConfigDO.forEach(s -> {
                Object o = ((Map<String, Object>) data).get(s.getModuleTableId().toString());
                if (o != null) {
                    Object o1 = ((Map<String, Object>) o).get(s.getColumnName() + "_" + s.getModuleTableId());
                    if("fanwei_user_field".equals(s.getColumnTagCode()) && o1 != null){
                        ApiSysUser user = sysUserApi.getUser(o1.toString());
                        if(Optional.ofNullable(user).isPresent()){
                            map2.put(s.getColumnFieldAlias(), user.getWorkNo());
                        }else{
                            map2.put(s.getColumnFieldAlias(), null);
                        }
                    }else{
                        map2.put(s.getColumnFieldAlias(), o1 != null ? o1.toString() : null);
                    }

                } else {
                    map2.put(s.getColumnFieldAlias(), null);
                }
            });
        }
        if (CollUtil.isNotEmpty(map2)) {
            JSONObject a = JSON.parseObject(dataProcessSaveReqVO.getBusinessParameter());
            if(a.containsKey(FHRGH) && StringUtils.isNotEmpty(String.valueOf(a.get(FHRGH)))){
                if(map2.containsKey(FHRGH)){
                    map2.remove(FHRGH);
                }
            }
            a.putAll(map2);
            String jsonString = JSON.toJSONString(a);
            dataProcessSaveReqVO.setBusinessParameter(jsonString);
            dataProcessSaveReqVO.setYwbm(a.get("ywbm") != null ? a.get("ywbm").toString() : null);
        } else {
            JSONObject a = JSON.parseObject(dataProcessSaveReqVO.getBusinessParameter());
            dataProcessSaveReqVO.setYwbm(a.get("ywbm") != null ? a.get("ywbm").toString() : null);
        }

        // 发送泛微
        OutSystemTableDO outSystemTableDO = new OutSystemTableDO();
        // 处理日志
        LogInfoDO logInfoDO = new LogInfoDO();
        logInfoDO.setContent(String.valueOf(lowCodeParamSave));
        // 创建外部关联关系
        outSystemTableDO.setPageId(pageInfoDO.getId());
        outSystemTableDO.setFlowId(dataProcessSaveReqVO.getWorkFlowId());
        outSystemTableDO.setBusinessId(dataProcessSaveReqVO.getSerialNum());
        outSystemTableDO.setFlowType("xb");
        outSystemTableDO.setPageDataId(handle1.getData().toString());
        if(StringUtils.isBlank(outSystemTableDO.getBusinessId())){
            outSystemTableDO.setBusinessId(String.valueOf(IdWorker.getId()));
        }
        String requestId = null;
        if (handle1.getCode() == 0) {
            // 组装入参
            requestId = processDataService.projectAppear(dataProcessSaveReqVO, outSystemTableDO, logInfoDO,lowCodeParamSave);
        } else {
            outSystemTableDO.setResultStatus(1);
        }
        outSystemTableMapper.insert(outSystemTableDO);
        return success(requestId);
    }

    public static String removeVariablePlaceholders(String input) {
        Pattern pattern = Pattern.compile("【\\$\\{[^}]+}】");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }

    public static String replaceRequestName(String name) {
        Pattern pattern = Pattern.compile("【】");
        Matcher matcher = pattern.matcher(name);
        return matcher.replaceAll("");
    }

    public List<String> getConfigName(String processName){
        List<String> nameList = new ArrayList<>();
        // 定义正则表达式
        String regex = "\\$\\{([^}]+)}";
        Pattern pattern = Pattern.compile(regex);
        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(processName);
        while (matcher.find()) {
            // 提取 ${} 中的内容
            nameList.add(matcher.group(1));
        }

        return nameList;
    }

    @PostMapping("/initiateDataProcessAgain")
    @Operation(summary = "再次发起流程", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DataProcessSaveAgainReqVO.class))))
    @PermitAll
    public CommonResult<?> initiateDataProcessAgain(@Valid @RequestBody DataProcessSaveAgainReqVO dataProcessSaveAgainReqVO) throws IllegalAccessException {
        LogHelper.outPutLog("表单发起---%s---", dataProcessSaveAgainReqVO.toString());
        // 获取页面信息
        PageInfoDO pageInfoDO = pageInfoMapper.selectById(dataProcessSaveAgainReqVO.getPageInfoId());

        // 获取映射数据服务
        List<PageApiDO> pageApiDO = pageApiMapper.selectList(new QueryWrapper<PageApiDO>()
                .eq("page_id", pageInfoDO.getId())
        );
        Map<String, PageApiDO> map = pageApiDO.stream().collect(Collectors.toMap(PageApiDO::getApiCode, Function.identity()));

        // 获取数据
        LowCodeParam lowCodeParam = new LowCodeParam();
        lowCodeParam.setServiceId(map.get("form-start-detail").getServerId());
        lowCodeParam.setPageId(pageInfoDO.getId());
        lowCodeParam.setPageApiCode(map.get("form-start-detail").getApiCode());
        Map<String, Object> map1 = new HashMap<>();
        if (dataProcessSaveAgainReqVO.getBusinessParameter() != null) {
            map1 = JSON.parseObject(dataProcessSaveAgainReqVO.getBusinessParameter());
        }
        map1.put("pageType", "edit");
        lowCodeParam.setParams(map1);
        LogHelper.outPutLog("获取数据入参---%s---%s---", map.get("form-start-detail").getServerId().toString(), lowCodeParam.toString());
        CommonResult<Object> handle = lowCodeApiController.handle(map.get("form-start-detail").getServerId(), lowCodeParam);
        LogHelper.outPutLog("获取数据结果---%s---", handle.toString());
        System.out.println(handle.getData().toString());

        // 保存数据
        LowCodeParam lowCodeParamSave = new LowCodeParam();
        lowCodeParamSave.setServiceId(map.get("form-create").getServerId());
        lowCodeParamSave.setPageId(pageInfoDO.getId());
        lowCodeParamSave.setPageApiCode(map.get("form-create").getApiCode());
        JSONObject jsonObject;
        if (handle.getData() != null) {
            jsonObject = JSON.parseObject(JSON.toJSONString(handle.getData()));
        } else {
            jsonObject = null;
        }
        lowCodeParamSave.setParams(setApprovalProcess(jsonObject,dataProcessSaveAgainReqVO.getApprovalProcess()));//组装前置流程ID
        LogHelper.outPutLog("保存数据入参---%s---%s---", map.get("form-create").getServerId(), lowCodeParamSave.toString());
        System.out.println(lowCodeParamSave.getParams());
        CommonResult<Object> handle1 = lowCodeApiController.handle(map.get("form-create").getServerId(), lowCodeParamSave);
        LogHelper.outPutLog("保存数据结果---%s---", handle1.toString());

        // 组装泛微字段
        QueryWrapper<PageListConfigDO> wrapper = new QueryWrapper<>();
        wrapper.eq("PAGE_ID", pageInfoDO.getId())
                .isNotNull("COLUMN_TAG_CODE");
        List<PageListConfigDO> pageListConfigDO = pageListConfigMapper.selectList(wrapper);
        Map<String, String> map2 = new HashMap<>();
        Object data = handle.getData();
        if (CollUtil.isNotEmpty(pageListConfigDO)) {
            pageListConfigDO.forEach(s -> {
                Object o = ((Map<String, Object>) data).get(s.getModuleTableId().toString());
                if (o != null) {
                    Object o1 = ((Map<String, Object>) o).get(s.getColumnName() + "_" + s.getModuleTableId());
                    map2.put(s.getColumnFieldAlias(), o1 != null ? o1.toString() : null);
                } else {
                    map2.put(s.getColumnFieldAlias(), null);
                }
            });
        }
        if (CollUtil.isNotEmpty(map2)) {
            JSONObject a = JSON.parseObject(dataProcessSaveAgainReqVO.getBusinessParameter());
            a.putAll(map2);
            String jsonString = JSON.toJSONString(a);
            dataProcessSaveAgainReqVO.setBusinessParameter(jsonString);
        }

        // 发送泛微
        OutSystemTableDO outSystemTableDO = new OutSystemTableDO();
        // 处理日志
        LogInfoDO logInfoDO = new LogInfoDO();
        logInfoDO.setContent(String.valueOf(lowCodeParamSave));
        // 创建外部关联关系
        outSystemTableDO.setPageId(pageInfoDO.getId());
        outSystemTableDO.setFlowId(dataProcessSaveAgainReqVO.getWorkFlowId());
        outSystemTableDO.setBusinessId(dataProcessSaveAgainReqVO.getSerialNum());
        outSystemTableDO.setFlowType("xb");
        outSystemTableDO.setPageDataId(handle1.getData().toString());
        if(StringUtils.isBlank(outSystemTableDO.getBusinessId())){
            outSystemTableDO.setBusinessId(String.valueOf(IdWorker.getId()));
        }
        String requestId = null;
        if (handle1.getCode() == 0) {
            // 组装入参
            outSystemTableDO.setResultStatus(4);
            requestId = processDataService.projectAppearAgain(dataProcessSaveAgainReqVO, outSystemTableDO, logInfoDO);
        } else {
            outSystemTableDO.setResultStatus(3);
        }
        outSystemTableMapper.insert(outSystemTableDO);
        return success(requestId);
    }

    /**
     * 给前置流程ID赋值，只需要给模型主表赋值即可
     * @param param
     * @param approvalProcess
     */
    private Map<String,Object> setApprovalProcess(Map<String,Object> param,String approvalProcess) {
        String APPROVAL_PROCESS = "APPROVAL_PROCESS_%s";
        if (StringUtils.isEmpty(approvalProcess)) return param;
        Map<String,Object> result = new HashMap<>();
        if (CollUtil.isNotEmpty(param)) {
            param.forEach((k, value) -> {
                if (!k.startsWith(LowCodeCacheServiceImpl.CHILD_FLAG)) {
                    if (value != null) {
                        Map<String, Object> v = BeanUtil.beanToMap(value);
                        String key = String.format(APPROVAL_PROCESS, k);
                        if (!v.containsKey(key)) {
                            v.put(key, approvalProcess);
                            result.put(k, v);
                        }else {
                            result.put(k, value);
                        }
                    } else {
                        result.put(k, value);
                    }
                }else {
                    result.put(k, value);
                }
            });
        }
        return result;
    }


}
