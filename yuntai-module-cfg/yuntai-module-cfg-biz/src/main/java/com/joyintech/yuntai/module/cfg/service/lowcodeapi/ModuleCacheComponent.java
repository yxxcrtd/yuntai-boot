package com.joyintech.yuntai.module.cfg.service.lowcodeapi;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleCache;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleRelationFieldCache;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleSqlCache;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.PageExtendEventCache;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.TableField;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbsystemcolumn.DbSystemColumnDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulefield.ModuleFieldDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageextendevent.PageExtendEventDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.dbsystemcolumn.DbSystemColumnMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduleapi.ModuleApiMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.modulefield.ModuleFieldMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.modulerelationfield.ModuleRelationFieldMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.modulesql.ModuleSqlMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageextendevent.PageExtendEventMapper;
import com.joyintech.yuntai.module.cfg.enums.CfgActionTypeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgApiTypeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgEventTypeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgSystemFieldEnum;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/11
 */
@Slf4j
@Component
public class ModuleCacheComponent  {
    @Resource
    private ModuleRelationFieldMapper relationFieldMapper;
    @Resource
    private ModuleApiMapper moduleApiMapper;
    @Resource
    private ModuleSqlMapper moduleSqlMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private DbSystemColumnMapper dbSystemColumnMapper;
    @Resource
    private PageExtendEventMapper pageExtendEventMapper;
    @Resource
    private ModuleFieldMapper moduleFieldMapper;
    @Value("${mybatis-plus.global-config.db-config.logic-delete-field:deleted}")
    private String deleteField;

    @Value("${mybatis-plus.global-config.db-config.logic-delete-value:1}")
    private String deleteValue;

    @Value("${mybatis-plus.global-config.db-config.logic-not-delete-value:0}")
    private String notDeleteVaule;

    @Value("${mybatis-plus.global-config.db-config.logic-delete-field-type:number}")
    private String deleteFieldType;
    // 缓存key
    public static final String CACHE_KEY = "SERVICE_ID:%d";
    public static final String MODULE_CACHE_KEY = "MODULE_ID:%d";
    // 系统字段key
    public static final String CACHE_SYSTEM_FIELD_KEY = "SYSTEM_FIELD:%d";

    // 模型字段key
    public static final String CACHE_MODULE_FIELD_KEY = "MODULE_FIELD_ID:%d";

    private static AtomicBoolean isInit = new AtomicBoolean(false);


    /**
     * 初始化缓存
     */
    public void initCache() {
        if (!isInit.compareAndSet(false, true)) {
            return;
        }
        this.cacheModule(null);
        this.cacheSystemField();
        isInit.set(false);
    }


    /**
     * 获取模型缓存
     *
     * @param serviceId
     * @return
     */
    public Map<String, TableField> getModuleTableField(Long moduleId) {
        String json = stringRedisTemplate.opsForValue().get(String.format(MODULE_CACHE_KEY, moduleId));
        if (StrUtil.isNotEmpty(json)) {
            return JSON.parseObject(json,  new TypeReference<Map<String, TableField>>() {});
        }
        return null;
    }

    /**
     * 获取模型字段缓存
     *
     * @param moduleFieldId
     * @return
     */
    public TableField getModuleFieldId(Long moduleFieldId) {
        String json = stringRedisTemplate.opsForValue().get(String.format(CACHE_MODULE_FIELD_KEY, moduleFieldId));
        if (StrUtil.isNotEmpty(json)) {
            return JSON.parseObject(json, TableField.class);
        }
        return null;
    }

    /**
     * 获取模型缓存
     *
     * @param serviceId
     * @return
     */
    public ModuleCache getModuleCache(Long serviceId, boolean includeSystemField) {
        String json = stringRedisTemplate.opsForValue().get(String.format(CACHE_KEY, serviceId));
        if (StrUtil.isNotEmpty(json)) {
            ModuleCache cache = JSON.parseObject(json, ModuleCache.class);
            if (includeSystemField) {
                cache.setSystemFieldList(this.getSystemFieldCache(cache.getDataSourceId()));
                cache.setDeleteField(this.getDeleteField(cache.getDataSourceId()));
                cache.setDeleteValue(this.getDeleteValue(cache.getDataSourceId()));
                cache.setNotDeleteVaule(this.getNotDeleteValue(cache.getDataSourceId()));
            }
            return cache;
        }
        return null;
    }

    /**
     * 获取模型缓存
     *
     * @return
     */
    public List<DbSystemColumnDO> getSystemFieldCache(Long dataSourceId) {
        String json = stringRedisTemplate.opsForValue().get(String.format(CACHE_SYSTEM_FIELD_KEY, dataSourceId));
        if (StrUtil.isNotEmpty(json)) {
            List<DbSystemColumnDO> list = JSON.parseArray(json, DbSystemColumnDO.class);
            list.removeIf(item -> CfgSystemFieldEnum.PRIMARY_KEY.getCode().equals(item.getCategory()));
            return list;
        }
        return CollUtil.newArrayList();
    }

    /**
     * 获取模型缓存
     *
     * @return
     */
    public List<DbSystemColumnDO> getSystemFieldCacheWithOut(Long dataSourceId) {
        String json = stringRedisTemplate.opsForValue().get(String.format(CACHE_SYSTEM_FIELD_KEY, dataSourceId));
        if (StrUtil.isNotEmpty(json)) {
            List<DbSystemColumnDO> list = JSON.parseArray(json, DbSystemColumnDO.class);
            list.removeIf(item -> CfgSystemFieldEnum.PRIMARY_KEY.getCode().equals(item.getCategory()));
            list.removeIf(item -> CfgSystemFieldEnum.DELETE_FLAG.getCode().equals(item.getCategory()));
            return list;
        }
        return CollUtil.newArrayList();
    }

    /**
     * 获取删除字段
     *
     * @return
     */
    public String getDeleteField(Long dataSourceId) {
        String json = stringRedisTemplate.opsForValue().get(String.format(CACHE_SYSTEM_FIELD_KEY, dataSourceId));
        if (StrUtil.isNotEmpty(json)) {
            List<DbSystemColumnDO> list = JSON.parseArray(json, DbSystemColumnDO.class);
            for (DbSystemColumnDO dbSystemColumnDO : list) {
                if (CfgSystemFieldEnum.DELETE_FLAG.getCode().equals(dbSystemColumnDO.getCategory())) {
                    return dbSystemColumnDO.getColumnName();
                }
            }
        }
        return deleteField;
    }

    /**
     * 获取删除字段的值
     *
     * @return
     */
    public Object getDeleteValue(Long dataSourceId) {
        String json = stringRedisTemplate.opsForValue().get(String.format(CACHE_SYSTEM_FIELD_KEY, dataSourceId));
        if (StrUtil.isNotEmpty(json)) {
            List<DbSystemColumnDO> list = JSON.parseArray(json, DbSystemColumnDO.class);
            for (DbSystemColumnDO dbSystemColumnDO : list) {
                if (CfgSystemFieldEnum.DELETE_FLAG.getCode().equals(dbSystemColumnDO.getCategory())) {
                    if (CfgSystemFieldEnum.NUMBER.getCode().equalsIgnoreCase(dbSystemColumnDO.getDefaultValue())) {
                        return Integer.parseInt(dbSystemColumnDO.getDeletedValue());
                    } else {
                        return dbSystemColumnDO.getDeletedValue();
                    }
                }
            }
        }
        if (CfgSystemFieldEnum.NUMBER.getCode().equalsIgnoreCase(deleteFieldType)) {
            return Integer.parseInt(deleteValue);
        } else {
            return deleteValue;
        }
    }

    /**
     * 获取未删除字段的值
     *
     * @return
     */
    public Object getNotDeleteValue(Long dataSourceId) {
        String json = stringRedisTemplate.opsForValue().get(String.format(CACHE_SYSTEM_FIELD_KEY, dataSourceId));
        if (StrUtil.isNotEmpty(json)) {
            List<DbSystemColumnDO> list = JSON.parseArray(json, DbSystemColumnDO.class);
            for (DbSystemColumnDO dbSystemColumnDO : list) {
                if (CfgSystemFieldEnum.DELETE_FLAG.getCode().equals(dbSystemColumnDO.getCategory())) {
                    if (CfgSystemFieldEnum.NUMBER.getCode().equalsIgnoreCase(dbSystemColumnDO.getDefaultValue())) {
                        return Integer.parseInt(dbSystemColumnDO.getNotDeletedValue());
                    } else {
                        return dbSystemColumnDO.getNotDeletedValue();
                    }
                }
            }
        }
        if (CfgSystemFieldEnum.NUMBER.getCode().equalsIgnoreCase(deleteFieldType)) {
            return Integer.parseInt(notDeleteVaule);
        } else {
            return notDeleteVaule;
        }
    }


    /**
     * 缓存 模型数据
     */
    private void cacheModule(Long moduleId) {
        List<ModuleCache> serviceList = moduleApiMapper.selectModuleCache(moduleId);
        if (CollUtil.isEmpty(serviceList)) return;
        List<ModuleRelationFieldCache> rList = relationFieldMapper.selectRelationFieldCache(moduleId);
        Map<Long, List<ModuleRelationFieldCache>> relationMap = rList.stream().collect(Collectors.groupingBy(ModuleRelationFieldCache::getModuleId));
        List<ModuleSqlCache> sqlList = moduleSqlMapper.selectSqlCache(moduleId);
        Map<Long, List<ModuleSqlCache>> sqlMap = sqlList.stream().collect(Collectors.groupingBy(ModuleSqlCache::getModuleId));
        List<ModuleCache> mappingList = moduleApiMapper.selectMappingModuleCache(moduleId);
        final Map<Long,List<ModuleCache>> mappingMap = new HashMap<>();
        if (CollUtil.isNotEmpty(mappingList)) {
            mappingMap.putAll(mappingList.stream().collect(Collectors.groupingBy(ModuleCache::getServiceId)));
        }
        //获取页面事件
        List<PageExtendEventDO> extendEventList = pageExtendEventMapper.selectList(new QueryWrapper<PageExtendEventDO>().eq("event_type", CfgEventTypeEnum.BACK.getCode()));
        final Map<Long, List<PageExtendEventCache>> eventMap = new HashMap<>();
        if (CollUtil.isNotEmpty(extendEventList)) {
            List<PageExtendEventCache> eventList = BeanUtils.toBean(extendEventList, PageExtendEventCache.class);
            eventMap.putAll(eventList.stream().collect(Collectors.groupingBy(PageExtendEventCache::getServiceId)));
        }
        serviceList.forEach(item -> {
            item.setRelationList(relationMap.get(item.getModuleId()));
            List<ModuleSqlCache> sqlList1 = sqlMap.get(item.getModuleId());
            if (CollUtil.isNotEmpty(sqlList1)) {
                Map<String, List<ModuleSqlCache>> actionTypeMap = sqlList1.stream().collect(Collectors.groupingBy(ModuleSqlCache::getActionType));
                if (item.getServiceCode().endsWith(CfgApiTypeEnum.CREATE.getCode())) {
                    item.setSqlList(actionTypeMap.getOrDefault(CfgActionTypeEnum.INSERT.name(), Lists.newArrayList()));
                } else if (item.getServiceCode().endsWith(CfgApiTypeEnum.UPDATE.getCode())) {
                    item.setSqlList(actionTypeMap.getOrDefault(CfgActionTypeEnum.INSERT.name(), Lists.newArrayList()));
                    item.getSqlList().addAll(actionTypeMap.getOrDefault(CfgActionTypeEnum.UPDATE.name(), Lists.newArrayList()));
                    item.getSqlList().addAll(actionTypeMap.getOrDefault(CfgActionTypeEnum.DELETE.name(), Lists.newArrayList()));
                    item.getSqlList().addAll(actionTypeMap.getOrDefault(CfgActionTypeEnum.CHILD_SELECT_LIST.name(),Lists.newArrayList()));
                } else if (item.getServiceCode().endsWith(CfgApiTypeEnum.DELETE.getCode())) {
                    item.setSqlList(actionTypeMap.getOrDefault(CfgActionTypeEnum.DELETE.name(), Lists.newArrayList()));
                    item.getSqlList().addAll(actionTypeMap.getOrDefault(CfgActionTypeEnum.CHILD_SELECT_LIST.name(), Lists.newArrayList()));
                    item.getSqlList().addAll(actionTypeMap.getOrDefault(CfgActionTypeEnum.GET_BY_ID.name(), Lists.newArrayList()));
                } else if (item.getServiceCode().endsWith(CfgApiTypeEnum.PAGE_LIST.getCode())) {
                    item.setSqlList(actionTypeMap.getOrDefault(CfgActionTypeEnum.SELECT_PAGE.name(), Lists.newArrayList()));
                } else if (item.getServiceCode().endsWith(CfgApiTypeEnum.CHILD_LIST.getCode())) {
                    item.setSqlList(actionTypeMap.getOrDefault(CfgActionTypeEnum.CHILD_SELECT_LIST.name(), Lists.newArrayList()));
                } else if (item.getServiceCode().endsWith(CfgApiTypeEnum.LIST.getCode())) {
                    item.setSqlList(actionTypeMap.getOrDefault(CfgActionTypeEnum.SELECT_PAGE.name(), Lists.newArrayList()));
                } else if (item.getServiceCode().endsWith(CfgApiTypeEnum.MAPPING_GET_BY_ID.getCode())) {
                    item.setSqlList(actionTypeMap.getOrDefault(CfgActionTypeEnum.MAPPING_GET_BY_ID.name(), Lists.newArrayList()));
                    item.getSqlList().addAll(actionTypeMap.getOrDefault(CfgActionTypeEnum.MAPPING_CHILD_SELECT_LIST.name(), Lists.newArrayList()));
                    List<ModuleCache> mList = mappingMap.get(item.getServiceId());
                    if (CollUtil.isNotEmpty(mList)) {
                        item.setDataSourceId(mList.get(0).getDataSourceId());
                    }
                } else if (item.getServiceCode().endsWith(CfgApiTypeEnum.GET_BY_ID.getCode())) {
                    item.setSqlList(actionTypeMap.getOrDefault(CfgActionTypeEnum.GET_BY_ID.name(), Lists.newArrayList()));
                    item.getSqlList().addAll(actionTypeMap.getOrDefault(CfgActionTypeEnum.CHILD_SELECT_LIST.name(), Lists.newArrayList()));
                }
            }
            item.setExtendEventList(eventMap.getOrDefault(item.getServiceId(),new ArrayList<>()));
            //serviceIdMap.put(String.format(CACHE_KEY, item.getServiceId()), JSON.toJSONString(item));
            stringRedisTemplate.opsForValue().set(String.format(CACHE_KEY, item.getServiceId()), JSON.toJSONString(item));
        });
        //stringRedisTemplate.opsForValue().multiSet(serviceIdMap);
        /*for (ModuleCache item : moduleCaches) {
            // 缓存模型表字段-别名作为map key
            processModelTableFieldCache(item);
        }*/

        processModelTableFieldCache(moduleId);

        // 缓存模型字段
        //processModelFieldIdCache(moduleId);
    }

    private void processModelTableFieldCache(Long moduleId) {
        List<TableField> fields = moduleFieldMapper.selectByModuleId(moduleId,null);
        Map<Long,List<TableField>> mapField = fields.stream().collect(Collectors.groupingBy(TableField::getModuleId));
        Map<String,String> moduleFiledMap = new HashMap<>();
        Map<String, TableField> fieldMap = new HashMap<>();
        Map<String, String> fieldIdMap = new HashMap<>();
        mapField.forEach((key, value) -> {
            fieldMap.clear();
            fieldIdMap.clear();
            for (TableField field : value) {
                //stringRedisTemplate.opsForValue().set(String.format(CACHE_MODULE_FIELD_KEY, field.getId()), JSON.toJSONString(field));
                fieldIdMap.put(String.format(CACHE_MODULE_FIELD_KEY, field.getId()),JSON.toJSONString(field));
                if (StringUtils.isNotEmpty(field.getSysAliasName())) {
                    fieldMap.put(field.getSysAliasName(), field);
                }
            }
            stringRedisTemplate.opsForValue().multiSet(fieldIdMap);
            moduleFiledMap.put(String.format(MODULE_CACHE_KEY, key), JSON.toJSONString(fieldMap));
            stringRedisTemplate.opsForValue().set(String.format(MODULE_CACHE_KEY, key), JSON.toJSONString(fieldMap));
        });
        //stringRedisTemplate.opsForValue().multiSet(moduleFiledMap);
    }

    private void processModelFieldIdCache(Long moduleId) {
        List<TableField> fields = moduleFieldMapper.selectByModuleId(moduleId,null);
        for (TableField field : fields) {
            stringRedisTemplate.opsForValue().set(String.format(CACHE_MODULE_FIELD_KEY, field.getId()), JSON.toJSONString(field));
        }
    }

    /**
     * 缓存系统字段
     */
    private void cacheSystemField() {
        List<DbSystemColumnDO> list = dbSystemColumnMapper.selectSystemFieldCache();
        if (!CollUtil.isEmpty(list)) {
            list.stream().filter(item -> Objects.nonNull(item.getDataSourceId())).collect(Collectors.groupingBy(DbSystemColumnDO::getDataSourceId))
                    .forEach((k, v) -> stringRedisTemplate.opsForValue().set(String.format(CACHE_SYSTEM_FIELD_KEY, k), JSON.toJSONString(v)));
        }
    }


    /**
     * 刷新缓存
     *
     * @param moduleId 传了刷新单个,不传刷新全部
     */
    public void refreshCacheByModuleId(Long moduleId) {
        new Thread(() -> {
            this.cacheModule(moduleId);
            this.cacheSystemField();
        }).start();
    }

    public void putCache(Object key, Map<String, Object> value) {
        stringRedisTemplate.opsForValue().set(key.toString(), value.toString());
    }

    public Object getCache(Object key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

}
