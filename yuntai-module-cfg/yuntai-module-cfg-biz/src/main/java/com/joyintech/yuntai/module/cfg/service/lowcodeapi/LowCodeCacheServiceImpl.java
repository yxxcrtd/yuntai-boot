package com.joyintech.yuntai.module.cfg.service.lowcodeapi;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.SERVICE_EVENT_UN_PASS;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.SERVICE_EXECUTE_ERROR;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.SERVICE_NOT_EXISTS;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.type.JdbcType;
import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.yulichang.toolkit.SpringContentUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum;
import com.joyintech.yuntai.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.joyintech.yuntai.framework.common.pojo.LowCodeParam;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.QueryField;
import com.joyintech.yuntai.framework.mybatis.core.util.DBDynamicSqlExecutorUtils;
import com.joyintech.yuntai.framework.mybatis.core.util.MyBatisUtils;
import com.joyintech.yuntai.framework.security.core.util.SecurityFrameworkUtils;
import com.joyintech.yuntai.framework.web.core.util.WebFrameworkUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleCache;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleRelationFieldCache;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleSqlCache;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.PageExtendEventCache;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleTableSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.TableField;
import com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo.ProcessDataPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo.ProcessDataSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.fileinfo.FileInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.loginfo.LogInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable.OutSystemTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnode.ProcessNodeDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduletable.ModuleTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.outsystemtable.OutSystemTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.processnode.ProcessNodeMapper;
import com.joyintech.yuntai.module.cfg.enums.CfgActionTypeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgApiTypeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgCallTypeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgModuleTypeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgOperateEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgRunTimeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgSystemFieldEnum;
import com.joyintech.yuntai.module.cfg.loginfo.LogHelper;
import com.joyintech.yuntai.module.cfg.openapi.dto.DataProcessSaveReqVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.ViewAssetMemberVO;
import com.joyintech.yuntai.module.cfg.service.fileinfo.FileInfoService;
import com.joyintech.yuntai.module.cfg.service.processdata.ProcessDataService;
import com.joyintech.yuntai.module.cfg.service.processdesign.ProcessDesignService;
import com.joyintech.yuntai.module.cfg.service.processdesign.ProcessDesignServiceImpl;
import com.joyintech.yuntai.module.system.api.user.SysUserApi;
import com.joyintech.yuntai.module.system.api.user.dto.ApiSysUser;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 索引定义 Service 接口
 *
 * @author 兆尹云台
 */
@Slf4j
@Service("lowCodeCacheService")
public class LowCodeCacheServiceImpl implements LowCodeService {

    private static final Gson gson = new Gson();
    private static final String SINGLE_RESULT = "singleResult";
    public static final String ATTACHMENT_LIST = "attachmentList";
    public static final String FLOW_ID = "flowId";
    public static final String NODE_ID = "nodeId";
    public static final String FLOW_REQUEST_ID = "flowRequestId";
    public static final String MODULE_CACHE_API = "moduleCacheApi";
    public static final String PARAMS = "params";
    public static final String SHOW_VALUE = "SHOW_VALUE";
    public static final String SHOW_TEXT = "SHOW_TEXT";
    public static final String CONT_ID = "CONT_ID";
    public static final String RECEIPT_NUMBER = "RECEIPT_NUMBER";
    public static final String HKD_ID = "HKD_ID";
    public static final String PROD_ID = "PROD_ID";

    @Resource
    private DBDynamicSqlExecutorUtils executorUtils;
    @Resource
    private ModuleCacheComponent cacheComponent;
    @Resource
    private ModuleTableMapper moduleTableMapper;

    @Resource
    private ProcessDesignService processDesignService;

    @Resource
    private ProcessDataService processDataService;

    @Resource
    private OutSystemTableMapper outSystemTableMapper;

    @Resource
    private FileInfoService fileInfoService;

    @Resource
    private SysUserApi sysUserApi;

    @Resource
    private ProcessNodeMapper processNodeMapper;

    @Value("${fanweioa.murl}")
    private String fanweioaMurl;

    @Value("${fanweioa.url}")
    private String fanweioaUrl;

    @Value("${fanwei.callbackUrl}")
    private String callbackUrl;

    private static final String FIELD_FORMAT = "%s_%d";
    // TODO：暂时因时间问题，只做oracle的修改，不考虑兼容性
    private static final String ID_FORMAT_ONE = "ID_%s";
    @SuppressWarnings("unused")
    private static final String ID_FORMAT_TWO = "id_%s";
    public static final String CHILD_FLAG = "list_";
    private static final String MAPP = "BUSINESS_ID_";
    private static final String MAPPS = "BUSSINESS_ID_";
    private static final String CHILD = "childId";
    String PK_PREFIXS_ONE = "ID" + "_";
    String PK_PREFIXS_TWO = "id" + "_";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object handle(LowCodeParam param, ModuleCache api) {
        if (Objects.isNull(api)) {
            throw exception(SERVICE_NOT_EXISTS);
        }
        Object result = null;
        EventResult before = this.beforeEvent(param, api);
        if (!before.isSuccess()) {
            throw exception(SERVICE_EVENT_UN_PASS,CollUtil.join(before.getErrorMsg(),"\n"));
        }
        if (api.getServiceCode().endsWith(CfgApiTypeEnum.CREATE.getCode())) {
            // result = org.springframework.util.ObjectUtils.nullSafeEquals(param.getPageVersion(), "v2") ? this.create2(param, api) : this.create(param, api);
            result = this.create2(param, api);
        } else if (api.getServiceCode().endsWith(CfgApiTypeEnum.UPDATE.getCode())) {
            // result = org.springframework.util.ObjectUtils.nullSafeEquals(param.getPageVersion(), "v2") ? this.update2(param, api) : this.update(param, api);
            result = this.update2(param, api);
        } else if (api.getServiceCode().endsWith(CfgApiTypeEnum.DELETE.getCode())) {
            // result = org.springframework.util.ObjectUtils.nullSafeEquals(param.getPageVersion(), "v2") ? this.delete2(param, api) : this.delete(param, api);
            result = this.delete2(param, api);
        } else if (api.getServiceCode().endsWith(CfgApiTypeEnum.MAPPING_GET_BY_ID.getCode())) {
            result = this.mappingGet(param, api);
        } else if (api.getServiceCode().endsWith(CfgApiTypeEnum.GET_BY_ID.getCode())) {
           if (null != param.getMapped() && param.getMapped()) {
               result = this.getMapped(param, api);
           } else {
                // result = org.springframework.util.ObjectUtils.nullSafeEquals(param.getPageVersion(), "v2") ? this.get2(param, api) : this.get(param, api);
               result = this.get2(param, api);
           }
        } else if (api.getServiceCode().endsWith(CfgApiTypeEnum.PAGE_LIST.getCode())) {
            result = this.getPage(param, api);
        } else if (api.getServiceCode().endsWith(CfgApiTypeEnum.CHILD_LIST.getCode())) {
            result = this.childList(param, api);
        } else if (api.getServiceCode().endsWith(CfgApiTypeEnum.LIST.getCode())) {
            result = this.list(param, api);
        } else {
            throw exception(SERVICE_NOT_EXISTS);
        }
        EventResult after = this.afterEvent(param, api);
        if (!after.isSuccess()) {
            throw exception(SERVICE_EVENT_UN_PASS.getCode(),CollUtil.join(after.getErrorMsg(),"\n"));
        }
        return result;

    }

    /**
     * 前置
     *
     * @param reqVO
     */
    private EventResult beforeEvent(LowCodeParam reqVO, ModuleCache api) {
        List<PageExtendEventCache> beforeList = api.getExtendEventList().stream().filter(e -> e.getRunTime().equals(CfgRunTimeEnum.LOAD_BEFORE.getCode())).collect(Collectors.toList());
        List<PageExtendEventCache> eventList = filterEventList(reqVO,beforeList);
        EventResult result = new EventResult();
        if (CollUtil.isNotEmpty(eventList)) {
            for (PageExtendEventCache event : eventList) {
                if (CfgCallTypeEnum.METHOD.getCode().equalsIgnoreCase(event.getCallType())) {
                    try{
                        methodInvoke(reqVO, event.getInterfaceName(),event.getMethodName());
                    }catch (Exception e){
                        String str = String.format("扩展事件%s.%s校验不通过或者执行失败", event.getInterfaceName(),event.getMethodName());
                        if (event.getIsIntercept()) {
                            result.getErrorMsg().add(str);
                        }else{
                            result.getWarnMsg().add(str);
                        }
                        log.error(str,e);
                    }
                }
                if (CfgCallTypeEnum.INTERFACE.getCode().equalsIgnoreCase(event.getCallType())) {
                    //TODO
                }
            }
        }
        return result;
    }

    /**
     * 后置事件
     *
     * @param reqVO
     */
    private EventResult afterEvent(LowCodeParam reqVO, ModuleCache api) {
        List<PageExtendEventCache> beforeList = api.getExtendEventList().stream().filter(e -> e.getRunTime().equals(CfgRunTimeEnum.LOAD_AFTER.getCode())).collect(Collectors.toList());
        List<PageExtendEventCache> eventList = this.filterEventList(reqVO,beforeList);
        EventResult result = new EventResult();
        if (CollUtil.isNotEmpty(eventList)) {
            for (PageExtendEventCache event : eventList) {
                if (CfgCallTypeEnum.METHOD.getCode().equalsIgnoreCase(event.getCallType())) {
                    try{
                        methodInvoke(reqVO, event.getInterfaceName(),event.getMethodName());
                    }catch (Exception e){
                        String str = String.format("扩展事件%s.%s校验不通过或者执行失败", event.getInterfaceName(),event.getMethodName());
                        if (event.getIsIntercept()) {
                            result.getErrorMsg().add(str);
                        }else{
                            result.getWarnMsg().add(str);
                        }
                    }
                }
                if (CfgCallTypeEnum.INTERFACE.getCode().equalsIgnoreCase(event.getCallType())) {
                    //TODO
                }
            }
        }
        return result;
    }

    private List<PageExtendEventCache> filterEventList(LowCodeParam reqVO,List<PageExtendEventCache> extendEvents){
        if (CollUtil.isEmpty(extendEvents)) {
            Map<String, List<PageExtendEventCache>> map = extendEvents.stream().collect(Collectors.groupingBy(item ->
                    item.getServiceId() + (Objects.isNull(item.getPageId()) ? "" : String.valueOf(item.getPageId())) + ((StrUtil.isNotEmpty(item.getApiCode())) ? item.getApiCode() : "")));
            return map.get(reqVO.getServiceId() + (Objects.isNull(reqVO.getPageId()) ? "" : String.valueOf(reqVO.getPageId())) + ((StrUtil.isNotEmpty(reqVO.getPageApiCode())) ? reqVO.getPageApiCode() : ""));
        }
        return Lists.newArrayList();
    }

    /**
     * 新建
     *
     * @param reqVO
     * @return
     */
    protected Object create(LowCodeParam reqVO, ModuleCache api) {
        List<ModuleSqlCache> list = api.getSqlList();
        Map<Long, List<ModuleSqlCache>> map = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getModuleTableId));
        List<ModuleRelationFieldCache> relationFields = api.getRelationList();
        List<Map<String, Map<String, Object>>> childList = new ArrayList<>();
        List<Map<String, Map<String, Object>>> subChildList = new ArrayList<>();
        Map<String, Map<String, Object>> rootMap = new HashMap<>();
        Map<String, Object> body = reqVO.getParams();
        this.handleData(body, childList, rootMap, relationFields, new ArrayList<>(), OperateTypeEnum.CREATE, subChildList);
        AtomicReference<Object> insert = new AtomicReference<>("");
        if (CollUtil.isNotEmpty(list)) {
            rootMap.forEach((key, value) -> {
                if (!ATTACHMENT_LIST.equals(key) && canConvertToLongUsingRegex(key)) {
                    List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                    if (CollUtil.isNotEmpty(sqlList)) {
                        this.setSaveParams(value, api);
                        if("MOD_CONT_GUAR_PLEDGE_OWN_BZ".equals(sqlList.get(0).getTableName())){
                            value.put("TYPE_" + sqlList.get(0).getModuleTableId(),"ensure");
                        }else if("MOD_CONT_GUAR_PLEDGE_OWN_DY".equals(sqlList.get(0).getTableName())){
                            value.put("TYPE_" + sqlList.get(0).getModuleTableId(),"mortgage");
                        }
                        Object insert1 = executorUtils.insert(sqlList.get(0).getActionSql(), value);
                        if (null != sqlList.get(0).getIsMain() && sqlList.get(0).getIsMain()) {
                            insert.set(insert1);
                        }
                    }
                }
            });
            if (CollUtil.isNotEmpty(childList)) {
                childList.forEach(childMap -> {
                    childMap.forEach((key, value) -> {
                        if (!ATTACHMENT_LIST.equals(key) && canConvertToLongUsingRegex(key)) {
                            List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                            if (CollUtil.isNotEmpty(sqlList)) {
                                this.setSaveParams(value, api);
                                executorUtils.insert(sqlList.get(0).getActionSql(), value);
                            }
                        }
                    });
                });
            }
        }

        // 保存日志流程数据
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(body);
            this.saveLogProcessData(reqVO, api.getModuleTableId(), jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Object id = insert.get();

        // 附件（新建页面）
        if(body.containsKey(String.valueOf(api.getModuleTableId()))){
            List<Map> attachmentList = (List<Map>) body.get(ATTACHMENT_LIST);
            if(attachmentList!=null && !attachmentList.isEmpty()){
                for(Map dto : attachmentList){
                    dto.put("pageId", String.valueOf(id));
                    if (reqVO.getFlowInfo() != null) {
                        dto.put("flowId", reqVO.getFlowInfo().get("flowId"));
                        if (ObjectUtils.isNotEmpty(reqVO.getFlowInfo().get("flowNodeId"))) {
                            dto.put("flowNodeId", reqVO.getFlowInfo().get("flowNodeId"));
                        }

                    }
                }
                fileInfoService.updateBatch(attachmentList);
            }
        }

        return id;
    }

    public boolean canConvertToLongUsingRegex(String str) {
        String regex = "^[+-]?\\d+$";
        return str != null && str.matches(regex) && str.length() <= 19;
    }


    /**
     * 给初始字段赋值
     *
     * @param body
     * @param childList
     * @param rootMap
     * @param relationFields
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void handleData(Map<String, Object> body, List<Map<String, Map<String, Object>>> childList, Map<String, Map<String, Object>> rootMap,
                            List<ModuleRelationFieldCache> relationFields, List<Long> child, OperateTypeEnum operateType,
                            List<Map<String, Map<String, Object>>> subChildList) {
        body.forEach((key, value) -> {
            if (key.startsWith(CHILD_FLAG)) {
                String childKey = key.replace(CHILD_FLAG, "");
                child.add(Long.valueOf(childKey));
                List vlist = (List) value;
                if (CollUtil.isNotEmpty(vlist) && vlist.size() > 0) {//重新组织
                    vlist.forEach(v -> {
                        Map<String, Map<String, Object>> vmap = new HashMap<>();
                        Map<String, Object> v1 = BeanUtil.beanToMap(v); //this.setChildMap(BeanUtil.beanToMap(v));
                        v1.forEach((k, v2) -> {
                            // List<Map<String, Map<String, Object>>> childList1 = new ArrayList<>();
                            if (k.startsWith(CHILD_FLAG)) {
                                String childKey1 = key.replace(CHILD_FLAG, "");
                                child.add(Long.valueOf(childKey1));
                                List vlist1 = (List) value;
                                if (CollUtil.isNotEmpty(vlist1) && vlist1.size() > 0) { // 重新组织
                                    vlist1.forEach(v11 -> {
                                        Map<String, Map<String, Object>> vmap1 = new HashMap<>();
                                        Map<String, Object> v12 = this.setChildMap(BeanUtil.beanToMap(v11));
                                        v12.forEach((k1, v21) -> {
                                            Map<String, Object> endMap1 = BeanUtil.beanToMap(v21);
                                            endMap1.put(String.format(ID_FORMAT_ONE, k1), IdWorker.getId());
                                            vmap1.put(k1, endMap1);
                                        });
                                    });
                                }
                            } else {
                                Map<String, Object> endMap = BeanUtil.beanToMap(v2);
                                endMap.put(String.format(ID_FORMAT_ONE, k), IdWorker.getId());
                                vmap.put(k, endMap);
                            }
                        });

                        Map<String, Object> newMap = new HashMap<>();
                        newMap.put("sub", subChildList);
                        vmap.put(key, newMap);

                        childList.add(vmap);
                    });
                }
            } else {
                AtomicBoolean mainFalg = new AtomicBoolean(false);
                Map<String, Object> v = BeanUtil.beanToMap(value);
                v.forEach((mainK,mainV) ->{
                    if(ObjectUtils.isNotEmpty(mainV)){
                        mainFalg.set(true);
                    }
                });
                if(!v.isEmpty() && mainFalg.get()){
                    if (operateType == OperateTypeEnum.CREATE) {
                        v.put(String.format(ID_FORMAT_ONE, key), IdWorker.getId());
                    }
                    rootMap.put(key, v);
                }
            }
        });
        Map<String, Map<String, Object>> allMap = new HashMap<>(rootMap);
        if (rootMap.size() > 1) {
            this.setRelationFieldValue(rootMap, allMap, relationFields);
        }
        if (CollUtil.isNotEmpty(child)) {
            childList.forEach(childMap -> {
                Map<String, Map<String, Object>> temp = new HashMap<>(rootMap);
                temp.putAll(childMap);
                this.setRelationFieldValue(childMap, temp, relationFields);
            });
        }
    }

    /**
     * 设置childMap
     *
     * @param v1
     * @return
     */
    private Map<String, Object> setChildMap(Map<String, Object> v1) {
        Set<String> keys = new HashSet<>();
        v1.keySet().forEach(k -> {
            String key = k.substring(k.lastIndexOf('_') + 1);
            if (StrUtil.isNumeric(key)) {
                keys.add(key);
            }
        });
        return this.setTableKey(keys, v1);
    }

    /**
     * 设置关联字段
     *
     * @param map
     * @param allMap
     */
    private void setRelationFieldValue(Map<String, Map<String, Object>> map, Map<String, Map<String, Object>> allMap, List<ModuleRelationFieldCache> rList) {
        if (CollUtil.isEmpty(rList)) {//没有关联关系说明是单表操作
            return;
        }
        Map<String, Object> tmp = new HashMap<>();
        if (CollUtil.isNotEmpty(rList)) {
            rList.forEach(r -> {
                Map<String, Object> rmap = allMap.get(String.valueOf(r.getRelationMoudleTableId()));
                Map<String, Object> mmap = allMap.get(String.valueOf(r.getModuleTableId()));
                if (CollUtil.isNotEmpty(mmap) ) {
                    String key = String.format(FIELD_FORMAT, r.getFieldName(), r.getModuleTableId());
                    Object val = mmap.get(key);
                    if (val != null) {
                        tmp.put(String.format(FIELD_FORMAT, r.getRelationFieldName(), r.getRelationMoudleTableId()), val);
                    } else if (tmp.get(key) != null) {
                        tmp.put(String.format(FIELD_FORMAT, r.getRelationFieldName(), r.getRelationMoudleTableId()), tmp.get(key));
                    }
                }
                if (CollUtil.isNotEmpty(rmap)) {
                    String key = String.format(FIELD_FORMAT, r.getRelationFieldName(), r.getRelationMoudleTableId());
                    Object val = rmap.get(key);
                    if (val != null) {
                        tmp.put(String.format(FIELD_FORMAT, r.getFieldName(), r.getModuleTableId()), val);
                    } else if (tmp.get(key) != null) {
                        tmp.put(String.format(FIELD_FORMAT, r.getFieldName(), r.getModuleTableId()), tmp.get(key));
                    }
                }
            });
            //处理没有覆盖到的关联字段
            rList.forEach(r -> {
                String key = String.format(FIELD_FORMAT, r.getFieldName(), r.getModuleTableId());
                String key1 = String.format(FIELD_FORMAT, r.getRelationFieldName(), r.getRelationMoudleTableId());
                if (tmp.get(key) == null) {
                    tmp.put(key, tmp.get(key1));
                }
                if (tmp.get(key1) == null) {
                    tmp.put(key1, tmp.get(key));
                }
            });
        }
        Map<Long, List<ModuleRelationFieldCache>> moduleMap = rList.stream().collect(Collectors.groupingBy(ModuleRelationFieldCache::getModuleTableId));
        Map<Long, List<ModuleRelationFieldCache>> relationModuleMap = rList.stream().collect(Collectors.groupingBy(ModuleRelationFieldCache::getModuleTableId));
        map.forEach((key, value) -> {
            if (CollUtil.isNotEmpty(rList)) {
                if (canConvertToLongUsingRegex(key)) {
                    List<ModuleRelationFieldCache> mList = moduleMap.get(Long.valueOf(key));
                    if (CollUtil.isNotEmpty(mList)) {
                        mList.forEach(m -> {
                            value.put(String.format(FIELD_FORMAT, m.getFieldName(), m.getModuleTableId()), tmp.get(String.format(FIELD_FORMAT, m.getRelationFieldName(), m.getRelationMoudleTableId())));
                        });
                    } else {
                        List<ModuleRelationFieldCache> m1List = relationModuleMap.get(Long.valueOf(key));
                        if (CollUtil.isNotEmpty(m1List)) {
                            m1List.forEach(m -> {
                                value.put(String.format(FIELD_FORMAT, m.getRelationFieldName(), m.getRelationMoudleTableId()), tmp.get(String.format(FIELD_FORMAT, m.getFieldName(), m.getModuleTableId())));
                            });
                        }
                    }
                }
            }
        });
    }

    /**
     * 更新接口
     *
     * @param reqVO
     */
    protected String update(LowCodeParam reqVO, ModuleCache api) {
        List<ModuleSqlCache> list = api.getSqlList();
        Map<Long, List<ModuleSqlCache>> map = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getModuleTableId));
        List<ModuleRelationFieldCache> relationFields = api.getRelationList();
        List<Map<String, Map<String, Object>>> childList = new ArrayList<>();
        List<Map<String, Map<String, Object>>> subChildList = new ArrayList<>();
        Map<String, Map<String, Object>> rootMap = new HashMap<>();
        List<Long> child = Lists.newArrayList();
        Map<String, Object> body = reqVO.getParams();
        this.handleData(body, childList, rootMap, relationFields, child, OperateTypeEnum.UPDATE, subChildList);
        Map<String, Object> mainMap = rootMap.get(String.valueOf(api.getModuleTableId()));

        this.deleteChild(mainMap, list, api, rootMap);

        if (CollUtil.isNotEmpty(list)) {
            rootMap.forEach((key, value) -> {
                if(!ATTACHMENT_LIST.equals(key)){
                    List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                    if (CollUtil.isNotEmpty(sqlList)) {
                        this.setSaveParams(value, api);
                        // 判断是否有ID，如没有，走新增
                        if (CollUtil.isNotEmpty(value)) {
                            String str = value.keySet().iterator().next();
                            int lastIndex = str.lastIndexOf('_');
                            String substring = str.substring(lastIndex + 1);
                            Object o = value.get(String.format(ID_FORMAT_ONE, substring));
                            if (o == null) {
                                value.put(String.format(ID_FORMAT_ONE, substring), IdWorker.getId());
                            }
                            for (ModuleSqlCache m : sqlList) {
                                if (o == null && m.getActionType().equals(CfgActionTypeEnum.INSERT.name())) {
                                    executorUtils.insert(m.getActionSql(), value);
                                }
                                if (o != null && m.getActionType().equals(CfgActionTypeEnum.UPDATE.name())) {
                                    executorUtils.update(m.getActionSql(), value);
                                }
                            }
                        }
                    }
                }
            });
            if (CollUtil.isNotEmpty(childList)) {
                childList.forEach(childMap -> {
                    childMap.forEach((key, value) -> {
                        if(!ATTACHMENT_LIST.equals(key)){
                            List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                            if (CollUtil.isNotEmpty(sqlList)) {
                                for (ModuleSqlCache m : sqlList) {
                                    if (m.getActionType().equals(CfgActionTypeEnum.INSERT.name())) {
                                        this.setSaveParams(value, api);
                                        executorUtils.insert(m.getActionSql(), value);
                                    }
                                }
                            }
                        }
                    });
                });
            }
        }

        if(body.containsKey(ATTACHMENT_LIST)) {
            List<Map> attachmentList = (List<Map>) body.get(ATTACHMENT_LIST);
            if (attachmentList != null && !attachmentList.isEmpty()) {
                for (Map dto : attachmentList) {
                    if(((Map) body.get(String.valueOf(api.getModuleTableId()))).containsKey(
                            PK_PREFIX_ONE + api.getModuleTableId())){
                        dto.put("pageId", ((Map) body.get(String.valueOf(api.getModuleTableId()))).get(
                                PK_PREFIX_ONE + api.getModuleTableId()));
                    }
                    if(((Map) body.get(String.valueOf(api.getModuleTableId()))).containsKey(
                            PK_PREFIX_TWO + api.getModuleTableId())){
                        dto.put("pageId", ((Map) body.get(String.valueOf(api.getModuleTableId()))).get(
                                PK_PREFIX_TWO + api.getModuleTableId()));
                    }
                    if (reqVO.getFlowInfo() != null) {
                        dto.put("flowId", reqVO.getFlowInfo().get("flowId"));
                        if(ObjectUtils.isNotEmpty(reqVO.getFlowInfo().get("flowNodeId"))){
                            dto.put("flowNodeId", reqVO.getFlowInfo().get("flowNodeId"));
                        }
                    }
                }
                fileInfoService.updateBatch(attachmentList);
            }
        }

        // 保存日志流程数据
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(body);
            this.saveLogProcessData(reqVO, api.getModuleTableId(), jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * 删除子表对应的数据
     *
     * @param mainMap
     * @param list
     */
    private void deleteChild(Map<String, Object> mainMap, List<ModuleSqlCache> list, ModuleCache api, Map<String, Map<String, Object>> rootMap) {
        Map<String, List<Long>> idsMap = new HashMap<>();
        for (ModuleSqlCache m : list) {
            if (m.getActionType().equals(CfgActionTypeEnum.CHILD_SELECT_LIST.name())) {
                // 获取非主表的关联数据字段
                Map<Long, List<ModuleRelationFieldCache>> ModuleRelation = api.getRelationList().stream()
                        .collect(Collectors.groupingBy(ModuleRelationFieldCache::getModuleTableId));
                List<ModuleRelationFieldCache> moduleRelationFieldCache = ModuleRelation.get(m.getModuleTableId());
                Map<String, Object> mainMaps = new HashMap<>(mainMap);
                if (CollUtil.isNotEmpty(moduleRelationFieldCache)) {
                    Set<Long> longs = moduleRelationFieldCache.stream().map(ModuleRelationFieldCache::getRelationMoudleTableId).collect(Collectors.toSet());
                    longs.forEach(s -> {
                        if (!Objects.equals(s, api.getModuleTableId())) {
                            if(rootMap.get(String.valueOf(s)) != null){
                                mainMaps.putAll(rootMap.get(String.valueOf(s)));
                            }
                        }
                    });
                };
                Map<String, Object> tempQuery = BeanUtil.toBean(mainMaps, Map.class);
                this.setQeueryParams(tempQuery, api);
                this.setIdList(tempQuery);
                //TODO: 因时间问题，暂时先做判断处理
                AtomicBoolean deleteStatus = new AtomicBoolean(false);
                if (CollUtil.isNotEmpty(tempQuery)) {
                    tempQuery.forEach((k, v) -> {
                        // 检查键是否以指定前缀开头
                        if (k.startsWith("ID") || k.startsWith("id")) {
                            deleteStatus.set(true);
                        }
                    });
                }
                List<Map<String, Object>> result = new ArrayList<>();
                if (deleteStatus.get()) {
                    String actionSql = m.getActionSql();
                    if(StringUtils.isNotEmpty(actionSql)){
                        if(actionSql.contains("collection")){
                            String regex = "collection\\s*=\\s*\"(\\w+)\"";
                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(m.getActionSql());
                            if (matcher.find()) {
                                String collectionValue = matcher.group(1);
                                if (tempQuery.get(collectionValue) != null) {
                                    result = executorUtils.findListDataNoUpper(m.getActionSql(), tempQuery);
                                }
                            }
                        }else{
                            result = executorUtils.findListDataNoUpper(m.getActionSql(), tempQuery);
                        }
                    }
                }
                if (CollUtil.isNotEmpty(result)) {//不为空则执行删除
                    result.forEach(r -> {
                        // 处理别名，转换为 列名_模块表id 格式
                        processSelectResultKey(r, api);
                        List<String> ids1 = r.keySet().stream().filter(e -> e.startsWith(PK_PREFIX_ONE)).collect(Collectors.toList());
                        if (CollUtil.isNotEmpty(ids1)) {
                            ids1.forEach(id -> {
                                Long v = Long.valueOf((String) r.get(id));
                                String key = id.replace(PK_PREFIX_ONE, "");
                                if (idsMap.containsKey(key)) {
                                    idsMap.get(key).add(v);
                                } else {
                                    idsMap.put(key, Lists.newArrayList(v));
                                }
                            });
                        }

                        List<String> ids2 = r.keySet().stream().filter(e -> e.startsWith(PK_PREFIX_TWO)).collect(Collectors.toList());
                        if (CollUtil.isNotEmpty(ids2)) {
                            ids2.forEach(id -> {
                                Long v = Long.valueOf((String) r.get(id));
                                String key = id.replace(PK_PREFIX_TWO, "");
                                if (idsMap.containsKey(key)) {
                                    idsMap.get(key).add(v);
                                } else {
                                    idsMap.put(key, Lists.newArrayList(v));
                                }
                            });
                        }
                    });
                }
            }
        }
        if (CollUtil.isNotEmpty(idsMap)) {
            Map<Long, List<ModuleSqlCache>> map = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getModuleTableId));
            Map<String, Object> idInMap = new HashMap<>();
            idsMap.forEach((key, value) -> {
                if(!ATTACHMENT_LIST.equals(key)){
                    List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                    if (CollUtil.isNotEmpty(sqlList)) {
                        for (ModuleSqlCache m : sqlList) {
                            if (m.getActionType().equals(CfgActionTypeEnum.DELETE.name())) {
                                idInMap.clear();
                                idInMap.put(PK_PREFIX_ONE + key, value);
//                                idInMap.put(PK_PREFIX_TWO + key, value);
                                this.setDeleteParams(idInMap, api);
                                executorUtils.update(m.getActionSql(), idInMap);
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 删除
     *
     * @param reqVO
     */
    protected String delete(LowCodeParam reqVO, ModuleCache api) {
        this.setIdList(reqVO.getParams());
        List<ModuleSqlCache> list = api.getSqlList();
        Map<Long, List<ModuleSqlCache>> map = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getModuleTableId));
        Map<String, List<ModuleSqlCache>> actionType = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getActionType));
        List<ModuleSqlCache> getByIdList = actionType.get(CfgActionTypeEnum.GET_BY_ID.name());
        this.deleteChild(reqVO.getParams(), list, api, null);

        if(reqVO.getParams().containsKey(PK_PREFIX_ONE + api.getModuleTableId())){
            List<String> id = (List<String>) reqVO.getParams().get(PK_PREFIX_ONE + api.getModuleTableId());
            if(id!=null && !id.isEmpty()){
                fileInfoService.delAttachmentList(id.get(0));
            }
        }

        if(reqVO.getParams().containsKey(PK_PREFIX_TWO + api.getModuleTableId())){
            List<String> id = (List<String>) reqVO.getParams().get(PK_PREFIX_TWO + api.getModuleTableId());
            if(id!=null && !id.isEmpty()){
                fileInfoService.delAttachmentList(id.get(0));
            }
        }

        if (CollUtil.isNotEmpty(getByIdList)) {
            this.setQeueryParams(reqVO.getParams(), api);
            List<Map<String, Object>> mainList = executorUtils.findListDataNoUpper(getByIdList.get(0).getActionSql(), reqVO.getParams());
            if (CollUtil.isNotEmpty(mainList)) {
                Map<String, List<Object>> idsMap = new HashMap<>();
                if (CollUtil.isNotEmpty(mainList)) {//不为空则执行删除
                    mainList.forEach(r -> {
                        // 处理别名，转换为 列名_模块表id 格式
                        processSelectResultKey(r, api);
                        List<String> ids1 = r.keySet().stream().filter(e -> e.startsWith(PK_PREFIX_ONE)).collect(Collectors.toList());
                        if (CollUtil.isNotEmpty(ids1)) {
                            ids1.forEach(id -> {
                                Object v = r.get(id);
                                String key = id.replace(PK_PREFIX_ONE, "");
                                if (idsMap.containsKey(key)) {
                                    idsMap.get(key).add(v);
                                } else {
                                    idsMap.put(key, Lists.newArrayList(v));
                                }
                            });
                        }

                        List<String> ids2 = r.keySet().stream().filter(e -> e.startsWith(PK_PREFIX_TWO)).collect(Collectors.toList());
                        if (CollUtil.isNotEmpty(ids2)) {
                            ids2.forEach(id -> {
                                Object v = r.get(id);
                                String key = id.replace(PK_PREFIX_TWO, "");
                                if (idsMap.containsKey(key)) {
                                    idsMap.get(key).add(v);
                                } else {
                                    idsMap.put(key, Lists.newArrayList(v));
                                }
                            });
                        }
                    });
                    Map<String, Object> idInMap = new HashMap<>();
                    idsMap.forEach((key, value) -> {
                        if(!ATTACHMENT_LIST.equals(key)){
                            List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                            if (CollUtil.isNotEmpty(sqlList)) {
                                for (ModuleSqlCache m : sqlList) {
                                    if (m.getActionType().equals(CfgActionTypeEnum.DELETE.name())) {
                                        idInMap.clear();
                                        idInMap.put(PK_PREFIX_ONE + key, value);
                                        idInMap.put(PK_PREFIX_TWO + key, value);
                                        this.setDeleteParams(idInMap, api);
                                        executorUtils.update(m.getActionSql(), idInMap);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * 映射单条查询
     *
     * @param reqVO
     * @return
     */
    protected Map<String, Object> mappingGet(LowCodeParam reqVO, ModuleCache api) {
        this.setIdLists(reqVO.getParams());
        List<ModuleSqlCache> list = api.getSqlList();
        Map<String, List<ModuleSqlCache>> actionTypeMap = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getActionType));
        List<ModuleSqlCache> getById = actionTypeMap.get(CfgActionTypeEnum.MAPPING_GET_BY_ID.name());
        List<Map<String, Object>> objList = executorUtils.findListDataWithParse(getById.get(0).getActionSql(), reqVO.getParams());
        Map<String, Object> obj = null;
        if (CollUtil.isNotEmpty(objList)) {
            obj = objList.get(0);
        }
        AtomicReference<String> moduleTableId = new AtomicReference<>();
        processSelectResultKeys(obj, api, moduleTableId);
        if (Objects.isNull(obj)) {
            return new HashMap<>();
        }
        if(CollUtil.isNotEmpty(reqVO.getChildIdList())){
            obj.put("CHILD_ID",reqVO.getChildIdList());
        }
        Set<String> tableSet = new HashSet<>();
        obj.keySet().forEach(k -> {
            String []keys = k.split("_");
            tableSet.add(keys[keys.length - 1]);
        });
        Map<String, Object> result = this.setTableKey(tableSet, obj);
        List<ModuleSqlCache> childSelect = actionTypeMap.get(CfgActionTypeEnum.MAPPING_CHILD_SELECT_LIST.name());
        if (CollUtil.isNotEmpty(childSelect)) {
            for (ModuleSqlCache s : childSelect) {
                this.setIdLists(obj);
                // 遍历 sourceMap 并检查键是否包含 "child"
                Iterator<Map.Entry<String, Object>> iterator = reqVO.getParams().entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    String key = entry.getKey();
                    if (key.contains("CHILD")) {
                        obj.put(key, entry.getValue());
                    }
                }
                String regex = "collection\\s*=\\s*\"(\\w+)\"";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(s.getActionSql());
                if (matcher.find()) {
                    String collectionValue = matcher.group(1);
                    if (obj.get(collectionValue) != null) {
                        List<Map<String, Object>> childList = executorUtils.findListDataWithParse(s.getActionSql(), obj);
                        if (CollUtil.isNotEmpty(childList)) {
                            AtomicReference<String> moduleTableIds = new AtomicReference<>();
                            childList.forEach(r -> processSelectResultKeys(r, api, moduleTableIds));
                            // 映射模型子表的MODULE_TABLE_ID 转换为 数据模型子表的MODULE_TABLE_ID
                            if(StringUtils.isNotEmpty(moduleTableIds.get()) && CollUtil.isNotEmpty(childList)){
                                result.put(CHILD_FLAG + moduleTableIds.get(), childList);
                            }
                        }
                    }
                } else {
                    List<Map<String, Object>> childList = executorUtils.findListDataWithParse(s.getActionSql(), obj);
                    if (CollUtil.isNotEmpty(childList)) {
                        AtomicReference<String> moduleTableIds = new AtomicReference<>();
                        childList.forEach(r -> processSelectResultKeys(r, api, moduleTableIds));
                        // 映射模型子表的MODULE_TABLE_ID 转换为 数据模型子表的MODULE_TABLE_ID
                        result.put(CHILD_FLAG + moduleTableIds.get(), childList);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 单条查询
     *
     * @param reqVO
     * @return
     */
    @SuppressWarnings("unused")
    protected Object get(LowCodeParam reqVO, ModuleCache api) {
        this.setIdList(reqVO.getParams());
        Map<String, Object> parameterMap = this.processParameter(reqVO.getParams(), api);
        reqVO.getParams().putAll(parameterMap);
        this.setQeueryParams(reqVO.getParams(), api);
        List<ModuleSqlCache> list = api.getSqlList();
        Map<String, List<ModuleSqlCache>> actionTypeMap = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getActionType));
        List<ModuleSqlCache> getById = actionTypeMap.get(CfgActionTypeEnum.GET_BY_ID.name());
        List<Map<String, Object>> objList = executorUtils.findListDataNoUpper(getById.get(0).getActionSql(), reqVO.getParams());
        if (CollUtil.isEmpty(objList)) {
            return new HashMap<>();
        }
        // 是否单条查询
        Boolean isSingleResult = (Boolean) reqVO.getParams().get(SINGLE_RESULT);

        // 上次暂存数据
        String formData = this.findLogProcessFormData(reqVO, api.getModuleTableId());
        if(StringUtils.isNotBlank(formData) && false){
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
            Map<String, Object> result = null;
            try {
                result = objectMapper.readValue(formData, typeRef);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            if (BooleanUtil.isTrue(isSingleResult)) {
                return result;
            }

            List<Map<String, Object>> resultList = new ArrayList<>();
            return resultList.add(result);
        }

        // 附件信息
        List<FileInfoDO> mapList = new ArrayList<>();
        if(reqVO.getParams().containsKey(PK_PREFIX_ONE +api.getModuleTableId())){
            mapList = fileInfoService.findAttachmentList((List<String>) reqVO.getParams().get(PK_PREFIX_ONE +api.getModuleTableId()));
        }
        if(reqVO.getParams().containsKey(PK_PREFIX_TWO +api.getModuleTableId())){
            mapList = fileInfoService.findAttachmentList((List<String>) reqVO.getParams().get(PK_PREFIX_TWO +api.getModuleTableId()));
        }

        // 是否单条查询
        if (BooleanUtil.isTrue(isSingleResult)) {
            //解析文件信息
            mapList.forEach(maps -> {
                String flowNodeId = maps.getFlowNodeId();
                String uploadUserId = maps.getUploadUserId();
                if (StringUtils.isNotEmpty(flowNodeId)) {
                    List<ProcessNodeDO> processNodeDO = processNodeMapper.selectList(new QueryWrapper<ProcessNodeDO>()
                            .eq("out_process_node_id", flowNodeId)
                            .eq("deleted",0)
                    );
                    if(CollUtil.isNotEmpty(processNodeDO)){
                        maps.setFlowNodeText(processNodeDO.get(0).getName());
                    }
                }
                if(StringUtils.isNotEmpty(uploadUserId)){
                    ApiSysUser user = null;
                    user = sysUserApi.getUserByUsername(uploadUserId);
                    if(Optional.empty().isPresent()){
                        user = sysUserApi.getUser(uploadUserId);
                    }
                    if(Optional.ofNullable(user).isPresent()){
                        maps.setUploadUserText(user.getRealname());
                    }
                }
            });
            Map<String, Object> mainObj = objList.get(0);
            Map<String, Object> result = getSingleResult(api, mainObj, actionTypeMap, parameterMap);
            result.put(ATTACHMENT_LIST, mapList);
            return result;
        } else {
            List<Map<String, Object>> resultList = new ArrayList<>();
            for (Map<String, Object> mainObj : objList) {
                resultList.add(getSingleResult(api, mainObj, actionTypeMap, parameterMap));
            }
            return  resultList;
        }
    }

    /**
     * 别名转表字段名
     */
    private Map<String, Object> getSingleResult(ModuleCache api, Map<String, Object> mainObj,
                                                   Map<String, List<ModuleSqlCache>> actionTypeMap, Map<String, Object> parameterMap) {
        processSelectResultKey(mainObj, api);
        Set<String> tableSet = new HashSet<>();
        mainObj.keySet().forEach(k -> {
            String []keys = k.split("_");
            tableSet.add(keys[keys.length - 1]);
        });
        Map<String, Object> result = this.setTableKey(tableSet, mainObj);
        // 最后添加，1. 防止动态参数名和字段别名冲突 2. 防止参数被当做表返回
        mainObj.putAll(parameterMap);
        List<ModuleSqlCache> childSelect = actionTypeMap.get(CfgActionTypeEnum.CHILD_SELECT_LIST.name());
        if (CollUtil.isNotEmpty(childSelect)) {
            // 根据主表数据查询子表，不需要转换为list
            this.setIdList(mainObj);
            this.setQeueryParams(mainObj, api);
            for (ModuleSqlCache s : childSelect) {
                boolean b = checkParamsInMap(s.getActionSql(), mainObj);
                if(b){
                    List<Map<String, Object>> childList = executorUtils.findListDataNoUpper(s.getActionSql(), mainObj);
                    if (CollUtil.isNotEmpty(childList)) {
                        childList.forEach(r -> processSelectResultKey(r, api));
                        result.put(CHILD_FLAG + s.getModuleTableId(), childList);
                    }
                }
            }
        }
        return result;
    }

    public boolean checkParamsInMap(String sql,Map<String, Object> paramMap){
        Pattern pattern = Pattern.compile("<foreach.*?collection=\"(.*?)\".*?>");
        Matcher matcher = pattern.matcher(sql);

        while (matcher.find()) {
            String paramName = matcher.group(1);
            if (!paramMap.containsKey(paramName)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 映射后数据单条查询
     *
     * @param reqVO
     * @return
     */
    protected Object getMapped(LowCodeParam reqVO, ModuleCache api) {
        this.setIdList(reqVO.getParams());
        Map<String, Object> parameterMap = this.processParameter(reqVO.getParams(), api);
        reqVO.getParams().putAll(parameterMap);
        this.setQeueryParams(reqVO.getParams(), api);
        List<ModuleSqlCache> list = api.getSqlList();
        Map<String, List<ModuleSqlCache>> actionTypeMap = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getActionType));
        List<ModuleSqlCache> getById = actionTypeMap.get(CfgActionTypeEnum.GET_BY_ID.name());
        List<Map<String, Object>> objList = executorUtils.findListDataNoUpper(getById.get(0).getActionSql(), reqVO.getParams());
        if (CollUtil.isEmpty(objList)) {
            return new HashMap<>();
        }
        // 是否单条查询
        Boolean isSingleResult = (Boolean) reqVO.getParams().get(SINGLE_RESULT);
        if (BooleanUtil.isTrue(isSingleResult)) {
            Map<String, Object> mainObj = objList.get(0);
            return getMappedSingleResult(api, mainObj, actionTypeMap, parameterMap);
        } else {
            List<Map<String, Object>> mappedResultList = new ArrayList<>();
            for (Map<String, Object> mainObj : objList) {
                mappedResultList.add(getMappedSingleResult(api, mainObj, actionTypeMap, parameterMap));
            }
            return  mappedResultList;
        }
    }

    private Map<String, Object> getMappedSingleResult(ModuleCache api, Map<String, Object> mainObj,
                                                   Map<String, List<ModuleSqlCache>> actionTypeMap, Map<String, Object> parameterMap) {
        Map<String, Object> mappedResult = new HashMap<>();
        processSelectResultKey(mainObj, api, mappedResult);
        Set<String> tableSet = new HashSet<>();
        mainObj.keySet().forEach(k -> {
            String []keys = k.split("_");
            tableSet.add(keys[keys.length - 1]);
        });
        Map<String, Object> result = this.setTableKey(tableSet, mainObj);
        // 最后添加，1. 防止动态参数名和字段别名冲突 2. 防止参数被当做表返回
        mainObj.putAll(parameterMap);
        List<ModuleSqlCache> childSelect = actionTypeMap.get(CfgActionTypeEnum.CHILD_SELECT_LIST.name());
        if (CollUtil.isNotEmpty(childSelect)) {
            this.setIdList(mainObj);
            this.setQeueryParams(mainObj, api);
            for (ModuleSqlCache s : childSelect) {
                List<Map<String, Object>> childList = executorUtils.findListDataNoUpper(s.getActionSql(), mainObj);
                if (CollUtil.isNotEmpty(childList)) {
                    Map<String, Object> mappedChildren = new HashMap<>();
                    for (Map<String, Object> child : childList) {
                        processMappedChild(api, child, mappedChildren);
                    }
                    mappedResult.putAll(mappedChildren);
                    result.put(CHILD_FLAG + s.getModuleTableId(), childList);
                }
            }
        }
        return mappedResult;
    }

    private void processMappedChild(ModuleCache api, Map<String, Object> child, Map<String, Object> mappedChildren) {
        // 处理当前子表数据
        Map<String, Object> mappedChild = new HashMap<>();
        processSelectResultKey(child, api, mappedChild);
        // 转换后的数据 合并到 mappedChildren
        for (Map.Entry<String, Object> childEntry : mappedChild.entrySet()) {
            // 包含 相关key
            if (mappedChildren.containsKey(childEntry.getKey())) {
                List<Object> mappedChildList = (List<Object>) mappedChildren.get(childEntry.getKey());
                mappedChildList.add(childEntry.getValue());
            } else {
                // 转换为List保存
                List<Object> mappedChildList = new ArrayList<>();
                mappedChildList.add(childEntry.getValue());
                mappedChildren.put(childEntry.getKey(), mappedChildList);
            }
        }
    }

    /**
     * 封装返回的报文
     *
     * @param tableSet
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> setTableKey(Set<String> tableSet, Map<String, Object> obj) {
        Map<String, Object> result = new HashMap<>();
        for (String t : tableSet) {
            obj.forEach((k, v) -> {
                Map<String, Object> tempMap = new HashMap<>();
                if (result.get(t) != null) {
                    tempMap = (Map<String, Object>) result.get(t);
                } else {
                    if (k.startsWith("list_")) {
                        tempMap.put(k, v);
                    }
                    result.put(t, tempMap);
                }
                if (k.endsWith(t)) {

                    System.out.println("\n" + k + "\n" + v + "\n" + t + "\n");

                    // k 以 ID_ 或 id_ 开头的
                    if (k.startsWith(PK_PREFIX_ONE) || k.startsWith(PK_PREFIX_TWO)) {
                        tempMap.put(k, v.toString());
                    } else {
                        if (!k.toString().startsWith("list_")) {
                            tempMap.put(k, (v != null && StringUtil.isNotBlank(v.toString())) ? v.toString() : v);
                        } else {
                            tempMap.put(k, v);
                        }
                    }

                    System.out.println("--------------------------------------------------------循环结束------------------");
                }
            });
        }

        System.out.println("\n========================================最后返回========================================" + result);

        return result;
    }

    /**
     * 列表查询
     *
     * @param reqVO
     * @return
     */
    protected List<Map<String, Object>> list(LowCodeParam reqVO, ModuleCache api) {
        this.setQeueryParams(reqVO.getParams(), api);
        List<ModuleSqlCache> list = api.getSqlList();
        List<Map<String, Object>> listDataNoUpper =
                executorUtils.findListDataNoUpper(this.setConditionAndSort(reqVO, list.get(0).getActionSql()),
                                                  reqVO.getParams());
        if (CollUtil.isNotEmpty(listDataNoUpper)) {
           listDataNoUpper.forEach(r -> processSelectResultKey(r, api));
        }
        return listDataNoUpper;
    }

    /**
     * 子查询
     *
     * @param reqVO
     * @return
     */
    protected Object childList(LowCodeParam reqVO, ModuleCache api) {
        this.setQeueryParams(reqVO.getParams(), api);
        List<ModuleSqlCache> list = api.getSqlList();
        String sqlStr = "";
        for (ModuleSqlCache sql : list) {
            // 表名+表别名
            if (api.getServiceCode().startsWith((sql.getTableName() + sql.getTableAlias()).toUpperCase())) {
                sqlStr = sql.getActionSql();
                break;
            }
        }
        if (reqVO.getEnablePage() == null || reqVO.getEnablePage()) {
            IPage<LowCodeParam> page = MyBatisUtils.buildPage(reqVO);
            reqVO.getParams().put("page", page);
            List<Map<String, Object>> result = executorUtils.findListDataNoUpper(this.setConditionAndSort(reqVO, sqlStr), reqVO.getParams());
            if (CollUtil.isNotEmpty(result)) {
                result.forEach(r -> processSelectResultKey(r, api));
            }
            return new PageResult<>(result, page.getTotal());
        } else {
            List<Map<String, Object>> listDataNoUpper =
                    executorUtils.findListDataNoUpper(this.setConditionAndSort(reqVO, sqlStr), reqVO.getParams());
            if (CollUtil.isNotEmpty(listDataNoUpper)) {
                listDataNoUpper.forEach(r -> processSelectResultKey(r, api));
            }
            return listDataNoUpper;
        }
    }

    /**
     * 分页查询
     *
     * @param reqVO
     * @return
     */
    protected Object getPage(LowCodeParam reqVO, ModuleCache api) {
        List<ModuleSqlCache> list = api.getSqlList();
        if (CollUtil.isEmpty(list)) {
            log.error("serviceId:{} 没有查询到分页sql", api.getServiceId());
            throw exception(SERVICE_EXECUTE_ERROR);
        }
        IPage<LowCodeParam> page = MyBatisUtils.buildPage(reqVO);
        reqVO.getParams().put("page", page);
        this.setQeueryParams(reqVO.getParams(), api);
        if (CfgModuleTypeEnum.COMMON.getCode().equals(api.getModuleType()) || CfgModuleTypeEnum.SQL.getCode().equals(api.getModuleType())) {
            String sql = "select A.NAME as \"b1\",C.UPDATE_BY as \"h1\",C.CREATE_BY as \"f1\",A.ID as \"a1\",A.CREATE_TIME as \"d1\",C.SEX as \"e1\",C.CREATE_TIME as \"g\",C.AGE as \"d\",A.AGE as \"h\",A.ADDRESS as \"i1\",C.TEACHERID as \"b\",A.CREATE_BY as \"c1\",A.UPDATE_TIME as \"f\",C.UPDATE_TIME as \"i\",A.UPDATE_BY as \"e\",C.ID as \"a\",C.NAME as \"c\" from T_TEACHER A  LEFT JOIN MOD_TEACHER_INFO C on C.TEACHERID=A.ID  and C.DEL_FLAG=#{DEL_FLAG} <where>   A.DEL_FLAG=#{DEL_FLAG}</where>";
            List<Map<String, Object>> result = executorUtils.findListDataNoUpper(this.setConditionAndSort(reqVO, sql), reqVO.getParams());
            if (!result.isEmpty()) {
                result.forEach(r -> processSelectResultKey(r, api));
            }
            return new PageResult<>(result, page.getTotal());
        } else if (CfgModuleTypeEnum.JAVA_BEAN.getCode().equals(api.getModuleType())) {
            return methodInvoke(reqVO, api);
        } else {
            return http(reqVO, api);
        }
    }

    /**
     * 处理select语句映射返回结果key，从系统别名key改为列名_模块表id
     */
    private void processSelectResultKeys(Map<String, Object> result, ModuleCache api, AtomicReference<String> moduleTableIds) {
        Map<String, TableField> moduleTableField = cacheComponent.getModuleTableField(api.getModuleId());
        if (moduleTableField == null) {
            return;
        }
        if (Objects.nonNull(result)) {
            Map<String, Object> convertedMap = new HashMap<>(result.size());
            AtomicReference<String> finalModuleTableIds = new AtomicReference<>();
            moduleTableField.forEach((k, v) -> {
                // 判断是否有映射字段
                if (null != v && null != v.getMappingFieldId()) {
                    TableField mappingField = cacheComponent.getModuleFieldId(v.getMappingFieldId());
                    if (null != mappingField) {
                        Object o = result.get(mappingField.getSysAliasName());
                        // 根据映射字段赋值
                        if (null != o) {
                            convertedMap.put(v.getFieldValueUnderline(), o);
                            // 根据映射字段获取表ID，此处排除外键ID情况
                            if (!v.getFieldValueUnderline().contains(PK_NAME_ONE)) {
                                finalModuleTableIds.set(v.getModuleTableId().toString());
                            }
                            //处理字段回显
                            if (v.getTextSql() != null) {
                                Map<String,Object> tempMap = new HashMap<>();
                                tempMap.put(v.getFieldValueUnderline(),o);
                                convertedMap.put(String.format("%s_TEXT_%d",v.getFieldValue(),v.getModuleTableId()), processText(tempMap,api,v));
                            }
                        }
                    }
                }
            });
            result.clear();
            result.putAll(convertedMap);
            moduleTableIds.set(finalModuleTableIds.get());
        }
    }

    /**
     * 处理select语句返回结果key，从系统别名key改为列名_模块表id
     */
    private void processSelectResultKey(Map<String, Object> result, ModuleCache api) {
        processSelectResultKey(result, api, null);
    }

    /**
     * 处理select语句返回结果key，从系统别名key改为列名_模块表id
     */
    private void processSelectResultKey(Map<String, Object> result, ModuleCache api, Map<String, Object> mappedResult) {
        Map<String, TableField> moduleTableField = cacheComponent.getModuleTableField(api.getModuleId());
        if (moduleTableField == null) {
            return;
        }
        Map<String, Object> convertedMap = new HashMap<>(result.size());
        result.forEach((k, v) -> {
            if (moduleTableField.containsKey(k)) {
                // 字段别名映射为  数据库字段名_模块表id
                TableField field = moduleTableField.get(k);
                // 检查键是否以指定前缀开头 TODO: 关于关联表外键精度丢失问题，暂时做过滤处理
                if (field.getFieldValueUnderline().startsWith(PK_PREFIX_ONE) || field.getFieldValueUnderline().startsWith(PK_PREFIX_TWO) || field.getFieldValueUnderline().contains(PK_NAME_ONE)) {
                    convertedMap.put(field.getFieldValueUnderline(), v.toString());
                } else {
                    convertedMap.put(field.getFieldValueUnderline(), v);
                }
                //处理字段回显
                if (field.getTextSql() != null && v != null) {
                    Map<String,Object> tempMap = new HashMap<>();
                    tempMap.put(field.getFieldValueUnderline(),v);
                    convertedMap.put(String.format("%s_TEXT_%d",field.getFieldValue(),field.getModuleTableId()), processText(tempMap,api,field));
                }
                // 映射为 业务表数据
                processMappedField(mappedResult, v, field);
            } else {
                convertedMap.put(k, v);
            }
        });
        result.clear();
        result.putAll(convertedMap);
    }

    /**
     * 处理字段回显
     * @param api
     * @param field
     */
    private String processText(Map<String, Object> result,ModuleCache api,TableField field) {
        String text = "";
        if (1 == field.getValueType()) {//多选
            List<Map<String,Object>> mapLIst = executorUtils.findListDataWithParse(field.getTextSql(), result);
            if (CollUtil.isNotEmpty(mapLIst)) {
                text = mapLIst.stream().map(m -> m.get(SHOW_TEXT).toString()).collect(Collectors.joining(","));
            }
        }else  {
            Map<String,Object> map = executorUtils.findOneData(field.getTextSql(), result);
            if (map != null && map.get(SHOW_TEXT) != null) {
                //此处针对单选的可以考虑进行缓存处理，此处暂不处理
                text = map.get(SHOW_TEXT).toString();
            }
        }
        return text;
    }

    private void processMappedField(Map<String, Object> mappedResult, Object v, TableField field) {
        if (mappedResult != null) {
            if (field.getMappingFieldId() != null) {
            //     // 保留未映射的冗余数据
            //     mappedResult.put(field.getFieldValueUnderline(), v);
            // } else {
                TableField mappingField = cacheComponent.getModuleFieldId(field.getMappingFieldId());
                if (mappingField != null) {
                    if (mappedResult.containsKey(mappingField.getTableName())) {
                        Map<String, Object> tableValueMap = (Map<String, Object>) mappedResult.get(mappingField.getTableName());
                        tableValueMap.put(mappingField.getFieldName(), v);
                    } else {
                        Map<String, Object> tableValueMap = new HashMap<>();
                        tableValueMap.put(mappingField.getFieldName(), v);
                        if (mappingField.getTableName() != null) {
                            mappedResult.put(mappingField.getTableName(), tableValueMap);
                        }
                    }
                }
            }
        }
    }

    /**
     * 远程方法调用
     *
     * @param reqVO
     * @param module
     * @return
     */
    private Object http(LowCodeParam reqVO, ModuleCache module) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("pageNo", reqVO.getPageNo());
            params.put("pageSize", reqVO.getPageSize());
            if (CollUtil.isNotEmpty(reqVO.getQueryFields())) {
                reqVO.getQueryFields().forEach(field -> {
                    params.put(field.getField(), field.getValue());
                });
            }
            if (module.getApiType().equals("1")) {
                String json = HttpUtil.get(module.getApiUrl(), params);
                return JSONUtil.toBean(json, Map.class);
            } else {
                String json = HttpUtil.post(module.getApiUrl(), params);
                return JSONUtil.toBean(json, Map.class);
            }
        } catch (Exception e) {
            log.error("模型接口调用{},请求方式：{},执行失败或校验不通过,异常信息:{}",module.getApiUrl(),module.getApiType(), e.getMessage());
            throw exception(SERVICE_EXECUTE_ERROR);
        }
    }


    /**
     * 动态执行代码,此处考虑缓存对象
     *
     * @param reqVO
     * @param module
     * @return
     */
    private Object methodInvoke(LowCodeParam reqVO, ModuleCache module) {
        try {
            Class<?> clazz = Class.forName(module.getModuleBean());
            Object obj = clazz.getConstructor().newInstance();
            Method method = clazz.getMethod(module.getModuleMethod(), Object.class);
            return method.invoke(obj, reqVO);
        } catch (Exception e) {
            log.error("模型方法调用{}.{}执行失败或校验不通过,异常信息:{}",module.getModuleBean(),module.getModuleMethod(), e.getMessage());
            throw exception(SERVICE_EXECUTE_ERROR);
        }
    }

    /**
     * 动态执行代码,此处考虑缓存对象
     *
     * @param reqVO
     * @param bean
     * @param methodName
     * @return
     */
    private Object methodInvoke(LowCodeParam reqVO, String bean,String methodName) {
        try {
            Class<?> clazz = Class.forName(bean);
            Object obj = clazz.getConstructor().newInstance();
            Method method = clazz.getMethod(methodName, Object.class);
            return method.invoke(obj, reqVO);
        } catch (Exception e) {
            log.error("页面扩展事件{}.{}执行失败或校验不通过,异常信息:{}",bean,methodName, e.getMessage());
            throw exception(SERVICE_EXECUTE_ERROR);
        }
    }


    /**
     * 设置查询条件和排序
     *
     * @param reqVO
     * @param sql
     */
    private String setConditionAndSort(LowCodeParam reqVO, String sql) {
        StringBuilder selectSql = new StringBuilder("select t.* from (" + sql + ") t ");
        if (CollUtil.isNotEmpty(reqVO.getQueryFields())) {
            List<String> sqlList = CollUtil.newArrayList();
            reqVO.getQueryFields().forEach(field -> {
                if (field.getValue() != null) {
                    reqVO.getParams().put(field.getField(), field.getValue());
                    if (field.getQueryOperator().equals(CfgOperateEnum.EQUALS.getCode())) {
                        sqlList.add(String.format("t.%s = #{%s}", field.getField(), getJdbcType(field,null)));
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.EQUALS_NO_CASE.getCode())) {
                        sqlList.add(String.format("LOWER(t.%s) = LOWER(#{%s})", field.getField(), getJdbcType(field,null)));
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.GREATER.getCode())) {
                        sqlList.add(String.format("t.%s &gt; #{%s}", field.getField(), getJdbcType(field,null)));
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.GREATER_EQUALS.getCode())) {
                        sqlList.add(String.format("t.%s &gt;= #{%s}", field.getField(), getJdbcType(field,null)));
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.LESS.getCode())) {
                        sqlList.add(String.format("t.%s &lt; #{%s}", field.getField(), getJdbcType(field,null)));
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.LESS_EQUALS.getCode())) {
                        sqlList.add(String.format("t.%s &lt;= #{%s}", field.getField(), getJdbcType(field,null)));
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.NOT_EQUALS.getCode())) {
                        sqlList.add(String.format("t.%s != #{%s}", field.getField(), getJdbcType(field,null)));
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.LIKE.getCode())) {
                        sqlList.add("t." + field.getField() + " like '%" + field.getValue() + "%'");
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.LEFT_LIKE.getCode())) {
                        sqlList.add("t." + field.getField() + " like '%" + field.getValue() + "'");
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.RIGHT_LIKE.getCode())) {
                        sqlList.add("t." + field.getField() + " like '" + field.getValue() + "%'");
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.IN.getCode())) {
                        sqlList.add(String.format("t.%s in <foreach collection=\"%s\" item=\"item\" close=\")\" open=\"(\" separator=\",\">#{item}</foreach>", field.getField(), field.getField()));
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.NOT_IN.getCode())) {
                        sqlList.add(String.format("t.%s not in <foreach collection=\"%s\" item=\"item\" close=\")\" open=\"(\" separator=\",\">#{item}</foreach>", field.getField(), field.getField()));
                    } else if (field.getQueryOperator().equals(CfgOperateEnum.BETWEEN.getCode())) {
                        List list = (List) field.getValue();
                        if (CollUtil.isEmpty(list) || list.size() < 2) {
                            log.warn("{}", "范围属性的查询条件无效,查询条件为空,或只有一个条件");
                        } else {
                            String start = field.getField() + "_start",end = field.getField() + "_end";
                            sqlList.add(String.format("t.%s between #{%s} and #{%s}", field.getField(), getJdbcType(field,start), getJdbcType(field,end)));
                            reqVO.getParams().put(start, list.get(0));
                            reqVO.getParams().put(end, list.get(1));
                        }
                    }
                }
            });
            if (CollUtil.isNotEmpty(sqlList)) {
                selectSql.append(" <where> ").append(CollUtil.join(sqlList, " AND ")).append(" </where> ");
            }
        }
        if (CollUtil.isNotEmpty(reqVO.getSortingFields())) {
            StringBuilder orderBy = new StringBuilder(" order by ");
            CollUtil.join(reqVO.getSortingFields(), orderBy);
            orderBy.append(CollUtil.join(reqVO.getSortingFields().stream().map(field -> String.format("t.%s %s", field.getField(),
                    field.getOrder())).collect(Collectors.toList()), ","));
            selectSql.append(orderBy);
        }
        return selectSql.toString();
    }

    /**
     * 获取jdbc的类型
     * @param queryField
     * @param fieldName
     * @return
     */
    private String getJdbcType(QueryField queryField,String fieldName) {
        String field = queryField.getField();
        if (StrUtil.isNotEmpty(fieldName)) {
            field = fieldName;
        }
        Set<String> dateType = Sets.newHashSet(JdbcType.DATE.name(), JdbcType.TIMESTAMP.name(),JdbcType.TIME.name());
        if (StrUtil.isNotEmpty(queryField.getJdbcType())) {
            if (dateType.contains(queryField.getJdbcType())) {
                return String.format("%s,typeHandler=%s",field,"com.joyintech.yuntai.framework.mybatis.core.type.DateTypeHandler");
            }
            return String.format("%s,jdbcType=%s",field,queryField.getJdbcType());
        }
        return String.format("%s",field);
    }


    /**
     * 设置新建修改参数
     *
     * @param reqVo
     * @param api
     */
    private void setSaveParams(Map<String, Object> reqVo, ModuleCache api) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        api.getSystemFieldList().forEach(field -> {
            if (CfgSystemFieldEnum.USER_ID.getCode().equals(field.getDefaultValue())) {
                reqVo.put(field.getColumnName(), loginUserId);
            }
            if (CfgSystemFieldEnum.DATE.getCode().equals(field.getDefaultValue())) {
                reqVo.put(field.getColumnName(), new Date());
            }
            if (CfgSystemFieldEnum.DELETE_FLAG.getCode().equals(field.getCategory())) {
                reqVo.put(api.getDeleteField(), api.getNotDeleteVaule());
            }
        });
    }

    /**
     * 设置删除参数
     *
     * @param reqVo
     * @param api
     */
    private void setDeleteParams(Map<String, Object> reqVo, ModuleCache api) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        api.getSystemFieldList().forEach(field -> {
            if (CfgSystemFieldEnum.UPDATE_FLAG.getCode().equals(field.getCategory())) {
                if (CfgSystemFieldEnum.USER_ID.getCode().equals(field.getDefaultValue())) {
                    reqVo.put(field.getColumnName(), loginUserId);
                }
                if (CfgSystemFieldEnum.DATE.getCode().equals(field.getDefaultValue())) {
                    reqVo.put(field.getColumnName(), new Date());
                }
            }
            if (CfgSystemFieldEnum.DELETE_FLAG.getCode().equals(field.getCategory())) {
                reqVo.put(api.getDeleteField(), api.getDeleteValue());
            }
        });
    }

    /**
     * 设置新建修改参数
     *
     * @param reqVo
     */
    private void setQeueryParams(Map<String, Object> reqVo, ModuleCache api) {
        reqVo.put(api.getDeleteField(), api.getNotDeleteVaule());
    }

    /**
     * 处理模型表 配置参数条件
     */
    private Map<String, Object> processParameter(Map<String, Object> params, ModuleCache api) {
        List<String> parameterList = moduleTableMapper.listParameterByModuleId(api.getModuleId());
        // 配置参数 字符串 转 list
        Map<String, Object> parameterMap = new HashMap<>();
        for (String parameter : parameterList) {
            if (params.containsKey(parameter)) {
                Object v = params.get(parameter);
                if ( !(v instanceof Collection || v.getClass().isArray()))  {
                    String[] ids = StringUtils.split(v.toString(), ",");
                    parameterMap.put(parameter, Lists.newArrayList(ids));
                }
            }
        }
        return parameterMap;
    }

    /**
     * 将map中的字段转换成集合对象
     *
     * @param reqVo
     */
    private void setIdLists(Map<String, Object> reqVo) {
        boolean singleResult = true;
        if (CollUtil.isEmpty(reqVo)) {
            return;
        }
        for (Map.Entry<String, Object> entry : reqVo.entrySet()) {
            // TODO: 暂时因时间问题，先做定制化处理，后续兼容逻辑：根据当前moduleID查询模型的MappingFiledID获取FiledID，用filedName替换MAPP和MAPPS
            if ((entry.getKey().startsWith(MAPP) || entry.getKey().startsWith(MAPPS) || entry.getKey().startsWith(CHILD) || entry.getKey().startsWith("CHILD_ID")) && !(entry.getValue() instanceof Collection || entry.getValue().getClass().isArray())) {
                // 判断是否是单条数据
                singleResult = !entry.getValue().toString().contains(",");
                String[] ids = StringUtils.split(entry.getValue().toString(), ",");
                reqVo.put(entry.getKey(), Lists.newArrayList(ids));
            }
        }
        reqVo.put(SINGLE_RESULT, singleResult);
        reqVo.put("DEL_FLAG", 0);
    }

    /**
     * 将map中的字段转换成集合对象
     *
     * @param reqVo
     */
    private void setIdList(Map<String, Object> reqVo) {
        boolean singleResult = true;
        if (CollUtil.isEmpty(reqVo)) {
            return;
        }
        for (Map.Entry<String, Object> entry : reqVo.entrySet()) {
            if ((entry.getKey().startsWith(PK_PREFIX_ONE) || entry.getKey().startsWith(PK_PREFIXS_ONE) || entry.getKey().startsWith(PK_PREFIX_TWO) || entry.getKey().startsWith(PK_PREFIXS_TWO))
                    && !(entry.getValue() instanceof Collection || entry.getValue().getClass().isArray())) {
                // 判断是否是单条数据
                singleResult = !entry.getValue().toString().contains(",");
                String[] ids = StringUtils.split(entry.getValue().toString(), ",");
                reqVo.put(entry.getKey(), Lists.newArrayList(ids));
            }
        }
        reqVo.put(SINGLE_RESULT, singleResult);
    }

    //事件结果
    static class EventResult {
        //不阻塞时未通过的校验
        private List<String> warnMsg;
        //阻塞时未通过的校验
        private List<String> errorMsg;

        public EventResult() {
            this.warnMsg = new ArrayList<>();
            this.errorMsg = new ArrayList<>();
        }

        public List<String> getWarnMsg() {
            return warnMsg;
        }

        public List<String> getErrorMsg() {
            return errorMsg;
        }

        public Boolean isSuccess() {
            if (CollUtil.isEmpty(errorMsg)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 外部流程节点对应的java类
     *
     * @param params 数据
     * @param flowInfo 流程
     */
    @Override
    public Object processMethods(Map<String, Object> params, Map<String, Object> flowInfo, ModuleCache api) {
        // 外部流程节点对应的java类
        if(flowInfo.containsKey(FLOW_ID) && flowInfo.containsKey(NODE_ID) && flowInfo.get(FLOW_ID)!=null && flowInfo.get(NODE_ID)!=null){
            List<String> methods = processDesignService.findProcessMethods((String)flowInfo.get(FLOW_ID), (String)flowInfo.get(NODE_ID));
            if(methods!=null && !methods.isEmpty()){
                for(String method : methods){
                    // TODO 调用外部方法  method、参数：params
                    if(methods.contains("http://")){

                    }

                    // 内部方法
                    // 方法类+方法 ProcessDesignService.findProcessDesign
                    else{
                        Map<String, Object> param = new HashMap<>();
                        // 这里是为了避免flowInfo中存在value值为空，否则可以直接传入params参数
                        param.put(FLOW_ID, flowInfo.get(FLOW_ID));
                        param.put(NODE_ID, flowInfo.get(NODE_ID));
                        param.put(MODULE_CACHE_API,api);
                        param.put(PARAMS,params);
                        String serviceClassName = method.substring(0, method.lastIndexOf("."));
                        String methodName  = method.substring(method.lastIndexOf(".")+1);
                        // 调用方法
                        Object result = invokeMethod(serviceClassName, methodName, param);
                        // 处理结果
                        log.info("流程节点监听调用 " + serviceClassName + "." + methodName + " 结果: " + result);
                        return result;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 内部方法
     *
     * @param serviceClassName
     * @param methodName
     * @param argsMap
     * @return
     */
    public Object invokeMethod(String serviceClassName, String methodName, Map<String, Object> argsMap) {
        try {
            Object serviceInstance = null;
            if(serviceClassName.contains("ProcessDesignServiceImpl")){
                serviceInstance = SpringContentUtils.getBean(ProcessDesignServiceImpl.class);
            }

            // 获取方法的参数类型，处理null值
            Class<?>[] parameterTypes = argsMap.values().stream()
                    .map(Object::getClass)
                    .toArray(Class<?>[]::new);

            // 获取方法对象
            Method method = serviceInstance.getClass().getMethod(methodName, parameterTypes);

            // 调用方法
            return method.invoke(serviceInstance, argsMap.values().toArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询流程日志的数据明细
     *
     * @param reqVO
     * @param moduleTableId
     * @return
     */
    private String findLogProcessFormData(LowCodeParam reqVO, Long moduleTableId) {
        ProcessDataPageReqVO processParam = new ProcessDataPageReqVO();
        processParam.setPageId(reqVO.getPageId());
        if(reqVO.getFlowInfo()!=null){
            if(reqVO.getFlowInfo().containsKey(FLOW_ID)){
                processParam.setFlowId((String)reqVO.getFlowInfo().get(FLOW_ID));
            }
            if(reqVO.getFlowInfo().containsKey(FLOW_REQUEST_ID)){
                processParam.setFlowRequestId((String)reqVO.getFlowInfo().get(FLOW_REQUEST_ID));
            }
            if(reqVO.getFlowInfo().containsKey(NODE_ID)){
                processParam.setNodeId((String)reqVO.getFlowInfo().get(NODE_ID));
            }
        }
        if(moduleTableId!=null && reqVO.getParams().containsKey(PK_PREFIX_ONE +moduleTableId)){
            List<String> idList = (List<String>) reqVO.getParams().get(PK_PREFIX_ONE +moduleTableId);
            processParam.setFormId(idList.get(0));
        }
        if(moduleTableId!=null && reqVO.getParams().containsKey(PK_PREFIX_TWO +moduleTableId)){
            List<String> idList = (List<String>) reqVO.getParams().get(PK_PREFIX_TWO +moduleTableId);
            processParam.setFormId(idList.get(0));
        }
        return processDataService.findProcessDataList(processParam);
    }

    /**
     * 保存日志流程数据
     *
     * @param reqVO
     * @param moduleTableId
     * @param jsonString
     */
    private void saveLogProcessData(LowCodeParam reqVO, Long moduleTableId, String jsonString) {
        ProcessDataSaveReqVO processData = new ProcessDataSaveReqVO();
        processData.setPageId(reqVO.getPageId());
        if(reqVO.getFlowInfo()!=null){
            if(reqVO.getFlowInfo().containsKey(FLOW_ID)){
                processData.setFlowId((String) reqVO.getFlowInfo().get(FLOW_ID));
            }
            if(reqVO.getFlowInfo().containsKey(FLOW_REQUEST_ID)){
                processData.setFlowRequestId((String) reqVO.getFlowInfo().get(FLOW_REQUEST_ID));
            }
            if(reqVO.getFlowInfo().containsKey(NODE_ID)){
                processData.setNodeId((String) reqVO.getFlowInfo().get(NODE_ID));
            }
        }
        if(moduleTableId!=null && reqVO.getParams()!=null
                && reqVO.getParams().containsKey(String.valueOf(moduleTableId))
                && ((Map) reqVO.getParams().get(String.valueOf(moduleTableId))).containsKey(PK_PREFIX_ONE + moduleTableId)){
            processData.setFormId((String) ((Map) reqVO.getParams().get(String.valueOf(moduleTableId))).get(PK_PREFIX_ONE + moduleTableId));
        }
        if(moduleTableId!=null && reqVO.getParams()!=null
                && reqVO.getParams().containsKey(String.valueOf(moduleTableId))
                && ((Map) reqVO.getParams().get(String.valueOf(moduleTableId))).containsKey(PK_PREFIX_TWO + moduleTableId)){
            processData.setFormId((String) ((Map) reqVO.getParams().get(String.valueOf(moduleTableId))).get(PK_PREFIX_TWO + moduleTableId));
        }
        processData.setFormData(jsonString);
        processDataService.createProcessData(processData);
    }

    /**
     * 发起流程
     *
     * @param param 页面数据
     * @param handle1 主表id
     */
    @Override
    public OutSystemTableDO initiateDataProcess(LowCodeParam param, Object handle1) {
        OutSystemTableDO outSystemTableDO = new OutSystemTableDO();
        try {
            Long pageId = param.getPageId();
            LogHelper.outPutLog("主表id---%s---", pageId.toString());
            //Map<String, Object> flowInfo = param.getFlowInfo();
            DataProcessSaveReqVO dataProcessSaveReqVO = this.initDataProcessSaveReqVO(param, pageId);
            LogHelper.outPutLog("表单发起---%s---", dataProcessSaveReqVO.toString());

            // 发送泛微
            // 创建外部关联关系
            outSystemTableDO.setPageId(pageId);
            outSystemTableDO.setFlowId(dataProcessSaveReqVO.getWorkFlowId());
            outSystemTableDO.setPageDataId(dataProcessSaveReqVO.getSerialNum());
            outSystemTableDO.setFlowType("xb");
            if (null != handle1) {
                outSystemTableDO.setPageDataId(handle1.toString());
            } else {
                throw exception(500, "发起流程失败，请联系管理员");
            }
//            outSystemTableDO.setFlowKey(handle1.toString());
            if(StringUtils.isBlank(outSystemTableDO.getBusinessId())){
                outSystemTableDO.setBusinessId(String.valueOf(IdWorker.getId()));
            }
            if(outSystemTableDO.getId()==null){
                outSystemTableMapper.insert(outSystemTableDO);
            } else {
                outSystemTableMapper.updateById(outSystemTableDO);
            }
            LogHelper.outPutLog("创建外部关联关系---%s---", outSystemTableDO.toString());

            // 保存数据
            LowCodeParam lowCodeParamSave = new LowCodeParam();
            lowCodeParamSave.setServiceId(param.getServiceId());
            lowCodeParamSave.setPageId(pageId);
            lowCodeParamSave.setPageApiCode(param.getPageApiCode());
            lowCodeParamSave.setParams(param.getParams());
            // 处理日志
            LogInfoDO logInfoDO = new LogInfoDO();
            logInfoDO.setContent(String.valueOf(lowCodeParamSave));
            LogHelper.outPutLog("处理日志---%s---", logInfoDO.toString());

            @SuppressWarnings("unused")
            String successDate = processDataService.projectAppear(dataProcessSaveReqVO, outSystemTableDO, logInfoDO, param);
            if(outSystemTableDO.getId()==null){
                outSystemTableMapper.insert(outSystemTableDO);
            } else {
                outSystemTableMapper.updateById(outSystemTableDO);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return outSystemTableDO;
    }

    private DataProcessSaveReqVO initDataProcessSaveReqVO(LowCodeParam param, Long pageId) {
        DataProcessSaveReqVO dataProcessSaveReqVO = new DataProcessSaveReqVO();
        Map<String, Object> flowInfo = param.getFlowInfo();
        dataProcessSaveReqVO.setUserId(String.valueOf(WebFrameworkUtils.getLoginUserId()));
        dataProcessSaveReqVO.setDeptId(String.valueOf(SecurityFrameworkUtils.getLoginUserDeptId()));
        if(flowInfo.containsKey("flowId")){
            dataProcessSaveReqVO.setWorkFlowId((String)flowInfo.get("flowId"));
        }
        dataProcessSaveReqVO.setPageInfoId(String.valueOf(pageId));
        // 泛微接口字段定义字段,未使用驼峰
        if(flowInfo.containsKey("isnextflow")){
            dataProcessSaveReqVO.setIsnextflow((String)flowInfo.get("isnextflow"));
        }
        if(flowInfo.containsKey("serialNum")){
            dataProcessSaveReqVO.setSerialNum((String)flowInfo.get("serialNum"));
        }
        if(flowInfo.containsKey("remark")){
            dataProcessSaveReqVO.setRemark((String)flowInfo.get("remark"));
        }
        if(flowInfo.containsKey("flowTitle")){
            dataProcessSaveReqVO.setRequestName((String)flowInfo.get("flowTitle"));
        }

        // 第一、第二信托经理人工号
        String dyxtjlgh = "";
        String dextjlgh = "";
        String workNo = "";
        //复核人工号
        String fhrgh = "";
        if(flowInfo.containsKey("workNo")){
            workNo = (String)flowInfo.get("workNo");
            //查询流程对应的用户id
            dataProcessSaveReqVO.setWorkNo(workNo);
            Map<String,String> headParams = new HashMap<>();
            headParams.put("HrmCode",workNo);
            String headResult = sendHttpGet( callbackUrl + "/api/LCHrmInfo/GetHrmID",headParams);
            JSONObject jsonObject = JSON.parseObject(headResult);
            if(StringUtils.isBlank(jsonObject.getString("hrmId"))){
                throw exception(500, "查询流程人员id异常");
            }
            dataProcessSaveReqVO.setUserId(jsonObject.getString("hrmId"));
        }
        if(flowInfo.containsKey("ywbm")){
            String ywbm = (String)flowInfo.get("ywbm");
            ViewAssetMemberVO fhrInfo = moduleTableMapper.findViewAssetMember(ywbm);
            if(fhrInfo != null){
                dyxtjlgh = fhrInfo.getDyxtjlgh();
                dextjlgh = fhrInfo.getDextjlgh();
            }
            dataProcessSaveReqVO.setYwbm(ywbm);
        }

        if(flowInfo.containsKey("flowExData")){
            Object flowExData = flowInfo.get("flowExData");
            // 需要给泛微传固定地址
            ((Map<String, Object>) flowExData).put("murl", fanweioaMurl);
            ((Map<String, Object>) flowExData).put("url", fanweioaUrl);

            if(((Map<String, Object>) flowExData).containsKey("dyxtjlgh")){
                dyxtjlgh = (String)((Map<String, Object>) flowExData).get("dyxtjlgh");
            }
            if(((Map<String, Object>) flowExData).containsKey("dextjlgh")){
                dextjlgh = (String)((Map<String, Object>) flowExData).get("dextjlgh");
            }
            if(((Map<String, Object>) flowExData).containsKey("fhrgh")){
                fhrgh = (String)((Map<String, Object>) flowExData).get("fhrgh");
            }

            if(workNo!=null && workNo.equals(dyxtjlgh)){
                ((Map<String, Object>) flowExData).put("fhrgh",dextjlgh);
            } else if (workNo!=null && workNo.equals(dextjlgh)) {
                ((Map<String, Object>) flowExData).put("fhrgh",dyxtjlgh);
            } else if(StringUtils.isNotEmpty(fhrgh)){
                ((Map<String, Object>) flowExData).put("fhrgh",fhrgh);
            }else {
                ((Map<String, Object>) flowExData).put("fhrgh",workNo);
            }
//            ((Map<String, Object>) flowExData).put("dyxtjlgh", dyxtjlgh);
//            ((Map<String, Object>) flowExData).put("dextjlgh", dextjlgh);
            if(((Map<String, Object>) flowExData).containsKey("MOD_RELATED_TRANS_INFO") ||
                    ((Map<String, Object>) flowExData).containsKey("MOD_RELATED_PRICING")){
                Object gljylxObj = new Object();
                gljylxObj = ((Map<String, Object>) flowExData).get("MOD_RELATED_TRANS_INFO");
                if(ObjectUtils.isEmpty(gljylxObj)){
                    gljylxObj = ((Map<String, Object>) flowExData).get("MOD_RELATED_PRICING");
                }
                if (gljylxObj instanceof List) {
                    List<?> list = (List<?>) gljylxObj;
                    StringBuilder result = new StringBuilder();
                    for (Object item : list) {
                        Map<String, Object> gljylxMap = (Map<String, Object>) item;
                        if (result.length() > 0) {
                            result.append(",");
                        }
                        result.append(String.valueOf(gljylxMap.get("gljylx")));
                    }
                    ((Map<String, Object>) flowExData).put("gljylx",result.toString());
                    ((Map<String, Object>) flowExData).remove("MOD_RELATED_TRANS_INFO");
                    ((Map<String, Object>) flowExData).remove("MOD_RELATED_PRICING");
                }
            }
            ((Map<String, Object>) flowExData).remove("MOD_APPROVAL_PRODUCT");
            ((Map<String, Object>) flowExData).remove("MOD_APPROVAL_ASSET");

            dataProcessSaveReqVO.setBusinessParameter(JSONUtil.toJsonStr(flowExData));
        }
        if(flowInfo.containsKey("requestLevel")){
            dataProcessSaveReqVO.setRequestLevel((String)flowInfo.get("requestLevel"));
        }
        //处理合同信息及借据信息
        Map<String, Object> body = param.getParams();
        handleContId(body,dataProcessSaveReqVO);

        return dataProcessSaveReqVO;
    }

    private void handleContId(Map<String, Object> body,DataProcessSaveReqVO dataProcessSaveReqVO){
        body.forEach((key, value) -> {
            if (!key.startsWith(CHILD_FLAG)) {
                Map<String, Object> map = BeanUtil.beanToMap(value);
                if(CollUtil.isNotEmpty(map)){
                    map.forEach((k,v) ->{
                        if(k.startsWith(CONT_ID)){
                            dataProcessSaveReqVO.setContId(String.valueOf(v));
                        }else if(k.startsWith(RECEIPT_NUMBER)){
                            dataProcessSaveReqVO.setReciptNumber(String.valueOf(v));
                        }else if(k.startsWith(HKD_ID)){
                            dataProcessSaveReqVO.setHkdId(String.valueOf(v));
                        }else if(k.startsWith(PROD_ID)){
                            dataProcessSaveReqVO.setProdId(String.valueOf(v));
                        }
                    });
                }
            }
        });
    }

    //调用泛微的接口
    public static String sendHttpGet(String url,Map<String,String> map){
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 构建查询参数
            StringBuilder queryParams = new StringBuilder(url);
            if (map != null && !map.isEmpty()) {
                queryParams.append("?");
                map.forEach((key, value) -> queryParams.append(key).append("=").append(value).append("&"));
                queryParams.setLength(queryParams.length() - 1); // 去掉最后的"&"
            }
            // 创建HttpGet请求对象
            HttpGet request = new HttpGet(queryParams.toString());
            // 发送请求并获取响应
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getModelData(LowCodeParam reqVO, ModuleCache api,ModuleCache updateApi) {
        JSONObject jsonObject = new JSONObject();
        this.setIdList(reqVO.getParams());
        Map<String, Object> parameterMap = this.processParameter(reqVO.getParams(), api);
        reqVO.getParams().putAll(parameterMap);
        this.setQeueryParams(reqVO.getParams(), api);
        List<ModuleSqlCache> list = api.getSqlList();
        Map<String, List<ModuleSqlCache>> actionTypeMap = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getActionType));
        List<ModuleSqlCache> getById = actionTypeMap.get(CfgActionTypeEnum.GET_BY_ID.name());
        List<Map<String, Object>> objList = executorUtils.findListDataNoUpper(getById.get(0).getActionSql(), reqVO.getParams());
        if (CollUtil.isNotEmpty(objList)) {
            // 是否单条查询
            Boolean isSingleResult = (Boolean) reqVO.getParams().get(SINGLE_RESULT);

            // 是否单条查询
            if (BooleanUtil.isTrue(isSingleResult)) {
                Map<String, Object> mainObj = objList.get(0);
                Map<String, Object> result = getSingleResult(api, mainObj, actionTypeMap, parameterMap);
                result.forEach((key,value) ->{
                    if(key.startsWith(CHILD_FLAG)){
                        String[] keys = key.split("_");
                        String resultkey = keys[keys.length - 1];
                        List<ModuleSqlCache> moduleSqlCacheList = actionTypeMap.get(
                                CfgActionTypeEnum.CHILD_SELECT_LIST.name());
                        for(ModuleSqlCache moduleSqlCache : moduleSqlCacheList){
                            if(String.valueOf(moduleSqlCache.getModuleTableId()).equals(resultkey)){
                                handleModelData(key,value, moduleSqlCache,jsonObject,updateApi);
                            }
                        }
                    }else{
                        ModuleSqlCache moduleSqlCache = getById.get(0);
                        handleModelData(key,value, moduleSqlCache,jsonObject,updateApi);
                    }
                });
            }else {
                List<Map<String, Object>> resultList = new ArrayList<>();
                for (Map<String, Object> mainObj : objList) {
                    resultList.add(getSingleResult(api, mainObj, actionTypeMap, parameterMap));
                }
                resultList.forEach(result ->{
                    result.forEach((key,value) ->{
                        if(key.startsWith(CHILD_FLAG)){
                            String[] keys = key.split("_");
                            String resultkey = keys[keys.length - 1];
                            List<ModuleSqlCache> moduleSqlCacheList = actionTypeMap.get(
                                    CfgActionTypeEnum.CHILD_SELECT_LIST.name());
                            for(ModuleSqlCache moduleSqlCache : moduleSqlCacheList){
                                if(String.valueOf(moduleSqlCache.getModuleTableId()).equals(resultkey)){
                                    handleModelData(key,value, moduleSqlCache,jsonObject,updateApi);
                                }
                            }
                        }else{
                            ModuleSqlCache moduleSqlCache = getById.get(0);
                            handleModelData(key,value, moduleSqlCache,jsonObject,updateApi);
                        }
                    });
                });
            }
        }
        return jsonObject;
    }

    private void handleModelData(String tableKey,Object body,ModuleSqlCache moduleSqlCache,JSONObject jsonObject,ModuleCache updateApi) {
        String tableName = moduleSqlCache.getTableName();
        List<ModuleSqlCache> list = updateApi.getSqlList();
        Map<Long, List<ModuleSqlCache>> idMap = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getModuleTableId));
        if (tableKey.startsWith(CHILD_FLAG)) {
            tableKey = tableKey.replace(CHILD_FLAG, "");
        }
        List<ModuleSqlCache> moduleSqlList = idMap.get(Long.valueOf(tableKey));
        ModuleSqlCache moduleSql = moduleSqlList.get(0);
        if(CollUtil.isNotEmpty(moduleSqlList)){
            tableName =moduleSql.getTableName();
        }
        JSONArray jsonArray = new JSONArray();
        //主表和关联表为jsonObject,子表为jsonArray
        if(body instanceof List){
            List bodyList = (List) body;
            for(Object object : bodyList){
                JSONObject tableJson = new JSONObject();
                Map<String, Object> map = (Map<String, Object>) object;
                map.forEach((key, value) -> {
                    String newKey = key;
                    if(key.contains("_")){
                        int lastIndex = key.lastIndexOf('_');
                        newKey = key.substring(0, lastIndex);
                    }
                    tableJson.put(newKey, value);
                });
                jsonArray.add(tableJson);
            }
            jsonObject.put(tableName,jsonArray);
        }
        if(body instanceof Map){
            JSONObject tableJson = new JSONObject();
            Map<String, Object> map = (Map<String, Object>) body;
            map.forEach((key, value) -> {
                String newKey = key;
                if(key.contains("_")){
                    int lastIndex = key.lastIndexOf('_');
                    newKey = key.substring(0, lastIndex);
                }
                if(moduleSql.getIsMain() && "id".equalsIgnoreCase(newKey)){
                    jsonObject.put("modId",String.valueOf(value));
                }
                tableJson.put(newKey, value);
            });
            jsonObject.put(tableName,tableJson);
        }

    }

    /**
     * 新建2
     *
     * @param reqVO
     * @return
     */
    protected Object create2(LowCodeParam reqVO, ModuleCache api) {
        List<ModuleSqlCache> list = api.getSqlList();
        Map<Long, List<ModuleSqlCache>> map = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getModuleTableId));
        List<ModuleRelationFieldCache> relationFields = api.getRelationList();
        List<Map<String, Map<String, Object>>> childList = new ArrayList<>();
        List<Map<String, Map<String, Object>>> subChildList = new ArrayList<>();
        Map<String, Map<String, Object>> rootMap = new HashMap<>();
        Map<String, Object> body = reqVO.getParams();
        this.handleData(body, childList, rootMap, relationFields, new ArrayList<>(), OperateTypeEnum.CREATE, subChildList); // 处理主键的关联关系
        AtomicReference<Object> insert = new AtomicReference<>("");

        // 处理主表和主表关联表的插入
        if (CollUtil.isNotEmpty(list)) {
            rootMap.forEach((key, value) -> {
                if (!ATTACHMENT_LIST.equals(key) && canConvertToLongUsingRegex(key)) {
                    List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                    if (CollUtil.isNotEmpty(sqlList)) {
                        this.setSaveParams(value, api);
                        Object insert1 = executorUtils.insert(sqlList.get(0).getActionSql(), value);
                        if (null != sqlList.get(0).getIsMain() && sqlList.get(0).getIsMain()) {
                            insert.set(insert1);
                        }
                    }
                }
            });

            if (CollUtil.isNotEmpty(childList)) {
                childList.forEach(childMap -> {
                    childMap.forEach((key, value) -> {
                        if (!ATTACHMENT_LIST.equals(key) && canConvertToLongUsingRegex(key)) {
                            List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                            if (CollUtil.isNotEmpty(sqlList)) {
                                this.setSaveParams(value, api);
                                executorUtils.insert(sqlList.get(0).getActionSql(), value);
                            }
                        }
                    });
                });
            }

            // 处理最后一级
            if (CollUtil.isNotEmpty(subChildList)) {
                subChildList.forEach(childMap -> {
                    childMap.forEach((key, value) -> {
                        if (!ATTACHMENT_LIST.equals(key) && canConvertToLongUsingRegex(key)) {
                            List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                            if (CollUtil.isNotEmpty(sqlList)) {
                                this.setSaveParams(value, api);
                                executorUtils.insert(sqlList.get(0).getActionSql(), value);
                            }
                        }
                    });
                });
            }

        }

            // // 查询 cfg_module_table 中的树形结构
            // List<ModuleTableSaveReqVO> tableAll = moduleTableMapper.selectTable(Lists.newArrayList(api.getModuleId()));
            // // 过滤 is_child = 1 的记录（关联ID是parentId）
            // List<ModuleTableSaveReqVO> tables2 = tableAll.stream().filter(table -> table.getIsChild() != null && table.getIsChild()).collect(Collectors.toList());
            // tables2.forEach(vo -> {

                // 1，处理子节点自己的数据
                // log.info("处理子节点自己的数据，子节点ID：{}", vo.getId());
                // childList.forEach(childMap -> {
                //     childMap.forEach((key, value) -> {
                //         if (org.springframework.util.ObjectUtils.nullSafeEquals(String.valueOf(vo.getId()), key)) {
                //             if (!ATTACHMENT_LIST.equals(key) && canConvertToLongUsingRegex(key)) {
                //                 List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                //                 if (CollUtil.isNotEmpty(sqlList)) {
                //                     relationFields.forEach(relation -> {
                //                         if (relation.getModuleTableId().toString().equals(key)) {
                //                             String fieldName = String.format(FIELD_FORMAT, relation.getFieldName(), relation.getModuleTableId());
                //                             log.info("设置关联字段：{} = {}", fieldName, parentId);
                //                             value.put(fieldName, Long.valueOf(parentId.toString()));
                //                         }
                //                     });
                //                     // value中有子集
                //                     this.setSaveParams(value, api);


                                            // // 循环value
                                            // for (Map.Entry<String, Object> innerEntry : value.entrySet()) {
                                            //     String innerKey2 = innerEntry.getKey();
                                            //     Object innerValue2 = innerEntry.getValue();
                                            //     System.out.println(key + "=========???===========" + innerKey2 + "======" + innerValue2);

                                            //     if (org.springframework.util.ObjectUtils.nullSafeEquals(innerKey2, key)) {
                                            //         System.out.println("\n");
                                            //         System.out.println("====== (子节点自己) ======" + innerValue2);
                                            //         System.out.println("\n");
                                            //         Map<String, Object> m = parseStringToMap(innerValue2.toString());
                                            //         m.forEach((k, v) -> {
                                            //             value.put(k, v);
                                            //         });
                                            //         value.remove(innerKey2);
                                            //         break;
                                            //     }
                                            // }

                                            // log.info("(子节点自己)执行的SQL：{}", sqlList.get(0).getActionSql());
                                            // log.info("(子节点自己)SQL的数据：{}", value);
                                            // Object insertSubId = executorUtils.insert(sqlList.get(0).getActionSql(), value);
                                            // log.info("insertSubId: {}", insertSubId);
                                    //     }
                                    // });

                //                 }
                //             }
                //         }
                //     });
                // });

                // 2，处理子节点的下级
                // log.info("处理子节点的下级，当前子节点的table_key：{}", vo.getTableKey());
                // // 过滤 table_key 的记录
                // List<ModuleTableSaveReqVO> tableKeyList = new ArrayList<>();
                // tableAll.forEach(obj -> {
                //     if (vo.getTableKey() != null && org.springframework.util.ObjectUtils.nullSafeEquals(vo.getTableKey(), obj.getMainTableKey()) && org.springframework.util.ObjectUtils.isEmpty(obj.getIsChild())) {
                //         tableKeyList.add(obj);
                //     }
                // });
                // tableKeyList.forEach(vo2 -> {

                    // log.info("递归处理子节点自己的数据，子节点ID：{}", vo2.getId());
                    // childList.forEach(childMap -> {
                    //     childMap.forEach((key2, value2) -> {
                    //         // if (org.springframework.util.ObjectUtils.nullSafeEquals(String.valueOf(vo2.getId()), key2)) {
                    //             if (!ATTACHMENT_LIST.equals(key2) && canConvertToLongUsingRegex(key2)) {
                    //                 List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key2));
                    //                 if (CollUtil.isNotEmpty(sqlList)) {
                                        // relationFields.forEach(relation -> {
                                        //     if (relation.getModuleTableId().toString().equals(key2)) {
                                        //         String fieldName = String.format(FIELD_FORMAT, relation.getFieldName(), relation.getModuleTableId());
                                        //         log.info("设置关联字段值：{} = {}", fieldName, parentId);
                                        //         value2.put(fieldName, Long.valueOf(parentId.toString()));
                                        //     }
                                        // });
                                        // this.setSaveParams(value2, api);
                                        // executorUtils.insert(sqlList.get(0).getActionSql(), value2);

                                        // 循环value
                                        // for (Map.Entry<String, Object> innerEntry : value2.entrySet()) {
                                        //     String innerKey = innerEntry.getKey();
                                        //     Object innerValue = innerEntry.getValue();
                                        //     if (org.springframework.util.ObjectUtils.nullSafeEquals(innerKey.replace(CHILD_FLAG, ""), key2)) {
                                        //         // 如果是子节点，根据table_key寻找main_table_key的id
                                        //         String curValue = "";
                                        //         if (innerKey.startsWith(CHILD_FLAG) && null != innerValue) {
                                        //             // 将数组转list
                                        //             String[] stringArray = innerValue.toString().split("},");
                                        //             List<Object> subList = Arrays.asList(stringArray);
                                        //             for (Object obj : subList) {
                                        //                 if (obj.toString().contains(String.valueOf(vo2.getId()))) {
                                        //                     curValue = String.valueOf(obj).trim();
                                        //                     curValue = curValue.substring(String.valueOf(vo2.getId()).length() + 1, curValue.length() - 1) + "}";
                                        //                 }
                                        //             }
                                        //         } else {
                                        //             curValue = String.valueOf(innerValue);
                                        //         }
                                        //         System.out.println("\n");
                                        //         System.out.println("====== (子节点的下级) ======" + curValue);
                                        //         System.out.println("\n");
                                        //         Map<String, Object> m = parseStringToMap(curValue);
                                        //         m.forEach((k, v) -> {
                                        //             value2.put(k, v);
                                        //         });
                                        //         value2.remove(innerKey);
                                        //         break;
                                        //     }
                                        // }
                                        // log.info("(子节点的下级)执行的SQL：{}", sqlList.get(0).getActionSql());
                                        // log.info("(子节点的下级)SQL的数据：{}", value2);
                                        // Object insertSubId = executorUtils.insert(sqlList.get(0).getActionSql(), value2);
                                        // log.info("insertSubId: {}", insertSubId);
        //                             }
        //                         }
        //                     }
        //                 });
        //             });
        //         });
        //     });
        // }

        // 保存日志流程数据
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(body);
            this.saveLogProcessData(reqVO, api.getModuleTableId(), jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Object id = insert.get();

        // 附件（新建页面）
        if(body.containsKey(String.valueOf(api.getModuleTableId()))){
            List<Map> attachmentList = (List<Map>) body.get(ATTACHMENT_LIST);
            if(attachmentList!=null && !attachmentList.isEmpty()){
                for(Map dto : attachmentList){
                    dto.put("pageId", String.valueOf(id));
                    if (reqVO.getFlowInfo() != null) {
                        dto.put("flowId", reqVO.getFlowInfo().get("flowId"));
                        if (ObjectUtils.isNotEmpty(reqVO.getFlowInfo().get("flowNodeId"))) {
                            dto.put("flowNodeId", reqVO.getFlowInfo().get("flowNodeId"));
                        }
                    }
                }
                fileInfoService.updateBatch(attachmentList);
            }
        }
        return id;
    }

    /**
     * 详情2
     */
    @SuppressWarnings({ "unchecked", "unused" })
    protected Object get2(LowCodeParam reqVO, ModuleCache api) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        this.setIdList(reqVO.getParams());
        Map<String, Object> parameterMap = this.processParameter(reqVO.getParams(), api);
        reqVO.getParams().putAll(parameterMap);
        this.setQeueryParams(reqVO.getParams(), api);
        List<ModuleSqlCache> list = api.getSqlList();
        Map<String, List<ModuleSqlCache>> actionTypeMap = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getActionType));
        List<ModuleSqlCache> getById = actionTypeMap.get(CfgActionTypeEnum.GET_BY_ID.name());

        // 1，查询参数主表数据
        List<Map<String, Object>> objList = executorUtils.findListDataNoUpper(getById.get(0).getActionSql(), reqVO.getParams());
        if (CollUtil.isEmpty(objList)) {
            return new HashMap<>();
        }

        // 是否单条查询
        Boolean isSingleResult = (Boolean) reqVO.getParams().get(SINGLE_RESULT);

        // 上次暂存数据
        String formData = this.findLogProcessFormData(reqVO, api.getModuleTableId());
        if(StringUtils.isNotBlank(formData) && false){
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
            Map<String, Object> result = null;
            try {
                result = objectMapper.readValue(formData, typeRef);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (BooleanUtil.isTrue(isSingleResult)) {
                return result;
            }
            List<Map<String, Object>> resultList = new ArrayList<>();
            return resultList.add(result);
        }

        // 附件信息
        List<FileInfoDO> mapList = new ArrayList<>();
        if(reqVO.getParams().containsKey(PK_PREFIX_ONE +api.getModuleTableId())){
            mapList = fileInfoService.findAttachmentList((List<String>) reqVO.getParams().get(PK_PREFIX_ONE +api.getModuleTableId()));
        }
        if(reqVO.getParams().containsKey(PK_PREFIX_TWO +api.getModuleTableId())){
            mapList = fileInfoService.findAttachmentList((List<String>) reqVO.getParams().get(PK_PREFIX_TWO +api.getModuleTableId()));
        }

        // 是否单条查询
        if (BooleanUtil.isTrue(isSingleResult)) {
            //解析文件信息
            mapList.forEach(maps -> {
                String flowNodeId = maps.getFlowNodeId();
                String uploadUserId = maps.getUploadUserId();
                if (StringUtils.isNotEmpty(flowNodeId)) {
                    List<ProcessNodeDO> processNodeDO = processNodeMapper.selectList(new QueryWrapper<ProcessNodeDO>().eq("out_process_node_id", flowNodeId).eq("deleted", 0));
                    if(CollUtil.isNotEmpty(processNodeDO)){
                        maps.setFlowNodeText(processNodeDO.get(0).getName());
                    }
                }
                if(StringUtils.isNotEmpty(uploadUserId)){
                    ApiSysUser user = null;
                    user = sysUserApi.getUserByUsername(uploadUserId);
                    if(Optional.empty().isPresent()){
                        user = sysUserApi.getUser(uploadUserId);
                    }
                    if(Optional.ofNullable(user).isPresent()){
                        maps.setUploadUserText(user.getRealname());
                    }
                }
            });

            Map<String, Object> res = getSingleResult2(api, objList.get(0), actionTypeMap, parameterMap);
            res.put(ATTACHMENT_LIST, mapList);
            return res;
            // return createDataStructure();
        } else {
            List<Map<String, Object>> resultList = new ArrayList<>();
            for (Map<String, Object> mainObj : objList) {
                resultList.add(getSingleResult(api, mainObj, actionTypeMap, parameterMap));
            }
            return  resultList;
        }
    }

    /**
     * 将字段别名转表字段名
     */
    private Map<String, Object> getSingleResult2(ModuleCache api, Map<String, Object> mainObj, Map<String, List<ModuleSqlCache>> actionTypeMap, Map<String, Object> parameterMap) {
        processSelectResultKey(mainObj, api);
        Set<String> tableSet = new HashSet<>();
        mainObj.keySet().forEach(k -> {
            String []keys = k.split("_");
            tableSet.add(keys[keys.length - 1]);
        });

        // 父级数据
        Map<String, Object> result = this.setTableKey(tableSet, mainObj);

        // 最后添加，1. 防止动态参数名和字段别名冲突 2. 防止参数被当做表返回
        mainObj.putAll(parameterMap);
        List<ModuleSqlCache> childSelect = actionTypeMap.get(CfgActionTypeEnum.CHILD_SELECT_LIST.name());
        if (CollUtil.isNotEmpty(childSelect)) {
            // 根据主表查询子表
            this.setIdList(mainObj);
            this.setQeueryParams(mainObj, api);
            for (ModuleSqlCache moduleSql : childSelect) {
                boolean b = checkParamsInMap(moduleSql.getActionSql(), mainObj);
                if(b) {
                    List<Map<String, Object>> childList = executorUtils.findListDataNoUpper(moduleSql.getActionSql(), mainObj);
                    if (CollUtil.isNotEmpty(childList)) {
                        childList.forEach(r -> processSelectResultKey(r, api));
                        result.put(CHILD_FLAG + moduleSql.getModuleTableId(), childList);

                        // moduleTableId：就是主键，根据这个主键获取 table_key, 再获取 main_table_key
                        // 根据 main_table_key 获取 cfg_module_table 的列表，如果IS_CHILD = 1，怎加上：_list = CHILD_FLAG
                        log.info("moduleTableId：{}" + moduleSql.getModuleTableId());


                        // 获取树
                        List<ModuleTableSaveReqVO> tableAll = moduleTableMapper.selectTable(Lists.newArrayList(api.getModuleId()));

                        // 根据 moduleTableId 过滤出一条数据
                        List<ModuleTableSaveReqVO> moduleTableIdList = new ArrayList<>();
                        tableAll.forEach(obj -> {
                            if (org.springframework.util.ObjectUtils.nullSafeEquals(moduleSql.getModuleTableId(), obj.getId())) {
                                moduleTableIdList.add(obj);
                            }
                        });

                        // 获取 table_key
                        String tableKey = "";
                        if (!CollUtil.isEmpty(moduleTableIdList)) {
                            tableKey = moduleTableIdList.get(0).getTableKey();
                        }

                        // 过滤 main_table_key 的记录
                        List<ModuleTableSaveReqVO> mainTableKeyList = new ArrayList<>();
                        for (ModuleTableSaveReqVO vo : tableAll) {
                            if (org.springframework.util.ObjectUtils.nullSafeEquals(tableKey, vo.getMainTableKey())) {
                                mainTableKeyList.add(vo);
                            }
                        }

                        //
                        mainTableKeyList.forEach(vo -> {
                            System.out.println(vo.getId());
                        });


                    }
                }
            }
        }


        return result;
    }

    // 使用 map 创建动态对象
    public Map<String, Object> createDynamicObject(String idSuffix, Map<String, Object> fields) {
        Map<String, Object> obj = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String fullKey = entry.getKey() + (idSuffix != null && !idSuffix.isEmpty() ? "_" + idSuffix : "");
            obj.put(fullKey, entry.getValue());
        }
        return obj;
    }


    // 创建整个数据结构
    public Map<String, Object> createDataStructure() {
        Map<String, Object> data = new LinkedHashMap<>();

        // 添加第一个对象（使用 key-value map）
        Map<String, Object> obj1Fields = new LinkedHashMap<>();
        obj1Fields.put("NAME", "T3");
        obj1Fields.put("AGE", "3");
        obj1Fields.put("ADDRESS", "T-Add-3");
        data.put("1926177721925050370", createDynamicObject("1926177721925050370", obj1Fields));

        // 添加第二个对象
        Map<String, Object> obj2Fields = new LinkedHashMap<>();
        obj2Fields.put("NAME", "TI-3");
        obj2Fields.put("AGE", "3");
        obj2Fields.put("SEX", "3");
        data.put("1926177721941827585", createDynamicObject("1926177721941827585", obj2Fields));

        // 添加学生列表
        List<Map<String, Object>> studentList = new ArrayList<>();

        // 第一个学生组
        Map<String, Object> student1BasicFields = new LinkedHashMap<>();
        student1BasicFields.put("NAME", "S-i-3");
        student1BasicFields.put("AGE", "3");
        student1BasicFields.put("SEX", "3");

        Map<String, Object> student1DetailFields = new LinkedHashMap<>();
        student1DetailFields.put("STUDENT_NAME", "S3");
        student1DetailFields.put("STUDENT_NUM", "S3");
        student1DetailFields.put("STUDENT_AGE", "3");

        // 第一个学生的爱好列表
        List<Map<String, Object>> hobbies1 = new ArrayList<>();

        // 爱好1
        Map<String, Object> hobby1BasicFields = new LinkedHashMap<>();
        hobby1BasicFields.put("NAME", "S-h-i-3");
        hobby1BasicFields.put("MAIN", "S-h-i-3");
        hobby1BasicFields.put("ADDRESS", "S-h-i-3");
        hobby1BasicFields.put("COUNT", "3");
        hobby1BasicFields.put("RATING", "5");

        Map<String, Object> hobby1DetailFields = new LinkedHashMap<>();
        hobby1DetailFields.put("HOBBY_NAME", "S-h-3");
        hobby1DetailFields.put("HOBBY_DESC", "S-h-3");
        hobby1DetailFields.put("HOBBY_DESCRIPTION", "S-h-3");
        hobby1DetailFields.put("FREQUENCY", "Daily");

        hobbies1.add(createHobby(
            createDynamicObject("1926177721933438977", hobby1BasicFields),
            createDynamicObject("1926177721992159234", hobby1DetailFields)
        ));

        // 爱好2
        Map<String, Object> hobby2BasicFields = new LinkedHashMap<>();
        hobby2BasicFields.put("NAME", "S-h-i-3-2");
        hobby2BasicFields.put("MAIN", "S-h-i-3-2");
        hobby2BasicFields.put("ADDRESS", "S-h-i-3-2");
        hobby2BasicFields.put("COUNT", "3");

        Map<String, Object> hobby2DetailFields = new LinkedHashMap<>();
        hobby2DetailFields.put("HOBBY_NAME", "S-h-3-2");
        hobby2DetailFields.put("HOBBY_DESC", "S-h--3-2");
        hobby2DetailFields.put("HOBBY_DESCRIPTION", "S-h-3-2");

        hobbies1.add(createHobby(
            createDynamicObject("1926177721933438977", hobby2BasicFields),
            createDynamicObject("1926177721992159234", hobby2DetailFields)
        ));

        studentList.add(createStudentGroup(
            createDynamicObject("1926177721946021890", student1BasicFields),
            createDynamicObject("1926177721946021891", student1DetailFields),
            hobbies1
        ));

        // 第二个学生组
        Map<String, Object> student2BasicFields = new LinkedHashMap<>();
        student2BasicFields.put("NAME", "S-i-2");
        student2BasicFields.put("AGE", "3");
        student2BasicFields.put("SEX", "3");

        Map<String, Object> student2DetailFields = new LinkedHashMap<>();
        student2DetailFields.put("STUDENT_NAME", "S-2");
        student2DetailFields.put("STUDENT_NUM", "S-2");
        student2DetailFields.put("STUDENT_AGE", "3");

        // 第二个学生的爱好列表
        List<Map<String, Object>> hobbies2 = new ArrayList<>();

        Map<String, Object> hobby3BasicFields = new LinkedHashMap<>();
        hobby3BasicFields.put("NAME", "hi");
        hobby3BasicFields.put("MAIN", "hi");
        hobby3BasicFields.put("ADDRESS", "hi");
        hobby3BasicFields.put("COUNT", "3");

        Map<String, Object> hobby3DetailFields = new LinkedHashMap<>();
        hobby3DetailFields.put("HOBBY_NAME", "h");
        hobby3DetailFields.put("HOBBY_DESC", "h");
        hobby3DetailFields.put("HOBBY_DESCRIPTION", "h");

        hobbies2.add(createHobby(
            createDynamicObject("1926177721933438977", hobby3BasicFields),
            createDynamicObject("1926177721992159234", hobby3DetailFields)
        ));

        studentList.add(createStudentGroup(
            createDynamicObject("1926177721946021890", student2BasicFields),
            createDynamicObject("1926177721946021891", student2DetailFields),
            hobbies2
        ));

        data.put("list_1926177721946021891", studentList);

        return data;
    }

    // 创建学生组
    private Map<String, Object> createStudentGroup(Map<String, Object> basicInfo, Map<String, Object> studentDetails, List<Map<String, Object>> hobbies) {
        Map<String, Object> studentGroup = new LinkedHashMap<>();
        studentGroup.put("1926177721946021890", basicInfo);
        studentGroup.put("1926177721946021891", studentDetails);
        studentGroup.put("list_1926177721992159234", hobbies);
        return studentGroup;
    }

    // 创建爱好对象
    private Map<String, Object> createHobby(Map<String, Object> basicInfo, Map<String, Object> hobbyInfo) {
        Map<String, Object> hobby = new LinkedHashMap<>();
        hobby.put("1926177721933438977", basicInfo);
        hobby.put("1926177721992159234", hobbyInfo);
        return hobby;
    }



    /**
     * 更新2
     */
    protected String update2(LowCodeParam reqVO, ModuleCache api) {
        List<ModuleSqlCache> list = api.getSqlList();
        Map<Long, List<ModuleSqlCache>> map = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getModuleTableId));
        List<ModuleRelationFieldCache> relationFields = api.getRelationList();
        List<Map<String, Map<String, Object>>> childList = new ArrayList<>();
        List<Map<String, Map<String, Object>>> subChildList = new ArrayList<>();
        Map<String, Map<String, Object>> rootMap = new HashMap<>();
        List<Long> child = Lists.newArrayList();
        Map<String, Object> body = reqVO.getParams();
        this.handleData(body, childList, rootMap, relationFields, child, OperateTypeEnum.UPDATE, subChildList);
        Map<String, Object> mainMap = rootMap.get(String.valueOf(api.getModuleTableId()));

        this.deleteChild(mainMap, list, api, rootMap);

        // 查询 cfg_module_table 中的树形结构
        List<ModuleTableSaveReqVO> tableAll = moduleTableMapper.selectTable(Lists.newArrayList(api.getModuleId()));
        tableAll.forEach(vo -> {

        if (CollUtil.isNotEmpty(list)) {
            rootMap.forEach((key, value) -> {
                if(!ATTACHMENT_LIST.equals(key)){
                    List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                    if (CollUtil.isNotEmpty(sqlList)) {
                        this.setSaveParams(value, api);
                        // 根据ID是否存在判断是新增还是修改
                        if (CollUtil.isNotEmpty(value)) {
                            String str = value.keySet().iterator().next();
                            int lastIndex = str.lastIndexOf('_');
                            String substring = str.substring(lastIndex + 1);
                            Object o = value.get(String.format(ID_FORMAT_ONE, substring));
                            if (o == null) {
                                value.put(String.format(ID_FORMAT_ONE, substring), IdWorker.getId());
                            }
                            for (ModuleSqlCache m : sqlList) {
                                if (o == null && m.getActionType().equals(CfgActionTypeEnum.INSERT.name())) {
                                    executorUtils.insert(m.getActionSql(), value);
                                }
                                if (o != null && m.getActionType().equals(CfgActionTypeEnum.UPDATE.name())) {
                                    executorUtils.update(m.getActionSql(), value);
                                }
                            }
                        }
                    }
                }
            });

            // 过滤 is_child = 1 的记录
            @SuppressWarnings("unused")
            List<ModuleTableSaveReqVO> tables2 = tableAll.stream().filter(table -> table.getIsChild() != null && table.getIsChild()).collect(Collectors.toList());
            // tables2.forEach(vo -> {
            //     //
            // });

            if (CollUtil.isNotEmpty(childList)) {
                childList.forEach(childMap -> {
                    childMap.forEach((key, value) -> {
                        if(!ATTACHMENT_LIST.equals(key)){
                            List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                            if (CollUtil.isNotEmpty(sqlList)) {
                                for (ModuleSqlCache m : sqlList) {
                                    if (m.getActionType().equals(CfgActionTypeEnum.INSERT.name())) {
                                        this.setSaveParams(value, api);
                                        executorUtils.insert(m.getActionSql(), value);
                                    }
                                }
                            }
                        }
                    });
                });
            }
        }

        });

        if(body.containsKey(ATTACHMENT_LIST)) {
            List<Map> attachmentList = (List<Map>) body.get(ATTACHMENT_LIST);
            if (attachmentList != null && !attachmentList.isEmpty()) {
                for (Map dto : attachmentList) {
                    if(((Map) body.get(String.valueOf(api.getModuleTableId()))).containsKey(
                            PK_PREFIX_ONE + api.getModuleTableId())){
                        dto.put("pageId", ((Map) body.get(String.valueOf(api.getModuleTableId()))).get(
                                PK_PREFIX_ONE + api.getModuleTableId()));
                    }
                    if(((Map) body.get(String.valueOf(api.getModuleTableId()))).containsKey(
                            PK_PREFIX_TWO + api.getModuleTableId())){
                        dto.put("pageId", ((Map) body.get(String.valueOf(api.getModuleTableId()))).get(
                                PK_PREFIX_TWO + api.getModuleTableId()));
                    }
                    if (reqVO.getFlowInfo() != null) {
                        dto.put("flowId", reqVO.getFlowInfo().get("flowId"));
                        if(ObjectUtils.isNotEmpty(reqVO.getFlowInfo().get("flowNodeId"))){
                            dto.put("flowNodeId", reqVO.getFlowInfo().get("flowNodeId"));
                        }
                    }
                }
                fileInfoService.updateBatch(attachmentList);
            }
        }

        // 保存日志流程数据
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(body);
            this.saveLogProcessData(reqVO, api.getModuleTableId(), jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * 删除2
     */
    protected String delete2(LowCodeParam reqVO, ModuleCache api) {
        this.setIdList(reqVO.getParams());
        List<ModuleSqlCache> list = api.getSqlList();
        Map<Long, List<ModuleSqlCache>> map = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getModuleTableId));
        Map<String, List<ModuleSqlCache>> actionType = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getActionType));
        List<ModuleSqlCache> getByIdList = actionType.get(CfgActionTypeEnum.GET_BY_ID.name());
        this.deleteChild(reqVO.getParams(), list, api, null);

        if(reqVO.getParams().containsKey(PK_PREFIX_ONE + api.getModuleTableId())){
            List<String> id = (List<String>) reqVO.getParams().get(PK_PREFIX_ONE + api.getModuleTableId());
            if(id!=null && !id.isEmpty()){
                fileInfoService.delAttachmentList(id.get(0));
            }
        }

        if(reqVO.getParams().containsKey(PK_PREFIX_TWO + api.getModuleTableId())){
            List<String> id = (List<String>) reqVO.getParams().get(PK_PREFIX_TWO + api.getModuleTableId());
            if(id!=null && !id.isEmpty()){
                fileInfoService.delAttachmentList(id.get(0));
            }
        }

        if (CollUtil.isNotEmpty(getByIdList)) {
            this.setQeueryParams(reqVO.getParams(), api);
            List<Map<String, Object>> mainList = executorUtils.findListDataNoUpper(getByIdList.get(0).getActionSql(), reqVO.getParams());
            if (CollUtil.isNotEmpty(mainList)) {
                Map<String, List<Object>> idsMap = new HashMap<>();
                if (CollUtil.isNotEmpty(mainList)) {//不为空则执行删除
                    mainList.forEach(r -> {
                        // 处理别名，转换为 列名_模块表id 格式
                        processSelectResultKey(r, api);
                        List<String> ids1 = r.keySet().stream().filter(e -> e.startsWith(PK_PREFIX_ONE)).collect(Collectors.toList());
                        if (CollUtil.isNotEmpty(ids1)) {
                            ids1.forEach(id -> {
                                Object v = r.get(id);
                                String key = id.replace(PK_PREFIX_ONE, "");
                                if (idsMap.containsKey(key)) {
                                    idsMap.get(key).add(v);
                                } else {
                                    idsMap.put(key, Lists.newArrayList(v));
                                }
                            });
                        }

                        List<String> ids2 = r.keySet().stream().filter(e -> e.startsWith(PK_PREFIX_TWO)).collect(Collectors.toList());
                        if (CollUtil.isNotEmpty(ids2)) {
                            ids2.forEach(id -> {
                                Object v = r.get(id);
                                String key = id.replace(PK_PREFIX_TWO, "");
                                if (idsMap.containsKey(key)) {
                                    idsMap.get(key).add(v);
                                } else {
                                    idsMap.put(key, Lists.newArrayList(v));
                                }
                            });
                        }
                    });
                    Map<String, Object> idInMap = new HashMap<>();
                    idsMap.forEach((key, value) -> {
                        if(!ATTACHMENT_LIST.equals(key)){
                            List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                            if (CollUtil.isNotEmpty(sqlList)) {
                                for (ModuleSqlCache m : sqlList) {
                                    if (m.getActionType().equals(CfgActionTypeEnum.DELETE.name())) {
                                        idInMap.clear();
                                        idInMap.put(PK_PREFIX_ONE + key, value);
                                        idInMap.put(PK_PREFIX_TWO + key, value);
                                        this.setDeleteParams(idInMap, api);
                                        executorUtils.update(m.getActionSql(), idInMap);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * 字符串转map
     * @param input
     * @return
     */
    public static Map<String, Object> parseStringToMap(String input) {
        String jsonStr = input.substring(1, input.length() - 1);
        jsonStr = "{" + jsonStr + "}";
        return gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>(){}.getType());
    }

    /**
     * list_开发字符串转map
     * @param input
     * @return
     */
    public static List<Map<String, Object>> parseListStringToMap(String input) {
        String jsonStr = preprocessToJson(input);
        Gson gson = new GsonBuilder().setLenient().create();
        JsonArray array = gson.fromJson(jsonStr, JsonArray.class);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (JsonElement element : array) {
            resultList.add(convertToMap(element.getAsJsonObject()));
        }
        return resultList;
    }

    // 字符串转json
    private static String preprocessToJson(String input) {
        // 基础转换
        String jsonStr = input
            .replace("{{", "[{")
            .replace("}}", "}]")
            .replace("}, {", "},{")
            .replace("=", ":");

        // 处理键（数字键、特殊键、嵌套键）
        jsonStr = Pattern.compile("(\\d+):").matcher(jsonStr).replaceAll("\"$1\":");
        jsonStr = Pattern.compile("(_[A-Z_]+):").matcher(jsonStr).replaceAll("\"$1\":");
        jsonStr = Pattern.compile("([A-Z]+_[0-9]+):").matcher(jsonStr).replaceAll("\"$1\":");

        // 处理值（字符串值加引号，数字值保持原样）
        jsonStr = Pattern.compile(":([^{}\\[\\],\\d\"]+?)([,}])").matcher(jsonStr).replaceAll(":\"$1\"$2");
        jsonStr = Pattern.compile(":\"?(\\d+)\"?").matcher(jsonStr).replaceAll(":$1");

        return jsonStr;
    }

    // json 转 map
    private static Map<String, Object> convertToMap(JsonObject jsonObject) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            if (value.isJsonObject()) {
                // 递归转换嵌套对象
                resultMap.put(key, convertToMap(value.getAsJsonObject()));
            } else if (value.isJsonPrimitive()) {
                // 处理基本类型
                JsonPrimitive primitive = value.getAsJsonPrimitive();
                if (primitive.isString()) {
                    resultMap.put(key, primitive.getAsString());
                } else if (primitive.isNumber()) {
                    resultMap.put(key, primitive.getAsInt());
                } else if (primitive.isBoolean()) {
                    resultMap.put(key, primitive.getAsBoolean());
                }
            }
        }
        return resultMap;
    }

}
