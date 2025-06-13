package com.joyintech.yuntai.module.cfg.openapi;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.toolkit.SpringContentUtils;
import com.google.gson.Gson;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.pojo.LowCodeParam;
import com.joyintech.yuntai.framework.common.util.date.DateUtils;
import com.joyintech.yuntai.framework.mybatis.core.util.DBDynamicSqlExecutorUtils;
import com.joyintech.yuntai.framework.security.core.LoginUser;
import com.joyintech.yuntai.framework.security.core.util.SecurityFrameworkUtils;
import com.joyintech.yuntai.framework.web.core.util.WebFrameworkUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.LowCodeApiController;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleCache;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleInfoRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.TableField;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node.Node;
import com.joyintech.yuntai.module.cfg.dal.dataobject.fileinfo.FileInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.loginfo.LogInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulefield.ModuleFieldDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulesql.ModuleSqlDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable.OutSystemTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageapi.PageApiDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageinfo.PageInfoDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.loginfo.LogInfoMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.modulefield.ModuleFieldMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.modulesql.ModuleSqlMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.outsystemtable.OutSystemTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageapi.PageApiMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageinfo.PageInfoMapper;
import com.joyintech.yuntai.module.cfg.loginfo.LogHelper;
import com.joyintech.yuntai.module.cfg.openapi.dto.DataProcessSaveReqVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.NoticeTypeSaveReqVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.PrintFormDataCommonReqVO;
import com.joyintech.yuntai.module.cfg.openapi.enums.PrintFromDataEnum;
import com.joyintech.yuntai.module.cfg.service.lowcodeapi.LowCodeService;
import com.joyintech.yuntai.module.cfg.service.lowcodeapi.ModuleCacheComponent;
import com.joyintech.yuntai.module.cfg.service.moduleinfo.ModuleInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.service.processdata.ProcessDataService;
import com.joyintech.yuntai.module.cfg.utils.FlowParamUtl;
import com.joyintech.yuntai.module.system.api.user.SysUserApi;
import com.joyintech.yuntai.module.system.api.user.dto.ApiSysUser;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

/**
 * 消息通知
 *
 * @author hzz
 * @since 2024-12-17
 */

@Tag(name = "外部调用接口-消息通知")
@RestController
@RequestMapping("/api/noticeType")
@Validated
public class NoticeTypeController {

    @Resource
    private PageInfoMapper pageInfoMapper;

    @Resource
    private LowCodeApiController lowCodeApiController;

    @Resource
    private OutSystemTableMapper outSystemTableMapper;

    @Resource
    private PageApiMapper pageApiMapper;

    @Resource
    private ModuleFieldMapper moduleFieldMapper;

    @Resource
    private LogInfoMapper logInfoMapper;

    @Resource
    private ModuleSqlMapper moduleSqlMapper;

    @Resource
    private ModuleInfoService moduleInfoService;

    @Value("${ibps.receiveProcess}")
    private String url;

    @Value("${ibps.pushBackFileData}")
    private String pushBackFileData;

    @Value("${fanwei.printUerId}")
    private String printUerId;

    @Resource
    private ModuleCacheComponent cacheComponent;

    @Resource
    private SysUserApi sysUserApi;

    @Resource
    private DBDynamicSqlExecutorUtils executorUtils;

    public static final String ATTACHMENT_LIST = "attachmentList";
    public static final String CHILD_FLAG = "list_";
    private static final String ID_FORMAT_ONE = "ID_";

//    private static final String  url = "http://172.16.11.90:4042/ibps/fwoa/process/receiveProcessType";
    //private static final String  url = "http://10.0.27.76:4010/ibps/fwoa/process/receiveProcessType";

    @PostMapping("/initiateNoticeType")
    @Operation(summary = "消息通知", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = NoticeTypeSaveReqVO.class))))
    public CommonResult<?> initiateNoticeType (@Valid @RequestBody NoticeTypeSaveReqVO noticeTypeSaveReqVO) {
        // 流程状态
        OutSystemTableDO outSystemTableDO = outSystemTableMapper.selectOne(OutSystemTableDO::getFlowInstanceId, noticeTypeSaveReqVO.getRequestid());

        // 获取页面信息
        PageInfoDO pageInfoDO = pageInfoMapper.selectById(outSystemTableDO.getPageId());
        noticeTypeSaveReqVO.setPageInfoId(outSystemTableDO.getPageId());
        noticeTypeSaveReqVO.setSerialNum(outSystemTableDO.getBusinessId());
        List<PageApiDO> pageApiDO = pageApiMapper.selectList(new QueryWrapper<PageApiDO>()
                .eq("page_id", pageInfoDO.getId())
        );
        Map<String, PageApiDO> map = pageApiDO.stream().collect(Collectors.toMap(PageApiDO::getApiCode, Function.identity()));

        // 获取模型字段
        List<ModuleFieldDO> moduleFieldDO = moduleFieldMapper.selectList(
                ModuleFieldDO::getSysAliasName, "a",
                ModuleFieldDO::getModuleId, map.get("form-detail").getModuleId());

        // 获取数据
        LowCodeParam lowCodeParam = new LowCodeParam();
        lowCodeParam.setServiceId(map.get("form-detail").getServerId());
        lowCodeParam.setPageId(pageInfoDO.getId());
        lowCodeParam.setPageApiCode(map.get("form-detail").getApiCode());
        Map<String, Object> map1 = new HashMap<>();
        map1.put(LowCodeService.PK_PREFIX_ONE + moduleFieldDO.get(0).getModuleTableId(), outSystemTableDO.getPageDataId());
        map1.put(LowCodeService.PK_PREFIX_TWO + moduleFieldDO.get(0).getModuleTableId(), outSystemTableDO.getPageDataId());
        lowCodeParam.setParams(map1);
        LogHelper.outPutLog("获取数据---%s---%s---", map.get("form-detail").getServerId().toString(), lowCodeParam.toString());
        CommonResult<Object> handle = lowCodeApiController.handle(map.get("form-detail").getServerId(), lowCodeParam);
        LogHelper.outPutLog("获取数据结果---%s---", handle.toString());

        if("2".equals(noticeTypeSaveReqVO.getNoticeType()) && "5".equals(noticeTypeSaveReqVO.getFlowStatus())){
            //泛微打印需求
            try{
                return success(true);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    Thread.sleep(3000);
                    String res = printFormData(noticeTypeSaveReqVO, handle, lowCodeParam);
                    LogHelper.outPutLog("个性化打印调用泛微系统接口返回值为:---%s--",res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 处理日志
        LogInfoDO logInfoDO = new LogInfoDO();
        logInfoDO.setFormId(Long.valueOf(outSystemTableDO.getPageDataId()));
        logInfoDO.setWorkFlowId(noticeTypeSaveReqVO.getWorkFlowId());
        logInfoDO.setRequestId(noticeTypeSaveReqVO.getRequestid());
        logInfoDO.setSerialNum(noticeTypeSaveReqVO.getSerialNum());
        logInfoDO.setContent(handle.getData().toString());
        logInfoDO.setNodeId(noticeTypeSaveReqVO.getNodeId());
        logInfoMapper.insert(logInfoDO);
        noticeTypeSaveReqVO.setUserId(logInfoDO.getCreator());

        // TODO:异步通知业务系统
        saveModId(handle.getData(),noticeTypeSaveReqVO);
        OkHttpClient client = new OkHttpClient();

        Boolean isLocal = noticeTypeSaveReqVO.getIsLocal();
        if(isLocal == null || !isLocal){
            //推送文件信息
            if("2".equals(noticeTypeSaveReqVO.getFlowStatus()) && "2".equals(noticeTypeSaveReqVO.getNoticeType())){
                Map data = (Map) handle.getData();
                Object object = data.get("attachmentList");
                List<FileInfoDO> fileInfoList = new ArrayList<>();
                if(object != null && object instanceof List){
                    if(object != null && ((List) object).size() > 0){
                        for(int i = 0; i < ((List) object).size(); i++){
                            Object obj = ((List<?>) object).get(i);
                            FileInfoDO fileInfo = BeanUtils.toBean(obj, FileInfoDO.class);
                            fileInfo.setUploadUser(fileInfo.getUploadUserId());
                            fileInfoList.add(fileInfo);
                        }
                        if(CollUtil.isNotEmpty(fileInfoList)){
                            noticeTypeSaveReqVO.setFileData(fileInfoList);
                            String fileJson = JSON.toJSONString(noticeTypeSaveReqVO);
                            LogHelper.outPutLog("给业务系统推送附件信息---%s---", fileJson);
                            Observable<String> observableFile = Observable.fromCallable(() -> {
                                Request request = new Request.Builder()
                                        .url(pushBackFileData)
                                        .post(okhttp3.RequestBody.create(fileJson, MediaType.parse("application/json; charset=utf-8")))
                                        .build();

                                try (Response response = client.newCall(request).execute()) {
                                    if (!response.isSuccessful()) throw new Exception("Unexpected code " + response);

                                    return response.body().string();
                                }
                            }).subscribeOn(Schedulers.io());

                            observableFile.observeOn(Schedulers.newThread())
                                    .subscribe(
                                            response -> LogHelper.outPutLog("发送成功---%s---", response),
                                            throwable -> LogHelper.outPutLog("发送失败---%s---", throwable.getMessage())
                                    );
                        }
                    }
                }
            }

            //判断是否推送模型数据
            Long moduleId = map.get("form-detail").getModuleId();
            ModuleInfoRespVO moduleInfo = moduleInfoService.getModuleInfo(moduleId);
            if(Optional.ofNullable(moduleInfo).isPresent()){
                Boolean isPushBackModelData = moduleInfo.getIsPushBackModelData();
                if(isPushBackModelData != null && isPushBackModelData) {
                    //回推模型数据
                    Map<String, Object> flowInfo = lowCodeParam.getFlowInfo();
                    if(flowInfo == null || flowInfo.isEmpty()){
                        flowInfo = new HashMap<>();
                        flowInfo.put("nodeId",noticeTypeSaveReqVO.getNodeId());
                        flowInfo.put("pageId",pageInfoDO.getId());
                        flowInfo.put("pageDataId",noticeTypeSaveReqVO.getPageInfoId());
                        flowInfo.put("flowId",noticeTypeSaveReqVO.getWorkFlowId());
                        lowCodeParam.setFlowInfo(flowInfo);
                    }
                    LogHelper.outPutLog("泛微回调时推送业务数据---%s---", lowCodeParam.toString());
                    LowCodeApiController bean = SpringContentUtils.getBean(LowCodeApiController.class);
                    bean.pushBackModelData(lowCodeParam,outSystemTableDO,null);
                }
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(noticeTypeSaveReqVO);

        Observable<String> observable = Observable.fromCallable(() -> {
            Request request = new Request.Builder()
                    .url(url)
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

        return success(true);
    }

    private String printFormData( NoticeTypeSaveReqVO noticeTypeSaveReqVO,CommonResult<Object> handle,LowCodeParam lowCodeParam)
            throws IllegalAccessException {
        PrintFormDataCommonReqVO printFormDataCommonReqVO = new PrintFormDataCommonReqVO();
        Map data = (Map) handle.getData();
        ModuleCache api = cacheComponent.getModuleCache(lowCodeParam.getServiceId(), true);
        List<TableField> fields = moduleFieldMapper.selectByModuleId(api.getModuleId(),null);
        ((Map<String, Object>) data).forEach((key, value) -> {
            if (!ATTACHMENT_LIST.equals(key) ){
                //解析主表数据
                if(value instanceof Map){
                    ((Map<String, Object>) value).forEach((k,v) ->{
                        fields.forEach(field -> {
                            if(k.equalsIgnoreCase(field.getFieldName() + "_" + key)){
                                setPrintData((Map<String, Object>) value,v,field,printFormDataCommonReqVO);
                            }
                        });
                    });
                }
                //解析子表数据
                if(value instanceof List){
                    List valueList = (List) value;
                    for(Object object : valueList){
                        ((Map<String, Object>) object).forEach((k,v) ->{
                            fields.forEach(field -> {
                                if(k.equalsIgnoreCase(field.getFieldName() + "_" + key)){
                                    setPrintData((Map<String, Object>)object,v,field,printFormDataCommonReqVO);
                                }
                            });
                        });
                    }
                }
            }
        });
        if(StringUtils.isEmpty(noticeTypeSaveReqVO.getUserId())){
            noticeTypeSaveReqVO.setUserId(printUerId);
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(printFormDataCommonReqVO.getAccountManager())){
            //根据userId查询用户数据
            ApiSysUser user = sysUserApi.getUser(printFormDataCommonReqVO.getAccountManager());
            if(Optional.ofNullable(user).isPresent()){
                printFormDataCommonReqVO.setAccountManager(user.getRealname());
            }
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(printFormDataCommonReqVO.getInstructionReviewer())){
            //根据userId查询用户数据
            ApiSysUser user = sysUserApi.getUser(printFormDataCommonReqVO.getInstructionReviewer());
            if(Optional.ofNullable(user).isPresent()){
                printFormDataCommonReqVO.setAccountManager(user.getRealname());
            }
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(printFormDataCommonReqVO.getFundTransferDate())){
            //格式化日期
            try{
                SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.FORMAT_YEAR_MONTH_DAY);
                Date parse = sdf.parse(printFormDataCommonReqVO.getFundTransferDate());
                String date = sdf.format(parse);
                printFormDataCommonReqVO.setFundTransferDate(date);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String res = FlowParamUtl.requestPrintData(noticeTypeSaveReqVO, printFormDataCommonReqVO);
        return res;
    }

    private void setPrintData(Map<String, Object> map,Object v,TableField field,PrintFormDataCommonReqVO printFormDataCommonReqVO){
        if(PrintFromDataEnum.PROJECT_NAME.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getProjectName())){
            printFormDataCommonReqVO.setProjectName(String.valueOf(v));
        }
        if(PrintFromDataEnum.PROJECT_NUMBER.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getProjectNumber())){
            printFormDataCommonReqVO.setProjectNumber(String.valueOf(v));
        }
        if(PrintFromDataEnum.PROJECT_TYPE.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getProjectType())){
            printFormDataCommonReqVO.setProjectType(String.valueOf(v));
        }
        if(PrintFromDataEnum.PAYMENT_METHOD.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getPaymentMethod())){
            printFormDataCommonReqVO.setPaymentMethod(String.valueOf(v));
        }
        if(PrintFromDataEnum.ACCOUNT_MANAGER.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getAccountManager())){
            printFormDataCommonReqVO.setAccountManager(String.valueOf(v));
        }
        if(PrintFromDataEnum.PURPOSE_OF_PAYMENT.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getPurposeOfPayment())){
            printFormDataCommonReqVO.setPurposeOfPayment(String.valueOf(v));
        }

        if(PrintFromDataEnum.FUND_TRANSFER_DATE.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getFundTransferDate())){
            printFormDataCommonReqVO.setFundTransferDate(String.valueOf(v));
        }
        if(PrintFromDataEnum.INSTRUCTION_REVIEWER.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getInstructionReviewer())){
            printFormDataCommonReqVO.setInstructionReviewer(String.valueOf(v));
        }
        if(PrintFromDataEnum.PAYMENT_AMOUNT.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getPaymentAmount())){
            printFormDataCommonReqVO.setPaymentAmount(String.valueOf(v));
        }
        if(PrintFromDataEnum.PAYMENT_ACCOUNT_NAME.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getPaymentAccountName())){
            printFormDataCommonReqVO.setPaymentAccountName(String.valueOf(v));
        }
        if(PrintFromDataEnum.PAYMENT_ACCOUNT_NUMBER.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getPaymentAccountNumber())){
            printFormDataCommonReqVO.setPaymentAccountNumber(String.valueOf(v));
        }
        if(PrintFromDataEnum.PAYMENT_ACCOUNT_BANK.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getPaymentAccountBank())){
            printFormDataCommonReqVO.setPaymentAccountBank(String.valueOf(v));
        }
        if(PrintFromDataEnum.PAYMENT_ACCOUNT_BRANCH.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getPaymentAccountBranch())){
            printFormDataCommonReqVO.setPaymentAccountBranch(String.valueOf(v));
        }
        if(PrintFromDataEnum.PAYMENT_ACCOUNT_BANK_LOCATION.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getPaymentAccountBankLocation())){
            //查询归属地信息
            printFormDataCommonReqVO.setPaymentAccountBankLocation(String.valueOf(v));
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(field.getTextSql())){
                List<Map<String, Object>> listDataNoUpper = executorUtils.findListDataNoUpper(field.getTextSql(), map);
                if(CollUtil.isNotEmpty(listDataNoUpper)){
                    String paymentAccountBankLocation = String.valueOf(listDataNoUpper.get(0).get("SHOW_TEXT"));
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(paymentAccountBankLocation) &&
                    !"null".equalsIgnoreCase(paymentAccountBankLocation)){
                        printFormDataCommonReqVO.setPaymentAccountBankLocation(paymentAccountBankLocation);
                    }
                }
            }
        }
        if(PrintFromDataEnum.PAYMENT_LARGE_AMOUNT_BANK_NUMBER.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getPaymentLargeAmountBankNumber())){
            printFormDataCommonReqVO.setPaymentLargeAmountBankNumber(String.valueOf(v));
        }
        if(PrintFromDataEnum.RECEIVING_ACCOUNT_NAME.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getReceivingAccountName())){
            printFormDataCommonReqVO.setReceivingAccountName(String.valueOf(v));
        }
        if(PrintFromDataEnum.RECEIVING_ACCOUNT_NUMBER.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getReceivingAccountNumber())){
            printFormDataCommonReqVO.setReceivingAccountNumber(String.valueOf(v));
        }
        if(PrintFromDataEnum.RECEIVING_ACCOUNT_BANK.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getReceivingAccountBank())){
            printFormDataCommonReqVO.setReceivingAccountBank(String.valueOf(v));
        }
        if(PrintFromDataEnum.RECEIVING_ACCOUNT_BRANCH.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getReceivingAccountBranch())){
            printFormDataCommonReqVO.setReceivingAccountBranch(String.valueOf(v));
        }
        if(PrintFromDataEnum.RECEIVING_ACCOUNT_BANK_LOCATION.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getReceivingAccountBankLocation())){
            printFormDataCommonReqVO.setReceivingAccountBankLocation(String.valueOf(v));
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(field.getTextSql())){
                List<Map<String, Object>> listDataNoUpper = executorUtils.findListDataNoUpper(field.getTextSql(), map);
                if(CollUtil.isNotEmpty(listDataNoUpper)){
                    String receivingAccountBankLocation = String.valueOf(listDataNoUpper.get(0).get("SHOW_TEXT"));
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(receivingAccountBankLocation) &&
                            !"null".equalsIgnoreCase(receivingAccountBankLocation)){
                        printFormDataCommonReqVO.setReceivingAccountBankLocation(receivingAccountBankLocation);
                    }
                }
            }
        }
        if(PrintFromDataEnum.RECEIVING_LARGE_AMOUNT_BANK_NUMBER.getCode().equals(field.getFieldAliasName()) &&
                StringUtils.isEmpty(printFormDataCommonReqVO.getReceivingLargeAmountBankNumber())){
            printFormDataCommonReqVO.setReceivingLargeAmountBankNumber(String.valueOf(v));
        }
    }

    private void saveModId(Object modData,NoticeTypeSaveReqVO noticeTypeSaveReqVO){
        if(modData instanceof Map){
            ((Map<String, Object>) modData).forEach((key, value) -> {
                if (!ATTACHMENT_LIST.equals(key) && !CHILD_FLAG.startsWith(key)){
                    if(value instanceof Map){
                        ((Map<String, Object>) value).forEach((k,v) ->{
                            if(k.startsWith(ID_FORMAT_ONE)){
                                noticeTypeSaveReqVO.setModId(String.valueOf(v));
                            }
                        });
                    }
                }
            });
        }
    }

    @PostMapping("/getData")
    @Operation(summary = "获取数据", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = NoticeTypeSaveReqVO.class))))
    @PermitAll
    public CommonResult<?> getData(@Valid @RequestBody NoticeTypeSaveReqVO dataProcessSaveReqVO) {
        LogHelper.outPutLog("获取数据---%s---", dataProcessSaveReqVO.toString());
        // 获取页面信息
        PageInfoDO pageInfoDO = pageInfoMapper.selectById(dataProcessSaveReqVO.getPageInfoId());

        // 流程状态
        OutSystemTableDO outSystemTableDO = outSystemTableMapper.selectOne(OutSystemTableDO::getFlowInstanceId, dataProcessSaveReqVO.getRequestid());

        // 获取映射数据服务
        List<PageApiDO> pageApiDO = pageApiMapper.selectList(new QueryWrapper<PageApiDO>()
                .eq("page_id", pageInfoDO.getId())
        );
        Map<String, PageApiDO> map = pageApiDO.stream().collect(Collectors.toMap(PageApiDO::getApiCode, Function.identity()));

        // 获取MODULE_TABLE_ID
        ModuleSqlDO moduleSqlDO = moduleSqlMapper.selectOne(
                ModuleSqlDO::getModuleId, map.get("form-detail").getModuleId(),
                ModuleSqlDO::getActionType, "GET_BY_ID"
        );

        // 获取数据
        LowCodeParam lowCodeParam = new LowCodeParam();
        lowCodeParam.setServiceId(map.get("form-detail").getServerId());
        lowCodeParam.setPageId(pageInfoDO.getId());
        lowCodeParam.setPageApiCode(map.get("form-detail").getApiCode());
        lowCodeParam.setMapped(true);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("pageType", "edit");
        map1.put(LowCodeService.PK_PREFIX_ONE + moduleSqlDO.getModuleTableId(), outSystemTableDO.getPageDataId());
        map1.put(LowCodeService.PK_PREFIX_TWO + moduleSqlDO.getModuleTableId(), outSystemTableDO.getPageDataId());
        lowCodeParam.setParams(map1);
        LogHelper.outPutLog("获取数据---%s---%s---", map.get("form-detail").getServerId(), lowCodeParam);
        CommonResult<Object> handle = lowCodeApiController.handle(map.get("form-detail").getServerId(), lowCodeParam);
        LogHelper.outPutLog("获取数据结果---%s---", handle.toString());
        Object data = handle.getData();
        if (data instanceof Map) {
            ((Map<String, Object>) data).put("pageDataId", outSystemTableDO.getBusinessId());
        }
        return success(data);
    }
}
