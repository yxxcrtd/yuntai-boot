package com.joyintech.yuntai.module.cfg.sqlgen.generator.mysql;

import com.joyintech.yuntai.module.cfg.sqlgen.enums.DbColType;
import com.joyintech.yuntai.module.cfg.sqlgen.enums.MySqlColType;
import com.joyintech.yuntai.module.cfg.sqlgen.generator.TableStatementGenerator;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Column;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * 建表语句生成器
 *
 * @author abator 2024/9/24
 */
public class MysqlTableStatementGenerator implements TableStatementGenerator {

    @Override
    public DbColType getDbColType() {
        return MySqlColType.INSTANCE;
    }

    @Override
    public List<String> generateCreateTableStatement(Table table) {
        List<String> result = new ArrayList<>();
        StringBuilder createTableSql = new StringBuilder();
        createTableSql.append("CREATE TABLE ").append(table.getTableName()).append(" (");
        List<String> primaryKeys = new ArrayList<>();
        for (Column column : table.getColumns()) {
            processCreateTableCommonInfo(createTableSql, primaryKeys, column);

            // 自增
            if (column.getIsAutoIncrement() != null && column.getIsAutoIncrement()) {
                createTableSql.append(" AUTO_INCREMENT");
            }

            // 列注释
            createTableSql.append(" COMMENT '").append(column.getColumnComment()).append("'");
            // 列信息结尾
            createTableSql.append(",");
        }
        // 处理主键
        appendPrimaryKey(createTableSql, primaryKeys);

        createTableSql.append(")");
        // 表注释
        createTableSql.append(" COMMENT '").append(table.getTableComment()).append("'");
        result.add(createTableSql.toString());
        return result;
    }

    @Override
    public List<String> generateAddColumnStatement(String tableName,List<Column> columns) {
        List<String> result = new ArrayList<>();
        String addColumnSql = "alter table %s add %s comment '%s'";
        for (Column column : columns) {
            result.add(String.format(addColumnSql, tableName, processAddColumn(column), column.getColumnComment()));
        }
        return result;
    }
}
