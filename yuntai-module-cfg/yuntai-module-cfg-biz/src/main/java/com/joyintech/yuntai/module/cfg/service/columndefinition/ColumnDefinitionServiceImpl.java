package com.joyintech.yuntai.module.cfg.service.columndefinition;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.COLUMN_DEFINITION_NOT_EXISTS;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.query.SQLQuery;
import com.joyintech.yuntai.framework.common.util.json.JsonUtils;
import com.joyintech.yuntai.framework.mybatis.core.util.JdbcUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.ComponentAttributeSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleInfoRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleInfoSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute.ColumnComponentAttributeDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulefield.ModuleFieldDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduletable.ModuleTableDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.columncomponentattribute.ColumnComponentAttributeMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.modulefield.ModuleFieldMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduletable.ModuleTableMapper;
import com.joyintech.yuntai.module.cfg.service.moduleinfo.ModuleInfoService;
import com.joyintech.yuntai.module.infra.api.db.DataSourceConfigApi;
import com.joyintech.yuntai.module.infra.api.db.dto.DataSourceConfigRespDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition.ColumnDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.tabledefinition.TableDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.columndefinition.ColumnDefinitionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.tabledefinition.TableDefinitionMapper;
import com.joyintech.yuntai.module.cfg.service.sqlgen.CommonDdlService;
import com.joyintech.yuntai.module.cfg.sqlgen.generator.TableStatementGenerator;
import com.joyintech.yuntai.module.cfg.sqlgen.generator.TableStatementGeneratorFactory;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Column;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Table;
import com.joyintech.yuntai.module.cfg.sqlgen.trans.DynamicDataSourceExecuteHelper;

/**
 * 字段定义 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ColumnDefinitionServiceImpl implements ColumnDefinitionService {
    @Resource
    private ColumnDefinitionMapper columnDefinitionMapper;

    @Resource
    private TableDefinitionMapper tableDefinitionMapper;

    @Resource
    private ColumnComponentAttributeMapper attributeMapper;

    @Resource
    private CommonDdlService commonDdlService;
    @Resource
    private DataSourceConfigApi dataSourceConfigApi;
    @Resource
    private ModuleFieldMapper moduleFieldMapper;
    @Resource
    private ModuleTableMapper moduleTableMapper;
    @Resource
    private ModuleInfoService moduleInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveColumnDefinition(List<ColumnDefinitionSaveReqVO> reqVOList,Boolean isTemp, String tableSql) {
        List<ColumnDefinitionDO> insertList = new ArrayList<>();
        List<ColumnDefinitionDO> updateList = new ArrayList<>();
        List<ColumnComponentAttributeDO> attributeList = new ArrayList<>();
        List<ColumnDefinitionDO> executeList = new ArrayList<>();
        List<Long> deleteIds = new ArrayList<>();
        for (ColumnDefinitionSaveReqVO reqVO : reqVOList) {
            ColumnDefinitionDO columnDefinition = BeanUtils.toBean(reqVO, ColumnDefinitionDO.class);
            if (!isTemp && (Objects.isNull(reqVO.getStatus()) || Objects.equals(reqVO.getStatus(), false))) {
                columnDefinition.setStatus(true);
                executeList.add(columnDefinition);
            }else if(isTemp && Objects.isNull(reqVO.getStatus())) {
                columnDefinition.setStatus(false);
            }
            if (columnDefinition.getId() == null) {
                columnDefinition.setId(IdWorker.getId());
                insertList.add(columnDefinition);
            } else {
                updateList.add(columnDefinition);
                deleteIds.add(columnDefinition.getId());
            }
            if (StrUtil.isNotEmpty(reqVO.getComponentCode()) && CollUtil.isNotEmpty(reqVO.getPropsList())) {
                reqVO.getPropsList().forEach(attribute -> {
                    ColumnComponentAttributeDO attr = new ColumnComponentAttributeDO();
                    attr.setId(IdWorker.getId());
                    attr.setColumnId(columnDefinition.getId());
                    attr.setAttributeId(attribute.getId());
                    attr.setAttributeValue(attribute.getAttributeValue());
                    attr.setPageId(attribute.getPageId());
                    attr.setPriority(attribute.getPriority());
                    attributeList.add(attr);
                });
            }
        }
        List<Long> idList = new ArrayList<>();
        // insertList非空时，批量插入
        if (CollUtil.isNotEmpty(insertList) ) {
            columnDefinitionMapper.insertBatch(insertList);
            idList.addAll(insertList.stream().map(ColumnDefinitionDO::getId).collect(Collectors.toList()));
        }
        // updateList非空时，批量更新
        if (CollUtil.isNotEmpty(updateList)) {
            attributeMapper.delete(new LambdaQueryWrapper<ColumnComponentAttributeDO>().in(CollectionUtil.isNotEmpty(deleteIds),
                    ColumnComponentAttributeDO::getColumnId, deleteIds));
            columnDefinitionMapper.updateBatch(updateList);
            idList.addAll(updateList.stream().map(ColumnDefinitionDO::getId).collect(Collectors.toList()));
        }
        columnDefinitionMapper.deletePhysicsByTableId(reqVOList.get(0).getTableId(),idList);
        if (CollUtil.isNotEmpty(attributeList)) {
            attributeMapper.insertBatch(attributeList);
        }
        if (!isTemp ) {
            // 仅创建时才执行建表语句
            TableDefinitionDO tableDefinitionDO = tableDefinitionMapper.getAndCheck(reqVOList.get(0).getTableId());
            // sql语句生成器
            TableStatementGenerator generator = TableStatementGeneratorFactory.getGenerator(tableDefinitionDO.getDatasourceId());
            if (!tableDefinitionDO.getStatus()) {
                List<Column> columnList = BeanUtils.toBean(reqVOList, Column.class);
                //校验字段类型是否正确
                generator.validate(columnList);
                Table table = new Table();
                table.setTableName(tableDefinitionDO.getTableName());
                table.setTableComment(tableDefinitionDO.getTableComment());
                table.setColumns(columnList);
                List<String> sqlList = generator.generateCreateTableStatement(table);
                // 根据数据源创建表、不同数据库的建表语句差异
                DynamicDataSourceExecuteHelper.executeWithDataSource(tableDefinitionDO.getDatasourceId(), () -> {
                    commonDdlService.executeSql(sqlList);
                    return null;
                });
            }else if(CollUtil.isNotEmpty(executeList)) {
                List<Column> columnList = BeanUtils.toBean(executeList, Column.class);
                //校验字段类型是否正确
                generator.validate(columnList);
                List<String> sqlList = generator.generateAddColumnStatement(tableDefinitionDO.getTableName(),columnList);
                if (CollUtil.isNotEmpty(sqlList)) {
                    DynamicDataSourceExecuteHelper.executeWithDataSource(tableDefinitionDO.getDatasourceId(), () -> {
                        commonDdlService.executeSql(sqlList);
                        return null;
                    });
                }
            }
            if (!tableDefinitionDO.getStatus()) {//正式保存后更新表状态
                tableDefinitionDO.setStatus(true);
                tableDefinitionMapper.updateById(tableDefinitionDO);
            }
            //处理已经入表的字段的类型
            TableInfo tableInfo = getTableInfo(tableDefinitionDO.getDatasourceId(),tableDefinitionDO.getTableName());
            if (Objects.nonNull(tableInfo)) {
                Map<String,TableField> colMap = tableInfo.getFields().stream().collect(Collectors.toMap(TableField::getColumnName, tf -> tf));
                colMap.forEach((k,v) -> {
                    ColumnDefinitionDO columnDefinitionDO = new ColumnDefinitionDO();
                    columnDefinitionDO.setJavaType(v.getColumnType().getType());
                    columnDefinitionDO.setTableId(tableDefinitionDO.getId());
                    if (Objects.nonNull(v.getMetaInfo()) && Objects.nonNull(v.getMetaInfo().getJdbcType())) {
                        columnDefinitionDO.setJdbcType(v.getMetaInfo().getJdbcType().name());
                    }
                    columnDefinitionDO.setColumnName(k);
                    columnDefinitionMapper.updateJavaType(columnDefinitionDO);
                });
            }
        }

        // 更新虚拟表的sql
        if(StringUtils.isNotBlank(tableSql) && reqVOList.get(0).getTableId()!=null){
            TableDefinitionDO updateObj = tableDefinitionMapper.selectById(reqVOList.get(0).getTableId());
            if(updateObj!=null){
                updateObj.setTableSql(tableSql);
                tableDefinitionMapper.updateById(updateObj);
            }
        }
        //刷新模型数据
        if(!isTemp){
            if(CollUtil.isNotEmpty(reqVOList)){
                ColumnDefinitionSaveReqVO columnDefinitionSaveReqVO = reqVOList.get(0);
                Long tableId = columnDefinitionSaveReqVO.getTableId();
                refreshModuleInfoByTable(tableId);
            }
        }
    }

    private void refreshModuleInfoByTable(Long tableId){
        List<ModuleTableDO> moduleTableDOList = moduleTableMapper.selectList(ModuleTableDO::getTableId, tableId);
        if(CollUtil.isNotEmpty(moduleTableDOList)){
            Long moduleId = moduleTableDOList.get(0).getModuleId();
            moduleInfoService.refreshModuleInfo(moduleId);
        }
    }

    private TableInfo getTableInfo(Long dataSourceConfigId, String name) {
        // 获得数据源配置
        DataSourceConfigRespDTO config = dataSourceConfigApi.getDataSourceConfig(dataSourceConfigId);
        Assert.notNull(config, "数据源({}) 不存在！", dataSourceConfigId);
        DbType dbType = JdbcUtils.getDbType(config.getUrl());
        // 使用 MyBatis Plus Generator 解析表结构
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder(config.getUrl(), config.getUsername(),
                config.getPassword());
        if (Objects.equals(dbType, DbType.SQL_SERVER)) { // 特殊：SQLServer jdbc 非标准，参见 https://github.com/baomidou/mybatis-plus/issues/5419
            dataSourceConfigBuilder.databaseQueryClass(SQLQuery.class);
        }
        StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder().enableSkipView(); // 忽略视图，业务上一般用不到
        strategyConfig.addInclude(name);
        GlobalConfig globalConfig = new GlobalConfig.Builder().dateType(DateType.TIME_PACK).build(); // 只使用 LocalDateTime 类型，不使用 LocalDate
        ConfigBuilder builder = new ConfigBuilder(null, dataSourceConfigBuilder.build(), strategyConfig.build(),
                null, globalConfig, null);
        // 按照名字排序
        List<TableInfo> tables = builder.getTableInfoList();
        tables.sort(Comparator.comparing(TableInfo::getName));
        return CollUtil.getFirst(tables);
    }

    @Override
    public void reloadTable(Long tableId) {
        TableDefinitionDO table = tableDefinitionMapper.getAndCheck(tableId);
        TableInfo newTableInfo = this.getTableInfo(table.getDatasourceId(), table.getTableName());
        if (Objects.nonNull(newTableInfo)) {
            // 旧列列表
            List<ColumnDefinitionDO> oldColList = columnDefinitionMapper.selectList(ColumnDefinitionDO::getTableId,tableId);
            Map<String,ColumnDefinitionDO> oldColMap = new HashMap<>();
            if (CollUtil.isNotEmpty(oldColList)) {
                oldColMap = oldColList.stream().collect(Collectors.toMap(s-> s.getColumnName().toUpperCase(), tf -> tf));
            }
            // 新列列表
            List<TableField> newColList = newTableInfo.getFields();
            Map<String,TableField> newColMap = newTableInfo.getFields().stream().collect(Collectors.toMap(s-> s.getColumnName().toUpperCase(), tf -> tf));
            // 删除字段（old列list有而new列list无）
            List<ColumnDefinitionDO> deleteList = new ArrayList<>();
            for (ColumnDefinitionDO columnDefinitionDO : oldColList) {
                if (!newColMap.containsKey(columnDefinitionDO.getColumnName().toUpperCase())){
                    deleteList.add(columnDefinitionDO);
                }
            }
            // 新增字段（new列list有，而old列list无）
            List<ColumnDefinitionDO> insertList = new ArrayList<>();
            List<ColumnDefinitionDO> allList = new ArrayList<>();
            int i = 0;
            for (TableField newCol : newColList) {
                // 新增字段
                if (!oldColMap.containsKey(newCol.getColumnName().toUpperCase())){
                    ColumnDefinitionDO insert = new ColumnDefinitionDO();
                    insert.setId(IdWorker.getId());
                    insert.setTableId(tableId);
                    reloadColumnInfo(newCol, insert);
                    insert.setStatus(true);
                    insert.setSort(i++);
                    insertList.add(insert);
                    allList.add(insert);
                } else {
                    // 更新字段
                    ColumnDefinitionDO update = oldColMap.get(newCol.getColumnName().toUpperCase());
                    reloadColumnInfo(newCol, update);
                    update.setSort(i++);
                    allList.add(update);
                }
            }
            // 先删除后插入（已包含更新）
            if (CollUtil.isNotEmpty(allList)) {
                columnDefinitionMapper.deletePhysicsByTableId(tableId, null);
                columnDefinitionMapper.insertBatch(allList);
            }

            // 刷新模块信息
            refreshModuleInfo(tableId, deleteList, insertList);
            refreshModuleInfoByTable(tableId);
        }
    }

    private void refreshModuleInfo(Long tableId, List<ColumnDefinitionDO> deleteList, List<ColumnDefinitionDO> insertList) {
        if (deleteList.isEmpty() && insertList.isEmpty()) {
            return;
        }
        // 如果deleteList不为空，则删除模型表cfg_module_field字段
        if (CollUtil.isNotEmpty(deleteList)) {
            // 根据column_id删除模型表cfg_module_field数据
            List<Long> columnIds = deleteList.stream().map(ColumnDefinitionDO::getId).collect(
                    Collectors.toList());
            moduleFieldMapper.deleteByColumnIds(columnIds);
        }
        // 根据tableId 查询module_id、module_table_id，生成ModuleFieldDO
        List<ModuleTableDO> moduleTableDOList = moduleTableMapper.selectList(ModuleTableDO::getTableId, tableId);
        // 如果insertList不为空，则插入模型表cfg_module_field字段
        if (CollUtil.isNotEmpty(insertList)) {
            List<ModuleFieldDO> moduleFieldDOList =
                    getModuleFieldDOList(insertList, moduleTableDOList);
            moduleFieldMapper.insertBatch(moduleFieldDOList);
        }

        // 更新模型SQL表cfg_module_sql
        List<Long> moduleIds =
                moduleTableDOList.stream().map(ModuleTableDO::getModuleId).distinct().collect(Collectors.toList());
        for (Long moduleId : moduleIds) {
            ModuleInfoRespVO moduleInfo = moduleInfoService.getModuleInfo(moduleId);
            String moduleInfoJson = JsonUtils.toJsonString(moduleInfo);
            ModuleInfoSaveReqVO updateReqVo = JsonUtils.parseObject(moduleInfoJson, ModuleInfoSaveReqVO.class);
            moduleInfoService.updateModuleInfo(updateReqVo);
        }
    }

    private static List<ModuleFieldDO> getModuleFieldDOList(List<ColumnDefinitionDO> insertList,
                                                         List<ModuleTableDO> moduleTableDOList) {
        List<ModuleFieldDO> moduleFieldDOList = new ArrayList<>();
        for (ColumnDefinitionDO columnDefinitionDO : insertList) {
            for (ModuleTableDO moduleTableDO : moduleTableDOList) {
                ModuleFieldDO moduleFieldDO = new ModuleFieldDO();
                moduleFieldDO.setId(IdWorker.getId());
                moduleFieldDO.setModuleId(moduleTableDO.getModuleId());
                moduleFieldDO.setModuleTableId(moduleTableDO.getId());
                moduleFieldDO.setColumnId(columnDefinitionDO.getId());
                moduleFieldDO.setColumnComment(columnDefinitionDO.getColumnComment());
                moduleFieldDOList.add(moduleFieldDO);
            }
        }
        return moduleFieldDOList;
    }

    private static void reloadColumnInfo(TableField newCol, ColumnDefinitionDO insert) {
        insert.setColumnName(newCol.getColumnName());
        insert.setColumnComment(newCol.getComment());
        insert.setJavaType(newCol.getColumnType().getType());
        insert.setIsPrimaryKey(newCol.isKeyFlag());
        if (Objects.nonNull(newCol.getMetaInfo())) {
            insert.setColumnType(newCol.getMetaInfo().getTypeName());
            insert.setIsNotNull(!newCol.getMetaInfo().isNullable());
            insert.setDefaultValue(newCol.getMetaInfo().getDefaultValue());
            insert.setColumnLength(newCol.getMetaInfo().getLength());
            insert.setColumnScale(newCol.getMetaInfo().getScale());
            if (Objects.nonNull(newCol.getMetaInfo().getJdbcType())) {
                insert.setJdbcType(newCol.getMetaInfo().getJdbcType().name());
            }
        }
    }

    @Override
    public Long createColumnDefinition(ColumnDefinitionSaveReqVO createReqVO) {
        // 插入
        ColumnDefinitionDO columnDefinition = BeanUtils.toBean(createReqVO, ColumnDefinitionDO.class);
        columnDefinitionMapper.insert(columnDefinition);
        // 返回
        return columnDefinition.getId();
    }


    @Override
    public void updateColumnDefinition(ColumnDefinitionSaveReqVO updateReqVO) {
        // 校验存在
        validateColumnDefinitionExists(updateReqVO.getId());
        // 更新
        ColumnDefinitionDO updateObj = BeanUtils.toBean(updateReqVO, ColumnDefinitionDO.class);
        columnDefinitionMapper.updateById(updateObj);
    }

    @Override
    public void deleteColumnDefinition(Long id) {
        // 校验存在
        validateColumnDefinitionExists(id);
        // 删除
        columnDefinitionMapper.deleteById(id);
    }

    private void validateColumnDefinitionExists(Long id) {
        if (columnDefinitionMapper.selectById(id) == null) {
            throw exception(COLUMN_DEFINITION_NOT_EXISTS);
        }
    }

    @Override
    public ColumnDefinitionDO getColumnDefinition(Long id) {
        ColumnDefinitionDO columnDefinitionDO = columnDefinitionMapper.selectById(id);
        if (columnDefinitionDO == null) {
            throw exception(COLUMN_DEFINITION_NOT_EXISTS);
        }
        return columnDefinitionDO;
    }

    @Override
    public PageResult<ColumnDefinitionDO> getColumnDefinitionPage(ColumnDefinitionPageReqVO pageReqVO) {
        return columnDefinitionMapper.selectPage(pageReqVO);
    }

    public List<ComponentAttributeSaveReqVO> getAttrList(Set<Long> ids) {
        return attributeMapper.selectComponentAttributeList(ids);
    }

}
