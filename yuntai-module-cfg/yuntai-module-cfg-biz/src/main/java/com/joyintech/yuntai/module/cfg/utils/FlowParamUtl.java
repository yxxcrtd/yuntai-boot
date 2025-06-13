package com.joyintech.yuntai.module.cfg.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.alibaba.fastjson.JSONObject;
import com.joyintech.yuntai.module.cfg.loginfo.LogHelper;
import com.joyintech.yuntai.module.cfg.openapi.dto.DataProcessSaveAgainReqVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.DataProcessSaveReqVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.NoticeTypeSaveReqVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.PrintFormDataCommonReqVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.WorkflowRequestTableField;


public class FlowParamUtl {

//    @Value("${external.spk:''}")
//    private static String spk;

//    @Value("${external.HOST:''}")
//    private static String HOST;

    private static final String spk = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsP+XANYL9cKf064lDdv6iByV5ihL2Zp6GjA2gIEXlKMCg1mELFuU/G+UL70LVG94WiTn6PUpsJ4zGFwMGwyvKIVXj3xvPUOUYn/xvtO3L8BzTSDd/bEaljmswD0/NSbdAZFYqCNhHwAUO2TEVJhehWMBGAQutlb4CdozcVH2Iok0ZSKewWr5TTpz1cwP7q6kGS3uX+WCKxliuuCRK3FnZGB+ptFv62SVeTxlMgUEukpfjDxH2yJtgSIsEks1kG8cJziakkc9NhqHYQmOM3PQfpVXlIAqghpOQ/PQVJIk3uNiovGtVxWE7P6JHgwWy9WbJs046MBBYHjYHXxcpf3NcwIDAQAB";

    public static<T> String reqestParam(JSONObject json, String workflowId, String userId, String otherParams, DataProcessSaveReqVO dataProcessSaveReqVO) throws IllegalAccessException {
        String res = "";
        String token = IdentityVerifyUtil.getToken();
        if (token == null) {
            return "";
        }
        String url = ConfigUtil.HOST + "/api/workflow/paService/doCreateRequest";
        HttpManager http = new HttpManager();
        Map<String, String> heads =  IdentityVerifyUtil.getHttpHeads(token,userId,spk);
        Map<String, String> params = new HashMap<>();
        params.put("requestName", dataProcessSaveReqVO.getRequestName()); // 流程名称
        params.put("mainData", getFormMainData(json));   // 业务入参
        params.put("detailData","");
        params.put("workflowId", workflowId);   // 流程ID
        params.put("remark", dataProcessSaveReqVO.getRemark());
        params.put("otherParams", otherParams);   // 其他参数
        params.put("requestLevel", dataProcessSaveReqVO.getRequestLevel());
        LogHelper.outPutLog(params.toString());
        try {
            res = http.postDataSSL(url, params, heads);
            System.out.println(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static<T> String reqestParamAgain(JSONObject json, String workflowId, String userId, String otherParams, DataProcessSaveAgainReqVO dataProcessSaveReqVO) throws IllegalAccessException {
        String res = "";
        String token = IdentityVerifyUtil.getToken();
        if (token == null) {
            return "";
        }
        String url = ConfigUtil.HOST + "/api/workflow/paService/submitRequest";
        HttpManager http = new HttpManager();
        Map<String, String> heads =  IdentityVerifyUtil.getHttpHeads(token,userId,spk);
        Map<String, String> params = new HashMap<>();
        params.put("mainData", getFormMainData(json));   // 业务入参
        params.put("detailData","");
        params.put("remark", dataProcessSaveReqVO.getRemark());
        params.put("otherParams", otherParams);   // 其他参数
        params.put("requestId", String.valueOf(dataProcessSaveReqVO.getRequestId()));
        LogHelper.outPutLog(params.toString());
        try {
            res = http.postDataSSL(url, params, heads);
            System.out.println(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 主表数据
     *
     * 附件上传 包含base64, http等
     * 包含浏览框数据，单行文本数据
     * @return
     */
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

    public static String convertCamelToDatabase(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelCaseName.length(); i++) {
            char currentChar = camelCaseName.charAt(i);
            // Check if the character is uppercase
            if (Character.isUpperCase(currentChar)) {
                // Append underscore if it's not the first character
                if (i > 0) {
                    result.append('_');
                }
                // Convert uppercase character to lowercase and append
                result.append(Character.toUpperCase(currentChar));
            } else {
                // Append the character as is
                result.append(currentChar);
            }
        }
        return result.toString().toUpperCase();
    }

    /**
     * 打印数据推送
     */
    public static<T> String requestPrintData(NoticeTypeSaveReqVO noticeTypeSaveReqVO,
            PrintFormDataCommonReqVO printFormDataCommonReqVO) throws IllegalAccessException {
        String res = "";
        String token = IdentityVerifyUtil.getToken();
        if (token == null) {
            return "";
        }
        String url = ConfigUtil.HOST + "/api/workflow/paService/submitRequest";
        HttpManager http = new HttpManager();
        Map<String, String> heads =  IdentityVerifyUtil.getHttpHeads(token,noticeTypeSaveReqVO.getUserId(),spk);
        Map<String, String> params = new HashMap<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(printFormDataCommonReqVO);
        params.put("mainData", getFormMainData(jsonObject));   // 业务入参
        params.put("requestId", noticeTypeSaveReqVO.getRequestid());
        LogHelper.outPutLog("个性化打印调用泛微系统接口入参为:---%s--",params.toString());
        try {
            res = http.postDataSSL(url, params, heads);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 明细数据
     * @return
     */
//    public static<T> String getFormDetailData(T entity) throws IllegalAccessException {
//        List<WorkflowDetailTableInfoEntity> details = new ArrayList<>();
//
//        //明细信息
//        WorkflowDetailTableInfoEntity detail1 = new WorkflowDetailTableInfoEntity();
//        detail1.setTableDBName("PROJ_BASIC_INFO_FLOW");
//
//        //明细数据
//        List<WorkflowRequestTableRecord> detailRows = new ArrayList<>();
//        WorkflowRequestTableRecord row1 = new WorkflowRequestTableRecord();
//        //明细行数据
//        List<WorkflowRequestTableField> rowDatas = new ArrayList<>();
//        // 使用反射获取字段名和字段值
//        Class<?> clazz = entity.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            //单行文本字段
//            WorkflowRequestTableField field1 = new WorkflowRequestTableField();
//            field.setAccessible(true); // 设置为可访问
//
//            String fieldName = field.getName();
//            Object fieldValue = field.get( entity);
//            System.out.println(fieldName + ": " + fieldValue);
//
//            field1.setFieldName(fieldName);
//            field1.setFieldValue(fieldValue + "");
//            rowDatas.add(field1);
//        }
//
//        row1.setRecordOrder(0);
//        row1.setWorkflowRequestTableFields(rowDatas);
//        detailRows.add(row1);
//
//        detail1.setWorkflowRequestTableRecords(detailRows);
//        details.add(detail1);
//        return JSONObject.toJSONString(details);
//    }

//    public static  <E extends DataEntity, D extends TableDaoImpl<? extends BaseMapper<E>, E>,T> void handleParams(
//            D dao,
//            E entity,
//            String msg,
//            boolean throwException
//    ) throws Exception {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<WorkflowRequestTableField> detailTableInfoEntityList = objectMapper.readValue(msg, new TypeReference<List<WorkflowRequestTableField>>() {});
//
//        for (WorkflowRequestTableField item : detailTableInfoEntityList) {
//            Map<String,Object> map = new HashMap<>();
//            map.put(item.getFieldName(),item.getFieldValue());
//            E entityFromMap = (E) createEntityFromMap(entity.getClass(), map);
//            dao.saveOrUpdate(entityFromMap);
//        }
//    }

//    public static <T> T createEntityFromMap(Class<T> clazz, Map<String, Object> fieldMap) throws Exception {
//        T entity = clazz.getDeclaredConstructor().newInstance();
//        Method[] methods = clazz.getMethods();
//        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
//            String fieldName = entry.getKey();
//            Object fieldValue = entry.getValue();
//
//            Field field = clazz.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
//
//            for (Method method : methods) {
//                if (method.getName().equals(setterName)) {
//                    // 检查 setter 方法的参数是否与属性类型匹配
//                    Class<?>[] parameterTypes = method.getParameterTypes();
//                    if (parameterTypes.length == 1 && parameterTypes[0].isAssignableFrom(fieldName.getClass())) {
//                        try {
//                            method.invoke(entity,fieldValue);
//                        } catch (Exception e) {
//                            System.out.println("Failed to invoke setter method: " + e.getMessage());
//                        }
//                    }
//                }
//            }
//
//        }
//
//        return entity;
//    }
}
