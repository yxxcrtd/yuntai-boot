/*
 * Copyright (c) 2011-2024, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joyintech.yuntai.module.cfg.sqlgen.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joyintech.yuntai.module.cfg.sqlgen.model.Column;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Index;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Table;

import lombok.Getter;

/**
 * 数据库数据元包装类
 * 参考 mybatis-plus-generator 实现
 *
 * @author abator 2024/09/05
 */
public class DefaultDatabaseMetaDataWrapper {

    private static final String EMPTY = "";

    private static final Logger logger = LoggerFactory.getLogger(DefaultDatabaseMetaDataWrapper.class);

    @Getter
    private final Connection connection;

    private final DatabaseMetaData databaseMetaData;

    // 暂时只支持一种
    private final String catalog;

    // 暂时只支持一种
    private final String schema;

    public DefaultDatabaseMetaDataWrapper(Connection connection, String schemaName) {
        try {
            if (null == connection) {
                throw new RuntimeException("数据库连接不能为空");
            }
            this.connection = connection;
            this.databaseMetaData = connection.getMetaData();
            this.catalog = connection.getCatalog();
            this.schema = schemaName;
        } catch (SQLException e) {
            throw new RuntimeException("获取元数据错误:", e);
        }
    }

    public void closeConnection() {
        Optional.ofNullable(connection).ifPresent((con) -> {
            try {
                con.close();
            } catch (SQLException sqlException) {
                logger.warn("connection closed exception", sqlException);
            }
        });
    }

    /**
     * 获取表索引信息
     *
     * @return 表索引信息
     */
    public List<Index> getIndexInfo(String tableName, boolean includePrimaryKey) {
        return getIndexInfo(this.catalog, this.schema, tableName, includePrimaryKey);
    }

    /**
     * 获取表索引信息
     *
     * @return 表索引信息
     */
    private List<Index> getIndexInfo(String catalog, String schema, String tableName, boolean includePrimaryKey) {
        String pkName = null;
        if (!includePrimaryKey) {
            try (ResultSet primaryKeysResultSet = databaseMetaData.getPrimaryKeys(catalog, schema, tableName)) {
                if (primaryKeysResultSet.next()) {
                    pkName = primaryKeysResultSet.getString("PK_NAME");
                }
            } catch (SQLException e) {
                throw new RuntimeException("读取表主键信息:" + tableName + "错误:", e);
            }
        }

        List<Index> indexList = new ArrayList<>();
        // 当前索引名称
        String currentIndexName = null;
        // 索引对象
        Index index = null;
        try (ResultSet resultSet = databaseMetaData.getIndexInfo(catalog, schema, tableName, false, false)) {
            while (resultSet.next()) {
                String type = resultSet.getString("TYPE");
                // 排除索引类型：0: tableIndexStatistic
                if (type == null || type.equals("0")) {
                    continue;
                }
                String name = resultSet.getString("INDEX_NAME");
                // 如果是主键，则不处理
                if (pkName != null && pkName.equals(name)) {
                    continue;
                }
                if (currentIndexName == null || !currentIndexName.equals(name)) {
                    currentIndexName = name;
                    index = new Index();
                    index.setIndexName(currentIndexName);
                    // 是否唯一
                    boolean nonUnique = resultSet.getBoolean("NON_UNIQUE");
                    index.setIsUniqueKey(!nonUnique);
                    indexList.add(index);
                }
                String columnName = resultSet.getString("COLUMN_NAME");
                index.setIndexColumns(joinIndexColumns(index.getIndexColumns(), columnName));
            }
            return indexList;
        } catch (SQLException e) {
            throw new RuntimeException("读取表字段信息:" + tableName + "错误:", e);
        }
    }

    private static String joinIndexColumns(String preColumns, String columnName) {
        if (StringUtils.isEmpty(preColumns)) {
            return columnName + " ASC";
        }
        // 默认都是Asc，因为oracle metadata获取不到 asc_or_desc
        return preColumns + ", " + columnName + " ASC";
    }

    public List<Column> getColumnsInfo(String tableNamePattern, boolean queryPrimaryKey) {
        return getColumnsInfo(this.catalog, this.schema, tableNamePattern, queryPrimaryKey);
    }

    /**
     * 获取表字段信息
     *
     * @return 表字段信息
     */
    public List<Column> getColumnsInfo(String catalog, String schema, String tableName,
                                       boolean queryPrimaryKey) {
        Set<String> primaryKeys = new HashSet<>();
        if (queryPrimaryKey) {
            try (ResultSet primaryKeysResultSet = databaseMetaData.getPrimaryKeys(catalog, schema, tableName)) {
                while (primaryKeysResultSet.next()) {
                    String columnName = primaryKeysResultSet.getString("COLUMN_NAME");
                    primaryKeys.add(columnName);
                }
                if (primaryKeys.size() > 1) {
                    logger.warn("当前表:{}，存在多主键情况！", tableName);
                }
            } catch (SQLException e) {
                throw new RuntimeException("读取表主键信息:" + tableName + "错误:", e);
            }
        }
        List<Column> columnList = new ArrayList<>();
        try (ResultSet resultSet = databaseMetaData.getColumns(catalog, schema, tableName, "%")) {
            while (resultSet.next()) {
                Column column = new Column();
                String name = resultSet.getString("COLUMN_NAME");
                column.setColumnName(name);
                column.setIsPrimaryKey(primaryKeys.contains(name));
                // TODO 不同数据库需要针对MySQL类型做转换--待定
                String typeName = resultSet.getString("TYPE_NAME");
                // 兼容性处理，去除类型中的括号，例如：Oracle中返回 TIMESTAMP(6) -> TIMESTAMP
                if (typeName != null && typeName.contains("(")) {
                    typeName = typeName.substring(0, typeName.indexOf("("));
                } else {
                    // 长度默认为空--可能有问题，有时长度会有特殊用意，默认空会消除（或者可以typeName原样返回--下拉类型会过多？）
                    column.setColumnLength(resultSet.getInt("COLUMN_SIZE"));
                }
                column.setColumnType(typeName);
                int dataType = resultSet.getInt("DATA_TYPE");
                JdbcType jdbcType = JdbcType.forCode(dataType);
                if (jdbcType == null) {
                    // 不标准的类型,统一转为OTHER
                    jdbcType = JdbcType.OTHER;
                }
                column.setJdbcType(jdbcType);
                column.setColumnScale(resultSet.getInt("DECIMAL_DIGITS"));
                column.setColumnComment(formatComment(resultSet.getString("REMARKS")));
                column.setIsNotNull(resultSet.getInt("NULLABLE") != DatabaseMetaData.columnNullable);
                column.setDefaultValue(resultSet.getString("COLUMN_DEF"));
                try {
                    column.setIsAutoIncrement("YES".equals(resultSet.getString("IS_AUTOINCREMENT")));
                } catch (SQLException sqlException) {
                    // 目前测试在oracle旧驱动下存在问题，降级成false.
                    column.setIsAutoIncrement(false);
                }
                columnList.add(column);
            }
            return columnList;
        } catch (SQLException e) {
            throw new RuntimeException("读取表字段信息:" + tableName + "错误:", e);
        }
    }

    public String formatComment(String comment) {
        return StringUtils.isBlank(comment) ? EMPTY : comment.replaceAll("\r\n", "\t");
    }

    public List<Table> getTables(String tableNamePattern, String[] types) {
        return getTables(this.catalog, this.schema, tableNamePattern, types);
    }

    public List<Table> getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) {
        List<Table> tables = new ArrayList<>();
        try (ResultSet resultSet = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types)) {
            Table table;
            while (resultSet.next()) {
                table = new Table();
                table.setTableName(resultSet.getString("TABLE_NAME"));
                table.setTableComment(formatComment(resultSet.getString("REMARKS")));
                table.setTableType(resultSet.getString("TABLE_TYPE"));
                tables.add(table);
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取数据库表信息出现错误", e);
        }
        return tables;
    }

}
