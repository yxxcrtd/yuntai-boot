package com.joyintech.yuntai.module.cfg.sqlgen.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库表模型
 *
 * @author abator 2024/9/25
 */
@Data
@NoArgsConstructor
public class Table {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表注释
     */
    private String tableComment;

    private String tableType;

    private List<Column> columns;

    private List<Index> indices;

}
