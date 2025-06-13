package com.joyintech.yuntai.module.cfg.dal.dataobject.modulefield;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 模型涉及字段 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_module_field")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleFieldDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 模型id
     */
    private Long moduleId;
    /**
     * 字段别名
     */
    private String columnAliasName;
    /**
     * 系统字段别名
     */
    private String sysAliasName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否只读字段,默认否
     */
    private Boolean readonly;
    /**
     * 表id
     */
    private Long moduleTableId;
    /**
     * 字段id
     */
    private Long columnId;

    /**
     * 模型映射字段id,对应这个表里的id
     */
    private Long mappingFieldId;

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段备注
     */
    private String columnComment;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * 字段长度
     */
    private Integer columnLength;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 计算字段sql
     */
    private String computeSql;

    /**
     *刷新模型
     */
    @TableField(exist = false)
    private String refreshType;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 回显表
     */
    private String textTableId;

    /**
     * 回显字段
     */
    private String textColumnId;

    /**
     * 回显表外键
     */
    private String textTableKey;

    /**
     * 单选0 多选 1
     */
    private Integer valueType;

    /**
     * 回显sql语句
     */
    private String textSql;

    /**
     * 是否回推
     */
    private Boolean isBackData;
}
