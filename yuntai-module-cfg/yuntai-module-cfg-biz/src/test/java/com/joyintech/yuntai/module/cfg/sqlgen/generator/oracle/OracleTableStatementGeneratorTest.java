package com.joyintech.yuntai.module.cfg.sqlgen.generator.oracle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public class OracleTableStatementGeneratorTest {

    @InjectMocks
    private OracleTableStatementGenerator generator;

    private Table table;

    @Before
    public void setUp() {
        table = new Table();
        table.setTableName("AA_TEST_TABLE");
        table.setTableComment("测试表注释");

        Column column1 = new Column();
        column1.setColumnName("ID");
        column1.setColumnType("NUMBER");
        column1.setColumnLength(10);
        column1.setIsPrimaryKey(true);
        column1.setIsNotNull(true);
        column1.setIsAutoIncrement(true);
        column1.setColumnComment("Primary key identifier");

        Column column2 = new Column();
        column2.setColumnName("NAME");
        column2.setColumnType("VARCHAR2");
        column2.setColumnLength(50);
        column2.setIsNotNull(false);
        column2.setDefaultValue("'N/A'");
        column2.setColumnComment("Name of the entity");
        List<Column> columns = new ArrayList<>();
        columns.add(column1);
        columns.add(column2);
        table.setColumns(columns);
    }

    @Test
    public void generateCreateTableStatement_ValidTable_ExpectedSQLStatements() {
        List<String> sqlStatements = generator.generateCreateTableStatement(table);

        assertEquals(5, sqlStatements.size());
        assertEquals(
                "CREATE TABLE AA_TEST_TABLE (ID NUMBER(10) NOT NULL,NAME VARCHAR2(50) DEFAULT 'N/A',PRIMARY KEY (ID))",
                sqlStatements.get(0));
        assertEquals("COMMENT ON TABLE AA_TEST_TABLE IS '测试表注释'", sqlStatements.get(1));
        assertEquals("COMMENT ON COLUMN AA_TEST_TABLE.ID IS 'Primary key identifier'", sqlStatements.get(2));
        assertEquals("COMMENT ON COLUMN AA_TEST_TABLE.NAME IS 'Name of the entity'", sqlStatements.get(3));
        assertTrue(sqlStatements.contains(
                "CREATE SEQUENCE aa_test_table_seq minvalue 1 maxvalue 9999999999999999999 start with 1 increment by 1 "
                        + "cache 10"));
    }
}
