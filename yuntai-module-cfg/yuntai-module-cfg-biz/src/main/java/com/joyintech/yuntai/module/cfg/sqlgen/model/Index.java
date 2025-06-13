package com.joyintech.yuntai.module.cfg.sqlgen.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库索引模型
 *
 * @author abator 2024/9/25
 */
@Data
@NoArgsConstructor
public class Index {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 索引列
     */
    private String indexColumns;

    /**
     * 是否唯一索引
     */
    private Boolean isUniqueKey;
}
