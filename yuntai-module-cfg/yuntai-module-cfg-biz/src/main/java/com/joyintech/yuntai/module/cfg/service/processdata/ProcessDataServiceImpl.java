package com.joyintech.yuntai.module.cfg.service.processdata;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.joyintech.yuntai.framework.common.pojo.LowCodeParam;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleCache;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleinfo.ModuleInfoDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduleinfo.ModuleInfoMapper;
import com.joyintech.yuntai.module.cfg.openapi.dto.DataProcessSaveAgainReqVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo.ProcessDataPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo.ProcessDataSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.loginfo.LogInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable.OutSystemTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processdata.ProcessDataDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.loginfo.LogInfoMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.outsystemtable.OutSystemTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.processdata.ProcessDataMapper;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.loginfo.LogHelper;
import com.joyintech.yuntai.module.cfg.openapi.dto.DataProcessSaveReqVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.FwSaveData;
import com.joyintech.yuntai.module.cfg.openapi.dto.WorkflowRequestTableField;
import com.joyintech.yuntai.module.cfg.service.lowcodeapi.LowCodeCacheServiceImpl;
import com.joyintech.yuntai.module.cfg.service.lowcodeapi.ModuleCacheComponent;
import com.joyintech.yuntai.module.cfg.utils.FlowParamUtl;
import com.joyintech.yuntai.module.cfg.utils.IdentityVerifyUtil;
import com.xingyuv.captcha.util.StringUtils;

import jodd.util.StringUtil;

import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.PROCESS_DATA_NOT_EXISTS;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 流程Log日志 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ProcessDataServiceImpl implements ProcessDataService {

    @Resource
    private ProcessDataMapper processDataMapper;

    @Resource
    private LogInfoMapper logInfoMapper;

    @Resource
    private OutSystemTableMapper outSystemTableMapper;

    @Value("${ibps.serviceDataSaveUrl}")
    private String serviceDataSaveUrl;

    @Value("${fanweioa.formUrl}")
    private String formUrl;

    @Resource
    private ModuleCacheComponent cacheComponent;

    @Resource
    private ModuleInfoMapper moduleInfoMapper;

    @Override
    public Long createProcessData(ProcessDataSaveReqVO createReqVO) {
        // 插入
        ProcessDataDO processData = BeanUtils.toBean(createReqVO, ProcessDataDO.class);
        processDataMapper.insert(processData);
        // 返回
        return processData.getId();
    }

    @Override
    public void updateProcessData(ProcessDataSaveReqVO updateReqVO) {
        // 校验存在
        validateProcessDataExists(updateReqVO.getId());
        // 更新
        ProcessDataDO updateObj = BeanUtils.toBean(updateReqVO, ProcessDataDO.class);
        processDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteProcessData(Long id) {
        // 校验存在
        validateProcessDataExists(id);
        // 删除
        processDataMapper.deleteById(id);
    }

    private void validateProcessDataExists(Long id) {
        if (processDataMapper.selectById(id) == null) {
            throw exception(PROCESS_DATA_NOT_EXISTS);
        }
    }

    @Override
    public ProcessDataDO getProcessData(Long id) {
        return processDataMapper.selectById(id);
    }

    @Override
    public PageResult<ProcessDataDO> getProcessDataPage(ProcessDataPageReqVO pageReqVO) {
        return processDataMapper.selectPage(pageReqVO);
    }

    /**
     * 流程日志数据
     *
     * @param reqVO 页面数据
     * @return
     */
    @Override
    public String findProcessDataList(ProcessDataPageReqVO reqVO) {
        List<ProcessDataDO> list = processDataMapper.findProcessDataList(reqVO);
        if(list!=null && !list.isEmpty()){
            return list.get(0).getFormData();
        }
        return null;
    }

    @Override
    public String projectAppear(DataProcessSaveReqVO dataProcessSaveReqVO, OutSystemTableDO outSystemTableDO, LogInfoDO logInfoDO,
            LowCodeParam param) throws IllegalAccessException {
        // 获取泛微token
        String token = IdentityVerifyUtil.getToken();
        LogHelper.outPutLog("获取泛微token---%s---", token);
        if (StringUtil.isBlank(token)) {
            throw exception(500, "泛微OA系统登录失败,请联系管理员");
        }

        FwSaveData fwSaveData  = new FwSaveData();
        // 组装业务参数
        Map<String, String> otherParams = new HashMap<>();
        if(StringUtil.isNotEmpty(dataProcessSaveReqVO.getIsnextflow())){
            otherParams.put("isnextflow",dataProcessSaveReqVO.getIsnextflow());
        }
        fwSaveData.setUrl(formUrl);
        fwSaveData.setProductId(outSystemTableDO.getPageDataId());

        JSONObject jsonObject = new JSONObject();
        // 因泛微系统不能接收ID，所以在此处做了下转换
        if (StringUtil.isNotBlank(dataProcessSaveReqVO.getBusinessParameter())) {
            jsonObject = JSON.parseObject(dataProcessSaveReqVO.getBusinessParameter());
            jsonObject.put("productId", outSystemTableDO.getPageDataId());
        } else {
            jsonObject.put("productId", outSystemTableDO.getPageDataId());
        }
        //        jsonObject.put("serialNum", dataProcessSaveReqVO.getSerialNum());
        JSONObject filteredJsonObject = new JSONObject();
        for (String key : jsonObject.keySet()) {
            // 检查键名是否包含 "CHILD"
            if (!(key.contains("CHILD") || key.contains("ID_"))) {
                // 如果不包含，则将该键值对添加到新的 JSONObject 中
                filteredJsonObject.put(key, jsonObject.get(key));
            }
        }

        LogHelper.outPutLog("调用泛微---%s---%s---", fwSaveData, dataProcessSaveReqVO);
        String data = FlowParamUtl.reqestParam(filteredJsonObject, dataProcessSaveReqVO.getWorkFlowId(), dataProcessSaveReqVO.getUserId(), JSON.toJSONString(otherParams), dataProcessSaveReqVO);
        if (data != null) {
            JSONObject result = JSON.parseObject(data);
            if ("SUCCESS".equals(result.get("code"))) {
                String successDate = result.get("data").toString();
                JSONObject requestIdJson = JSON.parseObject(successDate);
                String requestId = requestIdJson.get("requestid").toString();
                outSystemTableDO.setFlowInstanceId(requestId);

                // 保存业务数据到快照
                Map<String, String> params = new HashMap<>();
                params.put("requestName", dataProcessSaveReqVO.getRequestName()); // 流程名称
                params.put("mainData", getFormMainData(filteredJsonObject));   // 业务入参
                params.put("detailData","");
                params.put("workflowId", dataProcessSaveReqVO.getWorkFlowId());   // 流程ID
                params.put("remark", dataProcessSaveReqVO.getRemark());
                params.put("otherParams", JSON.toJSONString(otherParams));   // 其他参数
                params.put("requestLevel", dataProcessSaveReqVO.getRequestLevel());
                StringBuffer buffer = new StringBuffer();
                buffer.append("lowCodeParamSave:").append(logInfoDO.getContent())
                        .append("----------params:").append(JSON.toJSONString(params))
                        .append("----------res:").append(result);
                logInfoDO.setContent(buffer.toString());
                logInfoDO.setRequestId(requestId);
                logInfoDO.setSerialNum(dataProcessSaveReqVO.getSerialNum());
                logInfoDO.setWorkFlowId(dataProcessSaveReqVO.getWorkFlowId());
                logInfoMapper.insert(logInfoDO);
                // 业务流程记录保存
                this.okHttpClient(requestId, dataProcessSaveReqVO,param);
                return successDate;
            } else {
                outSystemTableDO.setResultStatus(1);
                outSystemTableMapper.insert(outSystemTableDO);
                throw  exception(500, data);
            }
        }
        return null;
    }

    public static<T> String getFormMainData(JSONObject json) throws IllegalAccessException {
        List<WorkflowRequestTableField> mainData = new ArrayList<>();
        WorkflowRequestTableField field1 = new WorkflowRequestTableField();
        for(String key: json.keySet()) {
            if(!(key.contains("BUSINESS_ID") || key.contains("BUSSINESS_ID"))){
                //单行文本字段
                WorkflowRequestTableField field2 = new WorkflowRequestTableField();
                //field.setAccessible(true); // 设置为可访问
                String fieldName = key;
                Object fieldValue = json.get(key);
                field2.setFieldName(fieldName);
                //                    field2.setFieldName(convertCamelToDatabase(fieldName));
                field2.setFieldValue(Objects.isNull(fieldValue) ? "" : fieldValue + "");
                mainData.add(field2);
            }
        }
        // 使用反射获取字段名和字段值
        //        Class<?> clazz = entity.getClass();
        //        Field[] fields = clazz.getDeclaredFields();
        //        for (Field field : fields) {
        //            //单行文本字段
        //            WorkflowRequestTableField field2 = new WorkflowRequestTableField();
        //            field.setAccessible(true); // 设置为可访问
        //
        //            String fieldName = field.getName();
        //            Object fieldValue = field.get( entity);
        //            System.out.println(fieldName + ": " + fieldValue);
        //            if (!Modifier.isStatic(field.getModifiers())) {
        //                if(!"requestid".equals(fieldName) && !"flowState".equals(fieldName) && !"fwFlowState".equals(fieldName)){
        //                    field2.setFieldName(fieldName);
        ////                    field2.setFieldName(convertCamelToDatabase(fieldName));
        //                    field2.setFieldValue(Objects.isNull(fieldValue) ? "" : fieldValue + "");
        //                    mainData.add(field2);
        //                }
        //
        //            }
        //
        //        }

        return JSONObject.toJSONString(mainData);
    }

    @Override
    public String projectAppearAgain(DataProcessSaveAgainReqVO dataProcessSaveReqVO, OutSystemTableDO outSystemTableDO, LogInfoDO logInfoDO) throws IllegalAccessException {
        // 获取泛微token
        String token = IdentityVerifyUtil.getToken();
        LogHelper.outPutLog("获取泛微token---%s---", token);
        if (StringUtil.isBlank(token)) {
            throw exception(500, "泛微OA系统登录失败,请联系管理员");
        }

        FwSaveData fwSaveData  = new FwSaveData();
        // 组装业务参数
        Map<String, String> otherParams = new HashMap<>();
        fwSaveData.setUrl(formUrl);
        fwSaveData.setProductId(outSystemTableDO.getPageDataId());

        JSONObject jsonObject = new JSONObject();
        // 因泛微系统不能接收ID，所以在此处做了下转换
        if (StringUtil.isNotBlank(dataProcessSaveReqVO.getBusinessParameter())) {
            jsonObject = JSON.parseObject(dataProcessSaveReqVO.getBusinessParameter());
            jsonObject.put("productId", outSystemTableDO.getPageDataId());
        } else {
            jsonObject.put("productId", outSystemTableDO.getPageDataId());
        }
        //        jsonObject.put("serialNum", dataProcessSaveReqVO.getSerialNum());
        JSONObject filteredJsonObject = new JSONObject();
        for (String key : jsonObject.keySet()) {
            // 检查键名是否包含 "CHILD"
            if (!(key.contains("CHILD") || key.contains("ID_"))) {
                // 如果不包含，则将该键值对添加到新的 JSONObject 中
                filteredJsonObject.put(key, jsonObject.get(key));
            }
        }

        LogHelper.outPutLog("调用泛微---%s---%s---", fwSaveData, dataProcessSaveReqVO);
        String data = FlowParamUtl.reqestParamAgain(filteredJsonObject, dataProcessSaveReqVO.getWorkFlowId(), dataProcessSaveReqVO.getUserId(), JSON.toJSONString(otherParams), dataProcessSaveReqVO);
        if (data != null) {
            JSONObject result = JSON.parseObject(data);
            if ("SUCCESS".equals(result.getString("code"))) {
                String successDate = result.getString("data");
                JSONObject requestIdJson = JSON.parseObject(successDate);
                String requestId = requestIdJson.getString("requestid");
                outSystemTableDO.setFlowInstanceId(requestId);

                // 保存业务数据到快照
                logInfoDO.setRequestId(requestId);
                logInfoDO.setSerialNum(dataProcessSaveReqVO.getSerialNum());
                logInfoDO.setWorkFlowId(dataProcessSaveReqVO.getWorkFlowId());
                logInfoMapper.insert(logInfoDO);
                return successDate;
            } else {
                outSystemTableDO.setResultStatus(3);
                outSystemTableMapper.insert(outSystemTableDO);
                throw  exception(500, data);
            }
        }
        return null;
    }

    /**
     * 流程日志记录保存
     *
     * @param param
     * @param requestId
     * @param dataProcessSaveReqVO
     */
    private void okHttpClient(String requestId, DataProcessSaveReqVO dataProcessSaveReqVO,LowCodeParam param) {
        // 调完泛微之后需要调一个业务系统的接口，保存一下流程的数据。
        // 流程日志记录保存：业务系统接口  /ibps/fwoa/process/saveProjFlowInfo
        //String  url = "http://10.0.27.76:4010/ibps/fwoa/process/saveProjFlowInfo";
//        String  url = "http://172.16.11.90:4042/ibps/fwoa/process/saveProjFlowInfo";
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (StringUtil.isNotBlank(dataProcessSaveReqVO.getBusinessParameter())){
            JSONObject jsonObject = JSON.parseObject(dataProcessSaveReqVO.getBusinessParameter());
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("BUSINESS_ID")) {
                    Object value = entry.getValue();
                    if (value instanceof List) {
                        JSONArray jsonArray = JSONArray.parseArray(String.valueOf(value));
                        map.put("BUSINESS_ID", jsonArray.get(0));
                        break;
                    }
                }
            }
        }
        // 业务主键id
        map.put("PROJ_ID", dataProcessSaveReqVO.getYwbm());
        // 流程id  -- 流程定义id  flowid
        map.put("INSTANCE_ID", dataProcessSaveReqVO.getWorkFlowId());
        // 发起人
        map.put("START_USER", dataProcessSaveReqVO.getWorkNo());
        // 流程状态  固定传2
        map.put("INSTANCE_STATE", "2");
        map.put("INITIAL_DATE", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 流程编号 --流程实例id  requestid
        map.put("PROCESS_NUMBER", requestId);
        // 流程标题
        map.put("INSTANCE_TITLE", dataProcessSaveReqVO.getRequestName());

        if(StringUtils.isNotEmpty(dataProcessSaveReqVO.getContId())){
            map.put("CONT_ID", dataProcessSaveReqVO.getContId());
        }

        if(StringUtils.isNotEmpty(dataProcessSaveReqVO.getReciptNumber())){
            map.put("RECEIPT_NUMBER", dataProcessSaveReqVO.getReciptNumber());
        }

        if(StringUtils.isNotEmpty(dataProcessSaveReqVO.getHkdId())){
            map.put("HKD_ID", dataProcessSaveReqVO.getHkdId());
        }

        if(StringUtils.isNotEmpty(dataProcessSaveReqVO.getProdId())){
            map.put("PROD_ID", dataProcessSaveReqVO.getProdId());
        }
        //查询配置信息
        Long serviceId = param.getServiceId();
        ModuleCache api = cacheComponent.getModuleCache(serviceId, true);
        Long moduleId = api.getModuleId();
        ModuleInfoDO moduleInfoDO = moduleInfoMapper.selectById(moduleId);
        String flowLogParams = moduleInfoDO.getFlowLogParams();
        if(StringUtils.isNotEmpty(flowLogParams)){
            //解析数据模型配置
            saveModelConfig(flowLogParams,map,param);
        }

        String json = JSON.toJSONString(map);
        LogHelper.outPutLog("调用业务系统日志---%s---", json);
        io.reactivex.rxjava3.core.Observable<String> observable = Observable.fromCallable(() -> {
            Request request = new Request.Builder()
                    .url(serviceDataSaveUrl)
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

    private void saveModelConfig(String flowLogParams, Map<String, Object> map, LowCodeParam param) {
        try{
            Map<String, Object> body = param.getParams();
            JSONObject jsonObject = JSONObject.parseObject(flowLogParams);
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                String[] parts = value.split("#");
                StringBuffer sb = new StringBuffer();
                for (String part : parts) {
                    if (!part.isEmpty()) {
                        body.forEach((k, v) -> {
                            if (!k.startsWith(LowCodeCacheServiceImpl.CHILD_FLAG)) {
                                Map<String, Object> vMap = BeanUtil.beanToMap(v);
                                if(CollUtil.isNotEmpty(vMap)){
                                    vMap.forEach((k1,v1) ->{
                                        if(k1.equalsIgnoreCase(part + "_" + k)){
                                            sb.append(String.valueOf(v1));
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
                if(StringUtils.isNotEmpty(sb.toString())){
                    map.put(key, sb.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}