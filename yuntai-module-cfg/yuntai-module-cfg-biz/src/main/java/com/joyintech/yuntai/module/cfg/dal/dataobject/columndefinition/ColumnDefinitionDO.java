package com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 字段定义 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_column_definition")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDefinitionDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
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
     * org.apache.ibatis.type.JdbcType
     */
    private String jdbcType;
    /**
     * 长度
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Integer columnLength;
    /**
     * 小数位数
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Integer columnScale;
    /**
     * 默认值
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String defaultValue;
    /**
     * 是否主键
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Boolean isPrimaryKey;
    /**
     * 非空
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
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
     * 组件主键
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String componentCode;

    /**
     * 是否生效,true:生效,false:不生效
     */
    private Boolean status;

    /**
     * 排序
     */
    private Integer sort;

}
