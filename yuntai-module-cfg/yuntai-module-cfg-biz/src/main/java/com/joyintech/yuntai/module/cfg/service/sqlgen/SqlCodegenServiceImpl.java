package com.joyintech.yuntai.module.cfg.service.sqlgen;

import static com.joyintech.yuntai.framework.common.exception.enums.GlobalErrorCodeConstants.DUPLICATE_KEY;
import static com.joyintech.yuntai.framework.common.util.collection.CollectionUtils.convertSet;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.joyintech.yuntai.framework.common.exception.ServiceException;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition.ColumnDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.indexdefinition.IndexDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.tabledefinition.TableDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.columndefinition.ColumnDefinitionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.indexdefinition.IndexDefinitionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.tabledefinition.TableDefinitionMapper;
import com.joyintech.yuntai.module.cfg.sqlgen.jdbc.DefaultDatabaseMetaDataWrapper;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Column;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Index;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Table;
import com.joyintech.yuntai.module.cfg.sqlgen.query.DefaultDatabaseQuery;
import com.joyintech.yuntai.module.infra.api.db.DataSourceConfigApi;
import com.joyintech.yuntai.module.infra.api.db.dto.DataSourceConfigRespDTO;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 代码生成 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
public class SqlCodegenServiceImpl implements SqlCodegenService {

    @Resource
    private DataSourceConfigApi dataSourceConfigApi;

    @Resource
    private TableDefinitionMapper tableDefinitionMapper;

    @Resource
    private ColumnDefinitionMapper columnDefinitionMapper;

    @Resource
    private IndexDefinitionMapper indexDefinitionMapper;

    @Override
    public List<Table> getTableList(Long dataSourceConfigId, String nameLike, String commentLike) {
        List<Table> tables = getTableList0(dataSourceConfigId);
        // 移除在 table definition 中，已经存在的
        Set<String> existsTables = convertSet(tableDefinitionMapper.selectListByDataSourceConfigId(dataSourceConfigId),
                                              TableDefinitionDO::getTableName);
        tables.removeIf(table -> existsTables.contains(table.getTableName()));
        return tables.stream()
                .filter(table -> (StrUtil.isEmpty(nameLike) || table.getTableName().contains(nameLike)) && (
                        StrUtil.isEmpty(commentLike) || table.getTableComment().contains(commentLike)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initTableFromDatabase(List<TableDefinitionSaveReqVO> tableReqList) {
        Long dataSourceConfigId = tableReqList.get(0).getDatasourceId();
        List<Table> tables = BeanUtils.toBean(tableReqList, Table.class);

        fillTableInfo(dataSourceConfigId, tables);

        // 数据域db类型转数据域id
        for (Table table : tables) {
            TableDefinitionDO tableDefinitionDO = BeanUtils.toBean(table, TableDefinitionDO.class);
            // 设置表数据源
            tableDefinitionDO.setDatasourceId(dataSourceConfigId);
            // 插入到 table definition 中
            try {
                tableDefinitionMapper.insert(tableDefinitionDO);
            } catch (DuplicateKeyException e) {
                throw new ServiceException(DUPLICATE_KEY.getCode(), "数据源下已经存在表名 " + tableDefinitionDO.getTableName());
            }

            // 列
            List<ColumnDefinitionDO> columnDefinitionDOs =
                    BeanUtils.toBean(table.getColumns(), ColumnDefinitionDO.class);
            columnDefinitionDOs.forEach(columnDefinitionDO -> columnDefinitionDO.setTableId(tableDefinitionDO.getId()));
            // 插入到 column definition 中
            columnDefinitionMapper.insertBatch(columnDefinitionDOs);
            // 插入到 index definition 中
            List<IndexDefinitionDO> indexDefinitionDOS = BeanUtils.toBean(table.getIndices(), IndexDefinitionDO.class);
            indexDefinitionDOS.forEach(indexDefinitionDO -> indexDefinitionDO.setTableId(tableDefinitionDO.getId()));
            indexDefinitionMapper.insertBatch(indexDefinitionDOS);
        }
    }

    /**
     * 填充表的详细信息-列信息、索引信息
     *
     * @param dataSourceConfigId 数据源配置的编号
     * @param tables 表列表
     */
    private void fillTableInfo(Long dataSourceConfigId, List<Table> tables) {
        DataSourceConfig.Builder dataSourceConfigBuilder = getDataSourceConfigBuilder(dataSourceConfigId);
        DataSourceConfig dataSourceConfig = dataSourceConfigBuilder.build();
        // 目前实现，每次都会获取一个新的连接，需要关闭连接
        DefaultDatabaseMetaDataWrapper metaDataWrapper =
                new DefaultDatabaseMetaDataWrapper(dataSourceConfig.getConn(), dataSourceConfig.getSchemaName());
        try {
            for (Table table : tables) {
                List<Column> columnsInfo = metaDataWrapper.getColumnsInfo(table.getTableName(), true);
                table.setColumns(columnsInfo);
                List<Index> indexInfo = metaDataWrapper.getIndexInfo(table.getTableName(), false);
                table.setIndices(indexInfo);
            }
        } finally {
            metaDataWrapper.closeConnection();
        }
    }

    private List<Table> getTableList0(Long dataSourceConfigId) {
        DataSourceConfig.Builder dataSourceConfigBuilder = getDataSourceConfigBuilder(dataSourceConfigId);

        // 忽略视图，业务上一般用不到
        StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder().enableSkipView();
        // 移除工作流和定时任务前缀的表名
        strategyConfig.addExclude("system_[\\S\\s]+|yudao_[\\S\\s]+|ACT_[\\S\\s]+|QRTZ_[\\S\\s]+|FLW_[\\S\\s]+");
        // 移除 ORACLE 相关的系统表
        strategyConfig.addExclude("IMPDP_[\\S\\s]+|ALL_[\\S\\s]+|HS_[\\S\\\\s]+");
        // 表里不能有 $，一般有都是系统的表
        strategyConfig.addExclude("[\\S\\s]+\\$[\\S\\s]+|[\\S\\s]+\\$");

        // 只使用 LocalDateTime 类型，不使用 LocalDate
        GlobalConfig globalConfig = new GlobalConfig.Builder().dateType(DateType.TIME_PACK).build();
        ConfigBuilder builder = new ConfigBuilder(null,
                                                  dataSourceConfigBuilder.build(),
                                                  strategyConfig.build(),
                                                  null,
                                                  globalConfig,
                                                  null);
        DefaultDatabaseQuery query = new DefaultDatabaseQuery(builder);
        List<Table> tableList = query.queryTables();
        // 按照名字排序
        tableList.sort(Comparator.comparing(Table::getTableName));
        return tableList;
    }

    @NotNull
    private DataSourceConfig.Builder getDataSourceConfigBuilder(Long dataSourceConfigId) {
        // 获得数据源配置
        DataSourceConfigRespDTO config = dataSourceConfigApi.getDataSourceConfig(dataSourceConfigId);
        Assert.notNull(config, "数据源({}) 不存在！", dataSourceConfigId);

        // 使用 MyBatis Plus Generator 解析表结构
        return new DataSourceConfig.Builder(config.getUrl(), config.getUsername(), config.getPassword());
    }
}
