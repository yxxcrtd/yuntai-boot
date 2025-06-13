package com.joyintech.yuntai.module.cfg.dal.dataobject.dbsystemcolumn;

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
 * 数据库系统字段 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_db_system_column")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbSystemColumnDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 字段注释
     */
    private String columnComment;
    /**
     * 位置
     */
    private Integer columnPosition;
    /**
     * 数据域主键
     */
    private Long dataDomainId;
    /**
     * 数据库类型（MySQL）
     */
    private String typeSource;

    /**
     * 数据类型
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
    /**
     * 备注
     */
    private String remark;
    /**
     * 字段分类:0-新增,1-删除2-更新，3-主键
     */
    private String category;
    /**
     * 是否是逻辑删除字段
     */
    private String deletedField;
    /**
     * 删除的值
     */
    private String deletedValue;
    /**
     * 未删除的值
     */
    private String notDeletedValue;

    @TableField(exist = false)
    private Long dataSourceId;

}
