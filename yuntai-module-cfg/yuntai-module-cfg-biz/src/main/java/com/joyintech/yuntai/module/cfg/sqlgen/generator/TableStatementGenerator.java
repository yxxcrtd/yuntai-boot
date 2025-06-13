package com.joyintech.yuntai.module.cfg.sqlgen.generator;

import cn.hutool.core.collection.CollUtil;
import com.joyintech.yuntai.module.cfg.sqlgen.enums.ColTypeConstant;
import com.joyintech.yuntai.module.cfg.sqlgen.enums.DbColType;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Column;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Index;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.COLUMN_DEFINITION_TYPE_ERROR;

/**
 * 建表语句生成器
 *
 * @author abator 2024/9/24
 */
public interface TableStatementGenerator {

    /**
     * 获取数据库类型
     * @return
     */
    DbColType getDbColType();

    /**
     * 校验列类型
     * @param columns 列
     */
    default void validate(List<Column> columns) {
        List<String> errors = new ArrayList<>();
        for (Column column : columns) {
            DbColType colType = getDbColType().get(column.getColumnType());
            if (Objects.nonNull(colType)) {
                if (colType.getCode() == ColTypeConstant.NOT_LEGNTH){
                    if (Objects.nonNull(column.getColumnLength()) || Objects.nonNull(column.getColumnScale())){
                        errors.add(String.format("%s字段类型为%s,长度和精度应为空", column.getColumnName(), colType));
                    }
                }else if (colType.getCode() == ColTypeConstant.LEGNTH){
                    if (Objects.isNull(column.getColumnLength()) || Objects.nonNull(column.getColumnScale())){
                        errors.add(String.format("%s字段类型为%s,长度不能为空,精度应为空", column.getColumnName(), colType));
                    }
                }else if (colType.getCode() == ColTypeConstant.LEGNTH_SCALE){
                    if (Objects.isNull(column.getColumnLength()) || Objects.isNull(column.getColumnScale())){
                        errors.add(String.format("%s字段类型为%s,长度不能为空,精度不能为空", column.getColumnName(), colType));
                    }
                }else if (colType.getCode() == ColTypeConstant.LEGNTH_OR_SCALE){
                    if (Objects.isNull(column.getColumnLength())){
                        errors.add(String.format("%s字段类型为%s,长度不能为空", column.getColumnName(), colType));
                    }
                }
            }
        }
        if (CollUtil.isNotEmpty(errors)) {
            throw exception(COLUMN_DEFINITION_TYPE_ERROR.getCode(),CollUtil.join(errors,"\r\n"));
        }
    }



    /**
     * 生成建表语句（包含comment）
     *
     * @param table 表定义信息
     * @return 建表语句（包含comment）-考虑如oracle的表注释会有多条语句
     */
    List<String> generateCreateTableStatement(Table table);

    /**
     * 生成新增字段语句
     * @param tableName
     * @param columns
     * @return
     */
    List<String> generateAddColumnStatement(String tableName,List<Column> columns);


    /**
     * 生成新增字段语句
     * @param column
     * @return
     */
    default String processAddColumn(Column column) {
        StringBuilder addColumn = new StringBuilder();
        addColumn.append(column.getColumnName()).append(" ").append(column.getColumnType());
        if (column.getColumnLength() != null) {
            addColumn.append("(").append(column.getColumnLength());
            if (column.getColumnScale() != null) {
                addColumn.append(",").append(column.getColumnScale());
            }
            addColumn.append(")");
        }
        if (column.getIsNotNull() != null && column.getIsNotNull()) {
            addColumn.append(" NOT NULL");
        }
        if (column.getDefaultValue() != null && !column.getDefaultValue().isEmpty()) {
            addColumn.append(" DEFAULT ").append(column.getDefaultValue());
        }
        return addColumn.toString();
    }


    /**
     * 生成建索引语句
     *
     * @param index 索引定义信息
     * @return 建索引语句
     */
    default String generateCreateIndexStatement(Index index) {
        String sql = "CREATE ";
        if (index.getIsUniqueKey()) {
            sql += "UNIQUE ";
        }
        sql += "INDEX " + index.getIndexName() + " ON " + index.getTableName() + " (" + index.getIndexColumns() + ")";
        return sql;
    }

    /**
     * 生成删除索引语句
     *
     * @param index 索引定义信息
     * @return 删除索引语句
     */
    default String generateDropIndexStatement(Index index) {
        return "DROP INDEX " + index.getIndexName() + " ON " + index.getTableName();
    }

    default void processCreateTableCommonInfo(StringBuilder createTableSql, List<String> primaryKeys, Column column) {
        if (column.getIsPrimaryKey() != null && column.getIsPrimaryKey()) {
            primaryKeys.add(column.getColumnName());
        }

        createTableSql.append(column.getColumnName()).append(" ").append(column.getColumnType());
        if (column.getColumnLength() != null) {
            createTableSql.append("(").append(column.getColumnLength());
            if (column.getColumnScale() != null) {
                createTableSql.append(",").append(column.getColumnScale());
            }
            createTableSql.append(")");
        }
        if (column.getIsNotNull() != null && column.getIsNotNull()) {
            createTableSql.append(" NOT NULL");
        }
        if (column.getDefaultValue() != null && !column.getDefaultValue().isEmpty()) {
            createTableSql.append(" DEFAULT ").append(column.getDefaultValue());
        }
    }

    default void appendPrimaryKey(StringBuilder createTableSql, List<String> primaryKeys) {
        createTableSql.deleteCharAt(createTableSql.length() - 1);
        if (!primaryKeys.isEmpty()) {
            createTableSql.append(",PRIMARY KEY (");
            for (String primaryKey : primaryKeys) {
                createTableSql.append(primaryKey).append(",");
            }
            createTableSql.deleteCharAt(createTableSql.length() - 1);
            createTableSql.append(")");
        }
    }
}
