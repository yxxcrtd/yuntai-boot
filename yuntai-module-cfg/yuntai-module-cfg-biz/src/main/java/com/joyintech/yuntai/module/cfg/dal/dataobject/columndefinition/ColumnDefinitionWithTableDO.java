package com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 字段定义 DO
 *
 * @author 兆尹云台
 */
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDefinitionWithTableDO {

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
    private String dataType;
    /**
     * Java类型
     */
    private String javaType;
    /**
     * 表名
     */
    private String tableName;

    /**
     * 数据源id
     */
    private Long datasourceId;

}
