package com.joyintech.yuntai.module.cfg.columndefinition.dto;

import lombok.Data;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/31
 */
@Data
public class ColumnDefinitionDTO {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 定义表主键
     */
    private Long tableId;
    /**
     * 列名
     */
    private String columnName;
    /**
     * 列注释
     */
    private String columnComment;
    /**
     * 数据域主键
     */
    private Long dataDomainId;
    /**
     * 数据库类型（MySQL）
     */
    private String dbType;
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
    /**
     * 是否系统字段
     */
    private Boolean isSys;

    /**
     * 数据类型
     */
    private String columnType;
    /**
     * Java类型
     */
    private String javaType;

    /**
     * jdbcType
     */
    private String jdbcType;

    /**
     * 组件主键
     */
    private Long componentId;

    /**
     * 是否生效,true:生效,false:不生效
     */
    private Boolean status;

    /**
     * 排序
     */
    private Integer sort;
}
