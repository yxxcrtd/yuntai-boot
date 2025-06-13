package com.joyintech.yuntai.module.cfg.sqlgen.model;

import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库表字段模型
 *
 * @author abator 2024/9/25
 */
@Data
@NoArgsConstructor
public class Column {

    private String columnName;

    private String columnComment;

    /**
     * jdbc type 定义
     */
    private JdbcType jdbcType;

    /**
     * 数据库类型
     */
    private String columnType;

    /**
     * 长度
     */
    private Integer columnLength;

    /**
     * 小数位数
     */
    private Integer columnScale;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否主键
     */
    private Boolean isPrimaryKey;

    /**
     * 非空
     */
    private Boolean isNotNull;

    /**
     * 自增
     */
    private Boolean isAutoIncrement;
}
