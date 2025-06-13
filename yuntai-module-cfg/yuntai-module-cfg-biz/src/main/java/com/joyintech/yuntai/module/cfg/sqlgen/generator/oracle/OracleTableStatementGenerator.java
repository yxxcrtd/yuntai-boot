package com.joyintech.yuntai.module.cfg.sqlgen.generator.oracle;

import com.joyintech.yuntai.module.cfg.sqlgen.enums.DbColType;
import com.joyintech.yuntai.module.cfg.sqlgen.enums.OracleColType;
import com.joyintech.yuntai.module.cfg.sqlgen.generator.TableStatementGenerator;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Column;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Index;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * 建表语句生成器
 *
 * @author abator 2024/9/24
 */
public class OracleTableStatementGenerator implements TableStatementGenerator {

    @Override
    public DbColType getDbColType() {
        return OracleColType.INSTANCE;
    }

    @Override
    public List<String> generateCreateTableStatement(Table table) {
        List<String> result = new ArrayList<>();
        StringBuilder createTableSql = new StringBuilder();
        createTableSql.append("CREATE TABLE ").append(table.getTableName()).append(" (");
        List<String> primaryKeys = new ArrayList<>();
        List<String> commentSqlList = new ArrayList<>();
        // 添加表注释
        commentSqlList.add("COMMENT ON TABLE " + table.getTableName() + " IS '" + table.getTableComment() + "'");
        String sequence = null;
        for (Column column : table.getColumns()) {
            processCreateTableCommonInfo(createTableSql, primaryKeys, column);
            // 列信息结尾
            createTableSql.append(",");

            // 添加自增列--序列名规则参考代码自动生成模板 DO.vm
            if (column.getIsAutoIncrement() != null && column.getIsAutoIncrement()) {
                sequence = "CREATE SEQUENCE " + table.getTableName().toLowerCase()
                        + "_seq minvalue 1 maxvalue 9999999999999999999 start with 1 increment by 1 cache 10";
            }
            // 添加列注释
            commentSqlList.add("COMMENT ON COLUMN " + table.getTableName() + "." + column.getColumnName() + " IS '"
                                       + column.getColumnComment() + "'");
        }
        // 去除 最后一个逗号
        appendPrimaryKey(createTableSql, primaryKeys);

        createTableSql.append(")");
        // create table
        result.add(createTableSql.toString());
        // 添加列注释
        result.addAll(commentSqlList);
        // 添加自增列
        if (sequence != null) {
            result.add(sequence);
        }
        return result;
    }

    @Override
    public String generateDropIndexStatement(Index index) {
      return "DROP INDEX " + index.getIndexName();
    }


    @Override
    public List<String> generateAddColumnStatement(String tableName,List<Column> columns) {
        List<String> result = new ArrayList<>();
        String addColumnSql = "alter table %s add %s ";
        String addColumnCommentSql = "COMMENT ON COLUMN %s.%s IS '%s'";
        for (Column column : columns) {
            result.add(String.format(addColumnSql, tableName, processAddColumn(column)));
            result.add(String.format(addColumnCommentSql, tableName, column.getColumnName(), column.getColumnComment()));
        }
        return result;
    }
}
