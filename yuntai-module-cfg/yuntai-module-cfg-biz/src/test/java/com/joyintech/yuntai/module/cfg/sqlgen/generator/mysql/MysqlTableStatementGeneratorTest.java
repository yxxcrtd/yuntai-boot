
package com.joyintech.yuntai.module.cfg.sqlgen.generator.mysql;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.joyintech.yuntai.module.cfg.sqlgen.model.Column;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Table;

@RunWith(MockitoJUnitRunner.class)
public class MysqlTableStatementGeneratorTest {

    @InjectMocks
    private MysqlTableStatementGenerator generator;

    private Table table;

    @Before
    public void setUp() {
        table = new Table();
        table.setTableName("test_table");
        table.setTableComment("测试ddl用表");

        List<Column> columns = new ArrayList<>();
        Column column1 = new Column();
        column1.setColumnName("id");
        column1.setColumnType("bigint");
        column1.setIsPrimaryKey(true);
        column1.setIsNotNull(true);
        column1.setDefaultValue(null);
        column1.setIsAutoIncrement(true);
        column1.setColumnComment("主键");
        columns.add(column1);

        Column column2 = new Column();
        column2.setColumnName("name");
        column2.setColumnType("varchar");
        column2.setColumnLength(100);
        column2.setIsNotNull(true);
        column2.setColumnComment("名称");
        columns.add(column2);

        Column column3 = new Column();
        column3.setColumnName("price");
        column3.setColumnType("decimal");
        column3.setColumnLength(10);
        column3.setColumnScale(2);
        column3.setDefaultValue("0.00");
        column3.setColumnComment("价格");
        columns.add(column3);

        table.setColumns(columns);
    }

    @Test
    public void generateCreateTableStatement_ValidInput_CorrectSQL() {
        List<String> sqlList = generator.generateCreateTableStatement(table);
        String expectedSql = "CREATE TABLE test_table (id bigint NOT NULL AUTO_INCREMENT COMMENT '主键',"
                + "name varchar(100) NOT NULL COMMENT '名称',price decimal(10,2) DEFAULT 0.00 COMMENT '价格',"
                + "PRIMARY KEY (id)) COMMENT '测试ddl用表'";
        assertEquals(expectedSql, sqlList.get(0));
    }
}
