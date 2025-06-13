package com.joyintech.yuntai.module.cfg.service.moduleinfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.Lists;
import com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.framework.common.util.tree.TreeNode;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleSqlCache;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleapi.vo.ModuleApiRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition.ColumnDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.functioninfo.FunctionInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleapi.ModuleApiDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleapiparam.ModuleApiParamDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulefield.ModuleFieldDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleinfo.ModuleInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulerelationfield.ModuleRelationFieldDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulesql.ModuleSqlDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduletable.ModuleTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageapi.PageApiDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.tabledefinition.TableDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.columndefinition.ColumnDefinitionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.functioninfo.FunctionInfoMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduleapi.ModuleApiMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduleapiparam.ModuleApiParamMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.modulefield.ModuleFieldMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduleinfo.ModuleInfoMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.modulerelationfield.ModuleRelationFieldMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.modulesql.ModuleSqlMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduletable.ModuleTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageapi.PageApiMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.tabledefinition.TableDefinitionMapper;
import com.joyintech.yuntai.module.cfg.enums.CfgActionTypeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgApiTypeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgModuleTypeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgParamTypeEnum;
import com.joyintech.yuntai.module.cfg.service.lowcodeapi.ModuleCacheComponent;
import com.joyintech.yuntai.module.cfg.utils.FuncUtil;
import com.joyintech.yuntai.module.cfg.utils.NumberUtils;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Predicate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 模型信息 Service 实现类
 *
 * @author 兆尹云台
 */
@Slf4j
@Service
public class ModuleInfoServiceImpl implements ModuleInfoService {
    public static final String MODULE_TABLE_ID = "module_table_id";
    public static final String UPDATE = "update";
    public static final String MODULE_ID = "module_id";
    @Resource
    private ModuleInfoMapper moduleInfoMapper;
    @Resource
    private ModuleTableMapper moduleTableMapper;
    @Resource
    private ModuleRelationFieldMapper moduleRelationMapper;
    @Resource
    private ModuleFieldMapper moduleFieldMapper;
    @Resource
    private ModuleApiMapper moduleApiMapper;
    @Resource
    private ModuleApiParamMapper moduleApiParamMapper;
    @Resource
    private ModuleSqlMapper moduleSqlMapper;
    @Resource
    private FunctionInfoMapper functionInfoMapper;
    @Resource
    private ModuleCacheComponent moduleCacheComponent;

    @Resource
    private PageApiMapper pageApiMapper;
    @Resource
    private TableDefinitionMapper tableDefinitionMapper;
    @Resource
    private ColumnDefinitionMapper columnDefinitionMapper;

    @Resource
    private ModuleCacheComponent cacheComponent;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createModuleInfo(ModuleInfoSaveReqVO createReqVO) {
        ModuleInfoDO moduleInfo = BeanUtils.toBean(createReqVO, ModuleInfoDO.class);
        if (CfgModuleTypeEnum.SQL.getCode().equals(moduleInfo.getModuleType())) {
            moduleInfo.setModuleSql(null);
        }
        moduleInfoMapper.insert(moduleInfo);
        createReqVO.setId(moduleInfo.getId());
        this.saveModuleInfo(createReqVO, OperateTypeEnum.CREATE);
        return moduleInfo.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateModuleInfo(ModuleInfoSaveReqVO updateReqVO) {
        ModuleInfoDO updateObj = BeanUtils.toBean(updateReqVO, ModuleInfoDO.class);
        if (CfgModuleTypeEnum.SQL.getCode().equals(updateObj.getModuleType())) {
            updateObj.setModuleSql(null);
        }
        ModuleInfoDO old = moduleInfoMapper.selectById(updateReqVO.getId());
        if (old == null) {
            throw exception(MODULE_INFO_NOT_EXISTS);
        }
        if (!FuncUtil.compareDate(old.getUpdateTime(),updateReqVO.getUpdateTimeStamp())) {
            throw exception(MODULE_INFO_CHANGE);
        }
        moduleInfoMapper.updateById(updateObj);
        updateReqVO.setId(updateObj.getId());
        this.saveModuleInfo(updateReqVO, OperateTypeEnum.UPDATE);
    }

    /**
     * 保存模型关联表信息
     *
     * @param reqVO
     * @param type
     */
    private void saveModuleInfo(ModuleInfoSaveReqVO reqVO, OperateTypeEnum type) {
        if (CfgModuleTypeEnum.COMMON.getCode().equals(reqVO.getModuleType())) {
            List<ModuleApiDO> apiList = new ArrayList<>();
            this.saveCommonModule(reqVO, type,apiList);
            this.saveMappingModule(reqVO,apiList);
            // 处理回显字段
            //this.updateTextTable(reqVO.getId());
        } else if (CfgModuleTypeEnum.SQL.getCode().equals(reqVO.getModuleType())) {
            this.saveSQLModule(reqVO, type);
        }else if(CfgModuleTypeEnum.JAVA_BEAN.getCode().equals(reqVO.getModuleType())){
            this.saveBeanModule(reqVO,type);
        }else{
            this.saveHttpModule(reqVO,type);
        }
        moduleCacheComponent.refreshCacheByModuleId(reqVO.getId());
    }

    /**
     * 保存映射模型
     * @param reqVO
     */
    private void saveMappingModule(ModuleInfoSaveReqVO reqVO,List<ModuleApiDO> apiList) {
        if (Objects.isNull(reqVO.getMappingModuleId())) return;
        List<TableField> fields = moduleFieldMapper.selectByModuleId(reqVO.getMappingModuleId(),reqVO.getId());
        List<ModuleSqlDO> sqlList = moduleSqlMapper.selectList(new LambdaQueryWrapper<ModuleSqlDO>()
                .eq(ModuleSqlDO::getModuleId, reqVO.getMappingModuleId()).in(ModuleSqlDO::getActionType,Lists.newArrayList(CfgActionTypeEnum.GET_BY_ID.name(),CfgActionTypeEnum.CHILD_SELECT_LIST.name())));
        if (CollUtil.isNotEmpty(sqlList)) {
            String sql = null;
            for (ModuleSqlDO sqlDO : sqlList) {
                sql = sqlDO.getActionSql();
                for (TableField tableField : fields) {
                    if (StringUtils.isNotBlank(sql) && StrUtil.isNotEmpty(tableField.getFieldValueUnderline()) && StrUtil.isNotEmpty(tableField.getMappingFieldName())) {
                        sql = sql.replace("\"" + tableField.getFieldValueUnderline()+"\"", "\""+ tableField.getMappingFieldValueUnderline()+"\"");
                        sql = sql.replace("{" + tableField.getFieldValueUnderline(), "{"+ tableField.getMappingFieldValueUnderline());
                    }
                }
                sqlDO.setActionSql(sql);
                sqlDO.setModuleId(reqVO.getId());
                sqlDO.setId(IdWorker.getId());
                if (CfgActionTypeEnum.GET_BY_ID.name().equals(sqlDO.getActionType())) {
                    sqlDO.setActionType(CfgActionTypeEnum.MAPPING_GET_BY_ID.name());
                }
                if (CfgActionTypeEnum.CHILD_SELECT_LIST.name().equals(sqlDO.getActionType())) {
                    sqlDO.setActionType(CfgActionTypeEnum.MAPPING_CHILD_SELECT_LIST.name());
                }
            }
            moduleSqlMapper.insert(sqlList);
            //处理api和api参数
            List<ModuleApiDO> aList = apiList.stream().filter(item -> CfgApiTypeEnum.MAPPING_GET_BY_ID.getCode().equals(item.getServiceCode())).collect(Collectors.toList());
            ModuleApiDO apiDO = new ModuleApiDO();
            apiDO.setModuleId(reqVO.getId());
            apiDO.setServiceName(CfgApiTypeEnum.MAPPING_GET_BY_ID.getDesc());
            apiDO.setServiceCode(CfgApiTypeEnum.MAPPING_GET_BY_ID.getCode());
            apiDO.setServiceType("1");
            if (CollUtil.isNotEmpty(aList)) {
                apiDO.setId(aList.get(0).getId());
            }
            moduleApiMapper.insert(apiDO);
            List<ModuleApiParamDO> list = moduleApiParamMapper.selectByModuleId(reqVO.getMappingModuleId(),CfgApiTypeEnum.GET_BY_ID.getCode());
            if (CollUtil.isNotEmpty(list)) {
                list.forEach(item -> {
                    item.setId(IdWorker.getId());
                    item.setApiId(apiDO.getId());
                    if (StrUtil.isNotEmpty(item.getFieldName())) {
                        for (TableField tableField : fields) {
                            if (StrUtil.isNotEmpty(tableField.getFieldValueUnderline()) && tableField.getFieldValueUnderline().equals(item.getFieldName())
                                    && StrUtil.isNotEmpty(tableField.getMappingFieldName())) {
                                item.setFieldName(tableField.getMappingFieldValueUnderline());
                            }
                        }
                    }
                });
                moduleApiParamMapper.insert(list);
            }
        }
    }


    /**
     * 保存视图模型
     * @param reqVO
     * @param type
     */
    private void saveCommonModule(ModuleInfoSaveReqVO reqVO, OperateTypeEnum type,List<ModuleApiDO> apiList ) {
        if (OperateTypeEnum.UPDATE.equals(type)) {
            apiList.addAll(moduleApiMapper.selectList(new QueryWrapper<ModuleApiDO>().lambda().eq(ModuleApiDO::getModuleId, reqVO.getId())));
            moduleRelationMapper.deleteByModuleId(reqVO.getId());
            moduleFieldMapper.deleteByModuleId(reqVO.getId());
            moduleSqlMapper.deleteByModuleId(reqVO.getId());
            moduleApiParamMapper.deleteByModuleId(reqVO.getId());
            moduleApiMapper.deleteByModuleId(reqVO.getId());
            moduleTableMapper.deleteByModuleId(reqVO.getId());
        }
        Map<String, ModuleTableDO> tableKeyTableMap = new HashMap<>();
        int i = 0;
        for (ModuleTableSaveReqVO item : reqVO.getTableList()) {
            item.setModuleId(reqVO.getId());
            ModuleTableDO tableDO = BeanUtils.toBean(item, ModuleTableDO.class);
            // 规则：表别名前端生成并传入 (使SQL支持多个相同子表场景)
            if (StringUtils.isBlank(item.getTableAlias())) {
                // 为空时，默认使用表名
                tableDO.setTableAlias(item.getTableName());
            }
            moduleTableMapper.insert(tableDO);
            item.setId(tableDO.getId());
            tableKeyTableMap.put(item.getTableKey(), tableDO);
            i++;
        }
        List<ModuleRelationFieldDO> rFieldList = new ArrayList<>();
        List<ModuleFieldDO> fieldList = new ArrayList<>();
        AtomicInteger num = new AtomicInteger(1);
        reqVO.getTableList().forEach(item -> {
            if (CollUtil.isNotEmpty(item.getRelationFields())) {
                item.getRelationFields().forEach(rField -> {
                    ModuleRelationFieldDO fieldDO = BeanUtils.toBean(rField, ModuleRelationFieldDO.class);
                    fieldDO.setModuleId(reqVO.getId());
                    fieldDO.setModuleTableId(tableKeyTableMap.get(item.getTableKey()).getId());
                    // 不仅仅是主表，还可以是主表的关联表--create时moduleTableId未生成，用tableKey识别
                    // 兼容老数据，为空默认主表
                    if(rField.getRelationTableKey() == null || rField.getRelationTableKey().isEmpty()) {
                        // 主表key
                        rField.setRelationTableKey(item.getMainTableKey());
                    }
                    // 新数据，关联表key
                    fieldDO.setRelationModuleTableId(tableKeyTableMap.get(rField.getRelationTableKey()).getId());
                    rFieldList.add(fieldDO);
                });
            }
            if (CollUtil.isNotEmpty(item.getFieldList())) {
                item.getFieldList().forEach(f -> {
                    ModuleFieldDO fieldDO = BeanUtils.toBean(f, ModuleFieldDO.class);
                    fieldDO.setModuleId(reqVO.getId());
                    fieldDO.setModuleTableId(tableKeyTableMap.get(item.getTableKey()).getId());
                    fieldDO.setColumnId(f.getFieldId());
                    // 非计算字段，清空
                    if (StringUtils.isBlank(fieldDO.getComputeSql())) {
                        fieldDO.setColumnName(null);
                        fieldDO.setColumnLength(null);
                        fieldDO.setColumnType(null);
                    }
                    // fix： id为空时，id赋值，否则使用原有id，即相当于更新逻辑
                    if (fieldDO.getId() == null || OperateTypeEnum.CREATE.equals(type)) {
                        fieldDO.setId(IdWorker.getId());
                    }
                    // if (item.getIsMain() && "默认主键".equals(fieldDO.getColumnComment())) {
                    //     fieldDO.setSysAliasName(LowCodeService.PK_NAME);
                    // } else {
                    //     if (StrUtil.isEmpty(fieldDO.getColumnAliasName())) {
                    String sequence = NumberUtils.getSequence(num.getAndIncrement());
                    fieldDO.setSysAliasName(sequence);
                        // }
                    // }
                    fieldList.add(fieldDO);
                });
            }
        });
        if (CollUtil.isNotEmpty(rFieldList)) {//保存关联表信息
            moduleRelationMapper.insert(rFieldList);
        }
        if (CollUtil.isNotEmpty(fieldList)) {//保存关联表信息
            moduleFieldMapper.insert(fieldList);
        }
        this.createApiAndSql(reqVO,apiList);
    }

    /**
     * 保存视图模型表
     * @param reqVO
     */
    private void saveModuleTable(ModuleInfoSaveReqVO reqVO) {
        if (CollUtil.isNotEmpty(reqVO.getTableList())) {
            ModuleTableSaveReqVO item = reqVO.getTableList().get(0);
            item.setModuleId(reqVO.getId());
            ModuleTableDO tableDO = BeanUtils.toBean(item, ModuleTableDO.class);
            tableDO.setTableAlias(ModuleSqlHelper.TABLE_ALIAS.get(0));
            tableDO.setTableId(null);
            moduleTableMapper.insert(tableDO);
            if (CollUtil.isNotEmpty(item.getFieldList())){
                List<ModuleFieldDO> fieldList = BeanUtils.toBean(item.getFieldList(), ModuleFieldDO.class);
                fieldList.forEach(f -> f.setModuleId(reqVO.getId()).setModuleTableId(tableDO.getId()));
                moduleFieldMapper.insert(fieldList);
            }
        }
    }

    /**
     * 保存sql模型
     * @param reqVO
     * @param type
     */
    private void saveSQLModule(ModuleInfoSaveReqVO reqVO, OperateTypeEnum type) {
        List<ModuleApiDO> apiList = new ArrayList<>();
        if (OperateTypeEnum.UPDATE.equals(type)) {
            apiList = moduleApiMapper.selectList(new QueryWrapper<ModuleApiDO>().lambda().eq(ModuleApiDO::getModuleId, reqVO.getId()));
            moduleSqlMapper.deleteByModuleId(reqVO.getId());
            moduleApiMapper.deleteByModuleId(reqVO.getId());
            moduleTableMapper.deleteByModuleId(reqVO.getId());
            moduleFieldMapper.deleteByModuleId(reqVO.getId());
        }
        ModuleSqlDO sqlDO = new ModuleSqlDO();
        sqlDO.setModuleId(reqVO.getId());
        sqlDO.setActionSql(reqVO.getModuleSql());
        sqlDO.setActionType(CfgActionTypeEnum.SELECT_PAGE.name());
        moduleSqlMapper.insert(sqlDO);
        ModuleApiDO apiDO = new ModuleApiDO();
        if (CollUtil.isNotEmpty(apiList)) {
            apiDO.setId(apiList.get(0).getId());
        }
        apiDO.setModuleId(reqVO.getId());
        apiDO.setServiceName(CfgApiTypeEnum.PAGE_LIST.getDesc());
        apiDO.setServiceCode(CfgApiTypeEnum.PAGE_LIST.getCode());
        apiDO.setServiceType("1");
        moduleApiMapper.insert(apiDO);
        this.saveModuleTable(reqVO);
    }

    /**
     * 保存bean模型
     * @param reqVO
     * @param type
     */
    private void saveBeanModule(ModuleInfoSaveReqVO reqVO, OperateTypeEnum type) {
        List<ModuleApiDO> apiList = new ArrayList<>();
        if (OperateTypeEnum.UPDATE.equals(type)) {
            apiList = moduleApiMapper.selectList(new QueryWrapper<ModuleApiDO>().lambda().eq(ModuleApiDO::getModuleId, reqVO.getId()));
            moduleApiMapper.deleteByModuleId(reqVO.getId());
            moduleTableMapper.deleteByModuleId(reqVO.getId());
            moduleFieldMapper.deleteByModuleId(reqVO.getId());
        }
        ModuleApiDO apiDO = new ModuleApiDO();
        if (CollUtil.isNotEmpty(apiList)) {
            apiDO.setId(apiList.get(0).getId());
        }
        apiDO.setModuleId(reqVO.getId());
        apiDO.setServiceName(CfgApiTypeEnum.PAGE_LIST.getDesc());
        apiDO.setServiceCode(CfgApiTypeEnum.PAGE_LIST.getCode());
        apiDO.setServiceType("1");
        moduleApiMapper.insert(apiDO);
        this.saveModuleTable(reqVO);
    }

    /**
     * 保存http模型
     * @param reqVO
     * @param type
     */
    private void saveHttpModule(ModuleInfoSaveReqVO reqVO, OperateTypeEnum type) {
        this.saveBeanModule(reqVO, type);
    }


    /**
     * 构建sql
     *
     * @param reqVO
     */
    private void createApiAndSql(ModuleInfoSaveReqVO reqVO,List<ModuleApiDO> apiList) {
        Long moduleId = reqVO.getId();
        List<ModuleTableRelation> tables = moduleTableMapper.selectByModuleId(moduleId);
        List<TableField> fields = moduleFieldMapper.selectByModuleId(moduleId,null);
        Map<Long, List<TableField>> fieldsMap = fields.stream().collect(Collectors.groupingBy(TableField::getModuleTableId));
        List<ModuleRelationField> relations = moduleRelationMapper.selectByModuleId(moduleId);
        Map<Long, List<ModuleRelationField>> relationssMap = relations.stream().collect(Collectors.groupingBy(ModuleRelationField::getModuleTableId));
        ModuleSqlHelper.initDefault(tables.get(0).getDataSourceId());
        for (ModuleTableRelation relation : tables) {
            relation.init();
            relation.setRelationFields(relationssMap.get(relation.getModuleTableId()));
            // 只读表、虚拟表 不需要 拼接 逻辑删除字段
            if (Objects.nonNull(reqVO.getReadonly()) && reqVO.getReadonly() || relation.isVirtualTable()) {
                relation.setIsLogicDelete(false);
            }
            List<TableField> tableFields = fieldsMap.get(relation.getModuleTableId());
            Set<TableField> fList = new HashSet<>(), pkList = new HashSet<>();
            if (CollUtil.isNotEmpty(tableFields)) {
                tableFields.forEach(item -> {
                    if (item.getIsPk() != null && item.getIsPk()) {
                        pkList.add(item);
                    }
                    fList.add(item);
                });
                //在表关联表中的字段也需要查询出来
                List<ModuleRelationField> rFileds = relation.getRelationFields();
                if (relation.getIsChild()) {//和父级的关联关系是子表
                    rFileds.forEach(v -> {
                        TableField field = TableField.newField(v.getFieldName(), v.getRelationFieldName(), v.getRelationMoudleTableId());
                        field.setFieldComment(v.getFieldComment());
                        relation.getSelectWhereFields().add(field);//查询查询使用父表的id
                    });
                    pkList.forEach(v -> {
                        TableField field = TableField.newField(v.getFieldName(), v.getFieldName(), v.getModuleTableId());
                        field.setFieldComment(v.getFieldComment());
                        relation.getDeleteWhereFields().add(field);
                    });
                } else if (CollUtil.isNotEmpty(pkList)) {
                    pkList.forEach(v -> {
                        TableField field = TableField.newField(v.getFieldName(), v.getFieldName(), v.getModuleTableId());
                        field.setFieldComment(v.getFieldComment());
                        relation.getDeleteWhereFields().add(field);
                        relation.getSelectWhereFields().add(field);
                        relation.getUpdateWhereFields().add(field);
                    });
                }
            }
            relation.setFields(fList).setPkFields(pkList);
        }
        // 构建表的嵌套的层级关系--主子表、关联表）
        List<ModuleTableRelation> rootList = new ArrayList<>();
        for (ModuleTableRelation relation : tables) {
            for (ModuleTableRelation relation1 : tables) {
                if (relation.getModuleTableId().equals(relation1.getRelationModuleTableTd())) {
                    if (relation1.getIsChild()) {
                        relation.getChildTable().add(relation1);
                    } else {
                        relation.getJoinTable().add(relation1);
                    }
                }
            }
            //找到根节点-关联表为空和子根节点-子表
            if (relation.getRelationModuleTableTd() == null || relation.getIsChild()) {
                rootList.add(relation);
            }
        }
        //从根节点开始构建查询字段
        for (ModuleTableRelation relation : rootList) {
            Set<String> selectFields = new HashSet<>();
            Set<TableField> allField = new HashSet<>();
            List<String> joinSqlList = new ArrayList<>();
            List<String> whereSqlList = new ArrayList<>();
            this.recursion(relation, joinSqlList, selectFields, allField);
            relation.setSelectFields(selectFields);
            relation.setJoinSql(joinSqlList);
            relation.setWhereSqlList(whereSqlList);
            relation.setAllFields(allField);
        }
        //不处理表名重复的
        List<ModuleSqlDO> sqlList = new ArrayList<>();
        // 过滤既是子表又是左联表
        List<ModuleTableRelation> rootLists = rootList.stream().filter(distinctByKeys(ModuleTableRelation::getModuleTableId, ModuleTableRelation::getRelationType)).collect(Collectors.toList());
        for (ModuleTableRelation relation : rootLists) {
            Map<String,String> map = ModuleSqlHelper.selectSql(relation);
            String selectPageSql = map.get(CfgActionTypeEnum.SELECT_PAGE.name());
            String getByIdSql = map.get(CfgActionTypeEnum.GET_BY_ID.name());
            String childSelect = map.get(CfgActionTypeEnum.CHILD_SELECT_LIST.name());
            if (StrUtil.isNotEmpty(childSelect)){
                sqlList.add(this.createModuleSql(relation, CfgActionTypeEnum.CHILD_SELECT_LIST, childSelect));
            }
            if (StrUtil.isNotEmpty(selectPageSql)){
                sqlList.add(this.createModuleSql(relation, CfgActionTypeEnum.SELECT_PAGE, selectPageSql));
            }
            if (StrUtil.isNotEmpty(getByIdSql)){
                sqlList.add(this.createModuleSql(relation, CfgActionTypeEnum.GET_BY_ID, getByIdSql));
            }
        }
        if (Objects.isNull(reqVO.getReadonly()) || !reqVO.getReadonly()) {
            // 过滤既是子表又是左联表
            List<ModuleTableRelation> collect = tables.stream().filter(distinctByKeys(ModuleTableRelation::getModuleTableId, ModuleTableRelation::getRelationType)).collect(Collectors.toList());
            for (ModuleTableRelation relation : collect) {
                // 只读表、虚拟表 不需要 insert、update、delete
                if (relation.getReadonly() || relation.isVirtualTable()) continue;
                if (relation.getIsChild()) {//当一张表即被join又被子表关联时，只生成insert和delete
                    sqlList.add(this.createModuleSql(relation, CfgActionTypeEnum.INSERT, ModuleSqlHelper.insertSql(relation)));
                    sqlList.add(this.createModuleSql(relation, CfgActionTypeEnum.DELETE, ModuleSqlHelper.deleteSqlWithIn(relation)));
                } else if (isChild(relation, rootList)) {//子表下关联的表
                    sqlList.add(this.createModuleSql(relation, CfgActionTypeEnum.INSERT, ModuleSqlHelper.insertSql(relation)));
                    sqlList.add(this.createModuleSql(relation, CfgActionTypeEnum.DELETE, ModuleSqlHelper.deleteSqlWithIn(relation)));
                } else {
                    sqlList.add(this.createModuleSql(relation, CfgActionTypeEnum.INSERT, ModuleSqlHelper.insertSql(relation)));
                    sqlList.add(this.createModuleSql(relation, CfgActionTypeEnum.UPDATE, ModuleSqlHelper.updateSql(relation)));
                    sqlList.add(this.createModuleSql(relation, CfgActionTypeEnum.DELETE, ModuleSqlHelper.deleteSqlWithIn(relation)));
                }
            }
        }
        moduleSqlMapper.insert(sqlList);
        //生成api和参数
        this.createModuleApi(reqVO,rootList,apiList);
    }

    @SafeVarargs
    public static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        Set<List<Object>> seen = ConcurrentHashMap.newKeySet();
        return t -> {
            List<Object> keys = new ArrayList<>();
            for (Function<? super T, ?> extractor : keyExtractors) {
                keys.add(extractor.apply(t));
            }
            return seen.add(keys);
        };
    }

    /**`
     * 生成api
     * @param reqVO
     * @param rootList
     * @param oldApiList
     */
    private void createModuleApi(ModuleInfoSaveReqVO reqVO,List<ModuleTableRelation> rootList, List<ModuleApiDO> oldApiList) {
        Map<String, ModuleApiDO> apiMap = new HashMap<>();
        if (CollUtil.isNotEmpty(oldApiList)) {
//            apiMap = oldApiList.stream().collect(Collectors.toMap(ModuleApiDO::getServiceCode, Function.identity()));
            apiMap = oldApiList.stream()
                    .collect(Collectors.toMap(ModuleApiDO::getServiceCode, Function.identity(), (existing, replacement) -> existing));
        }
        ModuleApiDO create = this.createModuleApi(rootList.get(0), CfgApiTypeEnum.CREATE,apiMap);
        ModuleApiDO update = this.createModuleApi(rootList.get(0), CfgApiTypeEnum.UPDATE,apiMap);
        ModuleApiDO delete = this.createModuleApi(rootList.get(0), CfgApiTypeEnum.DELETE,apiMap);
        ModuleApiDO list = this.createModuleApi(rootList.get(0), CfgApiTypeEnum.LIST,apiMap);
        ModuleApiDO pageList = this.createModuleApi(rootList.get(0), CfgApiTypeEnum.PAGE_LIST,apiMap);
        ModuleApiDO getById = this.createModuleApi(rootList.get(0), CfgApiTypeEnum.GET_BY_ID,apiMap);
        List<ModuleApiDO> apiList = Lists.newArrayList(pageList, getById,list);
        if (Objects.isNull(reqVO.getReadonly()) || !reqVO.getReadonly()) {
            apiList.add(create);
            apiList.add(update);
            apiList.add(delete);
        }
        //处理子表生成的api
        List<ModuleApiDO> childApiList = new ArrayList<>();
        for (ModuleTableRelation relation : rootList) {
            if (relation.getIsChild()) {
                childApiList.add(this.createModuleApi(relation, CfgApiTypeEnum.CHILD_LIST,apiMap));
            }
        }
        apiList.addAll(childApiList);
        moduleApiMapper.insert(apiList);

        List<ModuleApiParamDO> insertParam = Lists.newArrayList();
        List<ModuleApiParamDO> updateParam = Lists.newArrayList();
        List<ModuleApiParamDO> deleteParam = Lists.newArrayList();
        List<ModuleApiParamDO> pageParam = Lists.newArrayList();
        List<ModuleApiParamDO> getByIdParam = Lists.newArrayList();
        List<ModuleApiParamDO> childListParam = Lists.newArrayList();
        int index = 2;
        for (ModuleTableRelation relation : rootList) {
            if (relation.getIsMain()) {
                int sort = 1;
                List<TableField> insertFields = ModuleSqlHelper.getFields(relation,Boolean.FALSE,Boolean.FALSE);
                insertFields.forEach(field -> {
                    ModuleApiParamDO paramDO = this.createApiParam(field, create,sort);
                    paramDO.setParamType(CfgParamTypeEnum.REQUEST.name());
                    insertParam.add(paramDO);
                });
                List<TableField> updateFields = ModuleSqlHelper.getFields(relation,Boolean.TRUE,Boolean.FALSE);
                updateFields.forEach(field -> {
                    ModuleApiParamDO paramDO = this.createApiParam(field, update,sort);
                    paramDO.setParamType(CfgParamTypeEnum.REQUEST.name());
                    updateParam.add(paramDO);
                });
                relation.getPkFields().forEach(field -> {
                    ModuleApiParamDO deleteDO = this.createApiParam(field, delete,sort);
                    deleteDO.setParamType(CfgParamTypeEnum.REQUEST.name());
                    deleteDO.setRequired(true);
                    deleteParam.add(deleteDO);

                    ModuleApiParamDO whereDO = this.createApiParam(field, getById,sort);
                    whereDO.setParamType(CfgParamTypeEnum.REQUEST.name());
                    whereDO.setRequired(true);
                    getByIdParam.add(whereDO);

                    if (CollUtil.isNotEmpty(childApiList)) {
                        for(ModuleApiDO apiDO : childApiList){
                            ModuleApiParamDO childDO = this.createApiParam(field, apiDO,sort);
                            childDO.setParamType(CfgParamTypeEnum.REQUEST.name());
                            childListParam.add(childDO);
                        }
                    }
                });
                List<TableField> selectFields = ModuleSqlHelper.getFields(relation,Boolean.TRUE,Boolean.TRUE);
                selectFields.forEach(field -> {
                    ModuleApiParamDO body = this.createApiParam(field, getById,sort);
                    body.setParamType(CfgParamTypeEnum.RESPONSE.name());
                    getByIdParam.add(body);

                    ModuleApiParamDO param1 = this.createApiParam(field, pageList,sort);
                    param1.setId(IdWorker.getId());
                    param1.setParamType(CfgParamTypeEnum.REQUEST.name());
                    pageParam.add(param1);

                    ModuleApiParamDO param2 = this.createApiParam(field, pageList,sort);
                    param2.setId(IdWorker.getId());
                    param2.setParamType(CfgParamTypeEnum.RESPONSE.name());
                    pageParam.add(param2);
                });
            }
            if (relation.getIsChild()) {
                int sort = index;
                List<TableField> insertFields = ModuleSqlHelper.getFields(relation,Boolean.FALSE,Boolean.FALSE);
                insertParam.add(this.createChildParam(relation,create,sort,CfgParamTypeEnum.REQUEST));
                insertFields.forEach(field -> {
                    ModuleApiParamDO paramDO = this.createApiParam(field, create,sort);
                    paramDO.setParamType(CfgParamTypeEnum.REQUEST.name());
                    insertParam.add(paramDO);
                });
                List<TableField> updateFields = ModuleSqlHelper.getFields(relation,Boolean.TRUE,Boolean.FALSE);
                updateParam.add(this.createChildParam(relation,update,sort,CfgParamTypeEnum.REQUEST));
                updateFields.forEach(field -> {
                    ModuleApiParamDO paramDO = this.createApiParam(field, update,sort);
                    paramDO.setParamType(CfgParamTypeEnum.REQUEST.name());
                    updateParam.add(paramDO);
                });
                List<TableField> selectFields = ModuleSqlHelper.getFields(relation,Boolean.TRUE,Boolean.TRUE);
                getByIdParam.add(this.createChildParam(relation,getById,sort,CfgParamTypeEnum.RESPONSE));
                selectFields.forEach(field -> {
                    ModuleApiParamDO body = this.createApiParam(field, getById,sort);
                    body.setParamType(CfgParamTypeEnum.RESPONSE.name());
                    body.setModuleTableId(field.getModuleTableId());
                    getByIdParam.add(body);
                });
                if (CollUtil.isNotEmpty(childApiList)) {
                    for (ModuleApiDO apiDO : childApiList) {
                        if (apiDO.getServiceCode().startsWith(relation.getTableName().toUpperCase())) {
                            selectFields.forEach(field -> {
                                ModuleApiParamDO queryDO = this.createApiParam(field, apiDO,sort);
                                queryDO.setParamType(CfgParamTypeEnum.REQUEST.name());
                                queryDO.setModuleTableId(field.getModuleTableId());
                                childListParam.add(queryDO);

                                ModuleApiParamDO responseDO = this.createApiParam(field, apiDO,sort);
                                responseDO.setParamType(CfgParamTypeEnum.RESPONSE.name());
                                responseDO.setModuleTableId(field.getModuleTableId());
                                childListParam.add(responseDO);
                            });
                        }
                    }
                }
                if (StringUtils.isNotEmpty(relation.getParameterName())) {//子表处理API时如果存在设置了查询条件，则需要在生成API时生成
                    ModuleApiParamDO apiParameter = new ModuleApiParamDO();
                    apiParameter.setId(IdWorker.getId());
                    apiParameter.setModuleTableId(relation.getModuleTableId());
                    apiParameter.setApiId(getById.getId());
                    apiParameter.setFieldId(null);
                    apiParameter.setParamType(CfgParamTypeEnum.REQUEST.name());
                    apiParameter.setFieldName(relation.getParameterName());
                    apiParameter.setFieldComment(relation.getParameterName());
                    getByIdParam.add(apiParameter);
                }
            }
            index++;
        }
        List<ModuleApiParamDO> allParam = Lists.newArrayList();
        if (Objects.isNull(reqVO.getReadonly()) || !reqVO.getReadonly()) {
            allParam.addAll(insertParam);
            allParam.addAll(updateParam);
            allParam.addAll(deleteParam);
        }
        allParam.addAll(pageParam);
        allParam.addAll(getByIdParam);
        allParam.addAll(childListParam);
        List<ModuleApiParamDO> listParam = BeanUtils.toBean(pageParam,ModuleApiParamDO.class);
        listParam.forEach(item->{ item.setApiId(list.getId());item.setId(IdWorker.getId());});
        allParam.addAll(listParam);

        moduleApiParamMapper.insert(allParam);
    }


    /**
     * 创建子表参数
     * @param relation
     * @param api
     * @return
     */
    private ModuleApiParamDO createChildParam(ModuleTableRelation relation, ModuleApiDO api,Integer sort,CfgParamTypeEnum typeEnum) {
        ModuleApiParamDO apiParam = new ModuleApiParamDO();
        apiParam.setId(IdWorker.getId());
        apiParam.setModuleTableId(relation.getModuleTableId());
        apiParam.setApiId(api.getId());
        apiParam.setFieldName(String.format("list_%d", relation.getModuleTableId()));
        apiParam.setFieldComment(relation.getTableName() + "子表集合");
        apiParam.setSort(sort);
        apiParam.setParamType(typeEnum.name());
        return apiParam;
    }

    /**
     * 创建api参数
     *
     * @param field
     * @param api
     */
    private ModuleApiParamDO createApiParam(TableField field, ModuleApiDO api,Integer sort) {
        ModuleApiParamDO apiParam = new ModuleApiParamDO();
        apiParam.setId(IdWorker.getId());
        apiParam.setModuleTableId(field.getModuleTableId());
        apiParam.setApiId(api.getId());
        apiParam.setFieldId(field.getFieldId());
        apiParam.setFieldName(field.getFieldValueUnderline());
        apiParam.setFieldComment(field.getFieldComment());
        apiParam.setSort(sort);
        return apiParam;
    }

    /**
     * 判断是不是子表的join表
     * @param relation
     * @param rootList
     * @return
     */
    private boolean isChild(ModuleTableRelation relation, List<ModuleTableRelation> rootList) {
        boolean isChild = false;
        if (CollUtil.isNotEmpty(rootList)) {
            for (ModuleTableRelation item : rootList) {
                if (item.getIsChild()) {
                    if (isChild(relation, item)) {
                        isChild = true;
                    }
                }
            }
        }
        return isChild;
    }

    /**
     * 判断是不是子表的join
     *
     * @param relation
     * @param parent
     * @return
     */
    private boolean isChild(ModuleTableRelation relation, ModuleTableRelation parent) {
        if (parent.getModuleTableId().equals(relation.getRelationModuleTableTd())) {
            return true;
        }
        if (CollUtil.isNotEmpty(parent.getJoinTable())) {//最多支持子表join 3张表
            for (ModuleTableRelation item : parent.getJoinTable()) {
                if (item.getModuleTableId().equals(relation.getRelationModuleTableTd())) {
                    return true;
                }
                if (CollUtil.isNotEmpty(item.getJoinTable())) {
                    for (ModuleTableRelation it1 : item.getJoinTable()) {
                        if (it1.getModuleTableId().equals(relation.getRelationModuleTableTd())) {
                            return true;
                        }
                        if (CollUtil.isNotEmpty(it1.getJoinTable())) {
                            for (ModuleTableRelation it2 : it1.getJoinTable()) {
                                if (it2.getModuleTableId().equals(relation.getRelationModuleTableTd())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 构建sql
     *
     * @param relation
     * @param fields
     */
    private void recursion(ModuleTableRelation relation, List<String> joinSqlList, Set<String> fields, Set<TableField> allField) {
        if (CollUtil.isNotEmpty(relation.getFields())) {
            for (TableField field : relation.getFields()) {
                // 计算字段
                if (StringUtils.isNotBlank(field.getComputeSql())) {
                   String fieldKey = field.getComputeSql() + " as \"" + field.getSysAliasName() + "\"";
                   fields.add(fieldKey);
                } else {
                    // 脏数据过滤
                    if (field.getFieldName() == null) {
                        continue;
                    }
                    String fieldKey = ModuleSqlHelper.selectKeyWithFilter(field.getFieldName(), relation.getTableAlias(), field.getSysAliasName());
                    if (StrUtil.isNotEmpty(fieldKey)) {
                        fields.add(fieldKey);
                        allField.add(field);
                    }
                }
            }
        }
        if (!relation.getIsMain() && !relation.getIsChild()) {//拼接join
            joinSqlList.add(ModuleSqlHelper.joinSql(relation));
        }
        if (CollUtil.isNotEmpty(relation.getJoinTable())) {
            for (ModuleTableRelation join : relation.getJoinTable()) {
                recursion(join, joinSqlList,fields, allField);
            }
        }
    }

    /**
     * 判断是否在列表中
     *
     * @param fieldList
     * @param fieldId
     * @return
     */
    private boolean isExist(List<TableField> fieldList, Long fieldId) {
        if (CollUtil.isNotEmpty(fieldList)) {
            return fieldList.stream().noneMatch(item -> fieldId.equals(item.getFieldId()));
        }
        return false;
    }

    /**
     * 构建module sql
     *
     * @param actionType
     * @param sql
     * @return
     */
    private ModuleSqlDO createModuleSql(ModuleTableRelation result, CfgActionTypeEnum actionType, String sql) {
        ModuleSqlDO sqlDO = new ModuleSqlDO();
        sqlDO.setModuleId(result.getModuleId());
        sqlDO.setTableName(result.getTableName());
        sqlDO.setModuleTableId(result.getModuleTableId());
        sqlDO.setActionType(actionType.name());
        sqlDO.setActionSql(sql);
        return sqlDO;
    }

    /**
     * 创建api
     *
     * @param relation
     * @param apiTypeEnum
     * @return
     */
    private ModuleApiDO createModuleApi(ModuleTableRelation relation, CfgApiTypeEnum apiTypeEnum,Map<String, ModuleApiDO> apiMap) {
        ModuleApiDO createApi = new ModuleApiDO();
        createApi.setModuleId(relation.getModuleId());
        createApi.setServiceName(apiTypeEnum.getDesc());
        if(apiTypeEnum.getCode().equals(CfgApiTypeEnum.CHILD_LIST.getCode())) {
            // 表名+表别名
            createApi.setServiceCode(String.format("%s_%s",(relation.getTableName() + relation.getTableAlias()).toUpperCase(),apiTypeEnum.getCode()));
        }else {
            createApi.setServiceCode(apiTypeEnum.getCode());
        }
        createApi.setServiceType("1");
        if (apiMap.get(createApi.getServiceCode()) != null) {
           createApi.setId(apiMap.get(createApi.getServiceCode()).getId());
        }
        return createApi;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteModuleInfo(Long id) {
        // 校验该模型是否在页面配置使用
        List<PageApiDO> pageApiDOS = pageApiMapper.selectList(PageApiDO::getModuleId, id);
        if (CollUtil.isNotEmpty(pageApiDOS)) {
            throw exception(MODULE_INFO_USE);
        }
        moduleInfoMapper.deleteByModuleId(id);
        moduleRelationMapper.deleteByModuleId(id);
        moduleFieldMapper.deleteByModuleId(id);
        moduleSqlMapper.deleteByModuleId(id);
        moduleApiParamMapper.deleteByModuleId(id);
        moduleApiMapper.deleteByModuleId(id);
        moduleTableMapper.deleteByModuleId(id);
    }

    @Override
    public ModuleInfoRespVO getModuleInfo(Long id) {
        ModuleInfoDO moduleInfoDO = moduleInfoMapper.selectById(id);
        if (moduleInfoDO == null) {
            throw exception(MODULE_INFO_NOT_EXISTS);
        }
        if (CfgModuleTypeEnum.SQL.getCode().equals(moduleInfoDO.getModuleType())) {
            List<ModuleSqlCache> list = moduleSqlMapper.selectSqlCache(id);
            if (CollUtil.isNotEmpty(list)){
               moduleInfoDO.setModuleSql(list.get(0).getActionSql());
            }
        }
        ModuleInfoRespVO respVO = BeanUtils.toBean(moduleInfoDO, ModuleInfoRespVO.class);
        List<ModuleApiDO> moduleApiList = moduleApiMapper.selectList(ModuleApiDO::getModuleId, id);
        List<ModuleTableSaveReqVO> tables = moduleTableMapper.selectTable(Lists.newArrayList(id));
        List<ModuleFieldSaveReqVO> fields = moduleFieldMapper.selectField(id);
        List<ModuleRelationFieldDO> relation = moduleRelationMapper.selectList(ModuleRelationFieldDO::getModuleId, id);
        if (CollUtil.isNotEmpty(tables)) {
            Map<Long,List<ModuleRelationFieldDO>> rMap = relation.stream().collect(Collectors.groupingBy(ModuleRelationFieldDO::getModuleTableId));
            Map<Long,List<ModuleFieldSaveReqVO>> fMap = fields.stream().collect(Collectors.groupingBy(ModuleFieldSaveReqVO::getModuleTableId,
                    Collectors.collectingAndThen(
                            Collectors.toList(), // 先将分组后的元素收集到 List 中
                            list -> { // 对每个分组内的 List 进行排序
                                list.sort(Comparator.comparing(ModuleFieldSaveReqVO::getSort, Comparator.nullsLast(Integer::compareTo)));
                                return list;
                            }
                    )
            ));
            for (ModuleTableSaveReqVO table : tables) {
                List<ModuleRelationFieldDO> relationFieldList = rMap.get(table.getId());
                List<ModuleFieldSaveReqVO> fieldList = fMap.get(table.getId());
                table.setRelationFields(BeanUtils.toBean(relationFieldList, ModuleRelationFieldSaveReqVO.class));
                table.setFieldList(fieldList);
                if (!CfgModuleTypeEnum.COMMON.getCode().equals(moduleInfoDO.getModuleType())) {
                    table.setTableName("moduleTable");
                    table.setTableComment("模型表");
                }
            }
            tables.sort(Comparator.comparing(ModuleTableSaveReqVO::getIsMain, Comparator.nullsLast(Comparator.naturalOrder())));
            tables.sort(Comparator.comparing(ModuleTableSaveReqVO::getTableAlias, Comparator.nullsLast(Comparator.naturalOrder())));
            respVO.setTableList(tables);
        }
        if (CollUtil.isNotEmpty(moduleApiList)) {
            respVO.setApiList(BeanUtils.toBean(moduleApiList, ModuleApiRespVO.class));
        }
        return respVO;
    }

    @Override
    public List<ModuleInfoDO> getModuleInfoList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return moduleInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ModuleInfoDO> getModuleInfoPage(ModuleInfoPageReqVO pageReqVO) {
        return moduleInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TreeNode> listModuleInfo() {
        List<ModuleInfoDO> list = moduleInfoMapper.selectList();
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<TreeNode> treeList = new ArrayList<>();
        Set<Long> menuSet = new HashSet<>();
        TreeNode node = null;
        for (ModuleInfoDO moduleInfoDO : list) {
            node = new TreeNode();
            node.setId(moduleInfoDO.getId());
            node.setName(moduleInfoDO.getModuleName());
            node.setParentId(moduleInfoDO.getMenuId());
            if (Objects.nonNull(moduleInfoDO.getMenuId())) {
                treeList.add(node);
                menuSet.add(moduleInfoDO.getMenuId());
            }
        }
        List<FunctionInfoDO> functionList = functionInfoMapper.selectBatchIds(menuSet);
        if (CollUtil.isNotEmpty(functionList)) {
            for (FunctionInfoDO functionInfoDO : functionList) {
                node = new TreeNode();
                node.setId(functionInfoDO.getId());
                node.setName(functionInfoDO.getFunctionName());
                node.setParentId(0L);
                treeList.add(node);
            }
        }
        return treeList;
    }

    /**
     * 刷新模型
     *
     * @param id
     */
    @Override
    public void refreshModuleInfo(Long id) {
        // 模型主表
        ModuleInfoDO moduleInfo = moduleInfoMapper.selectOne("id", id);
        if(moduleInfo==null){
            return;
        }

        // A1模型配置-表
        List<ModuleTableDO> moduleTableList = moduleTableMapper.selectList(MODULE_ID, id);
        if(moduleTableList!=null && !moduleTableList.isEmpty()){
            for(ModuleTableDO moduleTable : moduleTableList){
                // 1、刷新字段
                this.refreshModuleField(moduleTable, moduleInfo, id);
            }
        }

        // 2、刷新sql
        this.refreshModuleSql(moduleInfo);
        // 3、刷新缓存
        moduleCacheComponent.refreshCacheByModuleId(id);
    }

    /**
     * 刷新sql
     *
     * @param moduleInfo
     */
    private void refreshModuleSql(ModuleInfoDO moduleInfo) {
        ModuleInfoSaveReqVO reqVO = new ModuleInfoSaveReqVO();
        reqVO.setId(moduleInfo.getId());
        reqVO.setReadonly(moduleInfo.getReadonly());
        reqVO.setMappingModuleId(moduleInfo.getMappingModuleId());

        List<ModuleApiDO> apiList = moduleApiMapper.selectList(new QueryWrapper<ModuleApiDO>().lambda().eq(ModuleApiDO::getModuleId, reqVO.getId()));
        moduleSqlMapper.deleteByModuleId(reqVO.getId());
        moduleApiParamMapper.deleteByModuleId(reqVO.getId());
        moduleApiMapper.deleteByModuleId(reqVO.getId());
        this.createApiAndSql(reqVO, apiList);
        this.saveMappingModule(reqVO,apiList);
    }

    /**
     * 刷新字段
     *
     * @param moduleTable
     * @param moduleInfo
     * @param id
     */
    private void refreshModuleField(ModuleTableDO moduleTable, ModuleInfoDO moduleInfo, Long id) {
        // B1数据库管理-源表
        TableDefinitionDO tableDefinition = tableDefinitionMapper.selectOne("id", moduleTable.getTableId());

        // 1、源表没有了，删除模型配置中的数据（模型主表中的主表id、A1模型配置-表、A2模型配置-字段）
        if(tableDefinition==null){
                    /*// 自己在模型里 手工新增的计算字段 不删
                    moduleFieldMapper.delete(
                            new QueryWrapper<ModuleFieldDO>()
                            .eq(MODULE_TABLE_ID, String.valueOf(moduleTable.getId())).isNotNull("column_id"));*/
            // 自己在模型里 手工新增的计算字段 删掉
            moduleFieldMapper.delete(
                    new QueryWrapper<ModuleFieldDO>()
                            .eq(MODULE_TABLE_ID, String.valueOf(moduleTable.getId())));

            List<ModuleFieldDO> moduleFieldList = moduleFieldMapper.selectList(MODULE_TABLE_ID, moduleTable.getId());
            if(moduleFieldList==null || moduleFieldList.isEmpty()){
                moduleTableMapper.deleteById(moduleTable);
                if(moduleTable.getTableId().equals(moduleInfo.getMainTableId())){
                    moduleInfo.setMainTableId(null);
                    moduleInfoMapper.updateById(moduleInfo);
                }
            }
        }

        // 2、源表还在，就处理字段：增add、删delete、改update
        else{
            // A2模型配置-字段
            List<ModuleFieldDO> moduleFieldList = moduleFieldMapper.selectList(MODULE_TABLE_ID, moduleTable.getId());
            // B2数据库管理-表字段
            List<ColumnDefinitionDO> columnDefinitionList = columnDefinitionMapper.selectList("table_id", moduleTable.getTableId());
            if(columnDefinitionList!=null && !columnDefinitionList.isEmpty()){
                columnDefinitionList.sort(Comparator.comparing(ColumnDefinitionDO::getSort));
            }

            // 没有字段，就没有要处理的
            if((moduleFieldList==null || moduleFieldList.isEmpty()) && (columnDefinitionList==null || columnDefinitionList.isEmpty())){
                // 返回，处理下一张表
                return;
            }

            // 对比 B2数据库管理-表字段、 A2模型配置-字段 中的字段

            // 情况一：字段全部新增
            if((moduleFieldList==null || moduleFieldList.isEmpty()) && (columnDefinitionList!=null && !columnDefinitionList.isEmpty())){
                AtomicInteger num = new AtomicInteger(1);
                for(ColumnDefinitionDO vo : columnDefinitionList){
                    ModuleFieldDO newVo = new ModuleFieldDO();
                    newVo.setModuleId(id);
                    newVo.setModuleTableId(moduleTable.getId());
                    newVo.setColumnId(vo.getId());
                    newVo.setColumnComment(vo.getColumnComment());
                    String sequence = NumberUtils.getSequence(num.getAndIncrement());
                    newVo.setSysAliasName(sequence);
                }
                moduleFieldMapper.insertOrUpdateBatch(moduleFieldList);
            }
            // 情况二：字段全部删除
            // 自己在模型里 手工新增的计算字段 不删
            else if((moduleFieldList!=null && !moduleFieldList.isEmpty()) && (columnDefinitionList==null || columnDefinitionList.isEmpty())){
                // 自己在模型里 手工新增的计算字段 不删
                moduleFieldMapper.delete(
                        new QueryWrapper<ModuleFieldDO>()
                                .eq(MODULE_TABLE_ID, String.valueOf(moduleTable.getId())).isNotNull("column_id"));
                List<ModuleFieldDO> list = moduleFieldMapper.selectList(MODULE_TABLE_ID, moduleTable.getId());
                if(list==null || list.isEmpty()){
                    moduleTableMapper.deleteById(moduleTable);
                    if(moduleTable.getTableId().equals(moduleInfo.getMainTableId())){
                        moduleInfo.setMainTableId(null);
                        moduleInfoMapper.updateById(moduleInfo);
                    }
                }
            }
            // 情况三：字段有删、有增、有改
            else{
                // A2模型配置-字段 第一遍循环是为了 删除、修改字段
                for(ModuleFieldDO vo : moduleFieldList){
                    // 自己在模型里 手工新增的计算字段 不处理
                    if(vo.getColumnId()==null){
                        vo.setRefreshType(UPDATE);
                    }
                    else{
                        boolean flag = false;
                        // B2数据库管理-表字段
                        for(ColumnDefinitionDO column : columnDefinitionList){
                            if(column.getId().equals(vo.getColumnId())){
                                vo.setColumnComment(column.getColumnComment());
                                vo.setRefreshType(UPDATE);
                                flag = true;
                                break;
                            }
                        }
                        if(!flag){
                            vo.setRefreshType("delete");
                        }
                    }
                }

                // 第二遍循环是为了 新增字段
                List<ModuleFieldDO> addList = new ArrayList<>();
                // B2数据库管理-表字段
                for(ColumnDefinitionDO column : columnDefinitionList){
                    boolean flag = false;
                    // A2模型配置-字段
                    for(ModuleFieldDO vo : moduleFieldList){
                        if(column.getId().equals(vo.getColumnId())){
                            flag = true;
                            break;
                        }
                    }
                    if(!flag){
                        ModuleFieldDO newVo = new ModuleFieldDO();
                        newVo.setModuleId(id);
                        newVo.setModuleTableId(moduleTable.getId());
                        newVo.setColumnId(column.getId());
                        newVo.setColumnComment(column.getColumnComment());
                        newVo.setRefreshType("add");
                        addList.add(newVo);
                    }
                }
                if(!addList.isEmpty()){
                    if(moduleFieldList==null || moduleFieldList.isEmpty()){
                        moduleFieldList = new ArrayList<>();
                    }
                    moduleFieldList.addAll(addList);
                }

                if(moduleFieldList!=null && !moduleFieldList.isEmpty()){
                    List<ModuleFieldDO> newList = new ArrayList<>();
                    AtomicInteger num = new AtomicInteger(1);
                    for(ModuleFieldDO vo : moduleFieldList){
                        if("add".equals(vo.getRefreshType()) || UPDATE.equals(vo.getRefreshType())){
                            String sequence = NumberUtils.getSequence(num.getAndIncrement());
                            vo.setSysAliasName(sequence);
                            newList.add(vo);
                        }
                    }
                    moduleFieldMapper.insertOrUpdateBatch(moduleFieldList);
                }
            }
        }
    }

    /**
     * 复制模型信息
     *
     * @param id
     */
    @Override
    public void copyPageInfo(Long id) {
        ModuleInfoDO vo = moduleInfoMapper.selectById(id);
        // 插入
        ModuleInfoDO info = BeanUtil.copyProperties(vo, ModuleInfoDO.class);
        info.setOldId(id);
        info.setId(null);
        info.setModuleName(info.getModuleName()+"(1)");
        info.setModuleCode(info.getModuleCode()+"(1)");
        this.initData(info);
        moduleInfoMapper.insert(info);

        // 模型关联
        List<ModuleTableDO> tableList = moduleTableMapper.selectList(MODULE_ID, id);
        if (!tableList.isEmpty()) {
            tableList.forEach(table -> {
                table.setOldId(table.getId());
                table.setModuleId(info.getId());
                table.setId(null);
                this.initData(table);
            });
            moduleTableMapper.insertBatch(tableList);
        }

        // 模型关联字段
        List<ModuleRelationFieldDO> rFieldList = moduleRelationMapper.selectList(MODULE_ID, id);
        if(rFieldList!=null && !rFieldList.isEmpty()){
            rFieldList.forEach(rField -> {
                rField.setOldId(rField.getId());
                rField.setModuleId(info.getId());
                if(!tableList.isEmpty()){
                    for(ModuleTableDO table : tableList){
                        if(table.getOldId().equals(rField.getModuleTableId())){
                            rField.setModuleTableId(table.getId());
                            break;
                        }
                    }
                }
                rField.setId(null);
                this.initData(rField);
            });
            moduleRelationMapper.insert(rFieldList);
        }

        // 模型涉及字段
        List<ModuleFieldDO> fieldList = moduleFieldMapper.selectList(MODULE_ID, id);
        if(fieldList!=null && !fieldList.isEmpty()){
            fieldList.forEach(field -> {
                field.setOldId(field.getId());
                field.setModuleId(info.getId());
                if(!tableList.isEmpty()){
                    for(ModuleTableDO table : tableList){
                        if(table.getOldId().equals(field.getModuleTableId())){
                            field.setModuleTableId(table.getId());
                            break;
                        }
                    }
                }
                field.setId(null);
                this.initData(field);
            });
            moduleFieldMapper.insert(fieldList);
        }

        // 模型新建后生成的SQL
        List<ModuleSqlDO> sqlList = moduleSqlMapper.selectList(MODULE_ID, id);
        if(sqlList!=null && !sqlList.isEmpty()){
            sqlList.forEach(sql -> {
                sql.setOldId(sql.getId());
                sql.setModuleId(info.getId());
                if(!tableList.isEmpty()){
                    for(ModuleTableDO table : tableList){
                        if(table.getOldId().equals(sql.getModuleTableId())){
                            sql.setModuleTableId(table.getId());
                            break;
                        }
                    }
                }
                sql.setId(null);
                this.initData(sql);
            });
            moduleSqlMapper.insert(sqlList);
        }

        // 模型API
        List<ModuleApiDO> apiList = moduleApiMapper.selectList(MODULE_ID, id);
        List<Long> apiIdList = apiList.stream().map(ModuleApiDO::getId).collect(Collectors.toList());
        if(!apiList.isEmpty()){
            apiList.forEach(api -> {
                api.setOldId(api.getId());
                api.setModuleId(info.getId());
                api.setId(null);
                this.initData(api);
            });
            moduleApiMapper.insert(apiList);
        }

        // 模型参数
        if(!apiIdList.isEmpty()){
            List<ModuleApiParamDO> allParam = moduleApiParamMapper.selectList("api_id", apiIdList);
            if(allParam!=null && !allParam.isEmpty()){
                allParam.forEach(all -> {
                    all.setOldId(all.getId());
                    if(!apiList.isEmpty()){
                        for(ModuleApiDO api : apiList){
                            if(api.getOldId().equals(all.getApiId())){
                                all.setApiId(api.getId());
                                break;
                            }
                        }
                    }
                    if(!tableList.isEmpty()){
                        for(ModuleTableDO table : tableList){
                            if(table.getOldId().equals(all.getModuleTableId())){
                                all.setModuleTableId(table.getId());
                                break;
                            }
                        }
                    }
                    all.setId(null);
                    this.initData(all);
                });
                moduleApiParamMapper.insert(allParam);
            }
        }
    }

    @Override
    public void refreshCache() {
        cacheComponent.initCache();
    }

    /**
     * 初始化
     *
     * @param vo
     */
    private void initData(BaseDO vo) {
        vo.setCreator(null);
        vo.setCreateTime(null);
        vo.setUpdater(null);
        vo.setUpdateTime(null);
    }

    /**
     * 处理回显字段
     *
     * @param moduleId
     */
    private void updateTextTable(Long moduleId) {
        // 所有的表
        List<ModuleTableVO> tableList = moduleTableMapper.findTableByModuleId(moduleId);
        if(tableList==null || tableList.isEmpty()){
            return;
        }
        List<Long> tableIdAll = tableList.stream().map(ModuleTableVO::getId).collect(Collectors.toList());
        // 需要回显的字段
        List<ModuleFieldVO> fieldList = moduleFieldMapper.findFieldByModuleId(moduleId, tableIdAll);
        if(fieldList==null || fieldList.isEmpty()){
            return;
        }
        // 需要调整的sql
        List<ModuleSqlDO> sqlList = moduleSqlMapper.selectList(new LambdaQueryWrapper<ModuleSqlDO>()
                    .eq(ModuleSqlDO::getModuleId, moduleId)
                    .in(ModuleSqlDO::getModuleTableId, tableIdAll)
                    .in(ModuleSqlDO::getActionType,Lists.newArrayList(CfgActionTypeEnum.GET_BY_ID.name(),CfgActionTypeEnum.CHILD_SELECT_LIST.name(),CfgActionTypeEnum.SELECT_PAGE.name())));

        if(sqlList!=null && !sqlList.isEmpty()) {
            for (ModuleSqlDO sqlDO : sqlList) {
                String sql = sqlDO.getActionSql();
                int num = 0;
                for (ModuleFieldVO field : fieldList) {
                    if(StringUtils.isNotBlank(sql) && !sql.contains(field.getTableName() +" "+ field.getTableAlias())){
                        continue;
                    }
                    /*String columnStr = "RE"+num+".";
                    String columnOld = field.getTableAlias() + "." +field.getColumnName();
                    String columnNew = columnStr+field.getTextColumn();
                    String tableKey = field.getTextTableKey();
                    String tableStr = " left join "+field.getTextTable()+" RE"+num + " on FIND_IN_SET("+columnStr+tableKey+", "+columnOld+") > 0 ";
                    sql = sql.replace("from", ", GROUP_CONCAT(distinct "+columnNew + " SEPARATOR ',') as " +field.getSysAliasName()+"Text from");
                    sql = sql.replace("<where>", tableStr+"<where>");
                    num++;*/

                    String columnStr = "RE"+num+".";
                    String columnOld = "a." +field.getColumnName();
                    String tableOld = field.getTableName();
                    String columnOldId = field.getTableAlias() + ".id";
                    String columnNew = columnStr+field.getTextColumn();
                    String tableKey = field.getTextTableKey();

                    String tableStr = " left join (SELECT a.id, LISTAGG(RE."+field.getTextColumn()+", ',') WITHIN GROUP (ORDER BY RE."+tableKey+") AS "+field.getTextColumn()+
                            " FROM " +tableOld+" a, " +field.getTextTable()+" RE WHERE RE."+tableKey+" IN (" +
                            " SELECT TO_CHAR(REGEXP_SUBSTR("+columnOld+", '[^,]+', 1, LEVEL)) FROM DUAL " +
                            " CONNECT BY LEVEL <![CDATA[ <= ]]> LENGTH("+columnOld+") - LENGTH(REPLACE("+columnOld+", ',', '')) + 1) GROUP BY" +
                            " a.id) RE"+num+" on RE"+num+".id = "+columnOldId+" ";
                    sql = sql.replace("from", ","+columnNew + " as " +field.getSysAliasName()+"Text from");
                    sql = sql.replace("<where>", tableStr+"<where>");
                    num++;
                }
                sqlDO.setActionSql(sql);
                moduleSqlMapper.updateById(sqlDO);
            }
        }
    }

}
