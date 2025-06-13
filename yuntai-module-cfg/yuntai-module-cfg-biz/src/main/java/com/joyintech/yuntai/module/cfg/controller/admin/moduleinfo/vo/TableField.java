package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Objects;
import java.util.Set;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/18
 */
@Data
public class TableField {
    /**
     * 表cfg_module_field id
     */
    private Long id;
    /**
     * 模型id
     */
    private Long moduleId;
    /**
     * 表id
     */
    private Long tableId;

    /**
     * 模块表id
     */
    private Long moduleTableId;

    /**
     * 字段id
     */
    private Long fieldId;

    /**
     * 表名
     */
    private String tableName;
    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段别名
     */
    private String fieldAliasName;

    /**
     * 系统字段别名
     */
    private String sysAliasName;

    /**
     * 映射字段名称
     */
    private String mappingFieldName;

    /**
     * 映射字段id
     */
    private Long mappingFieldId;

    /**
     * 映射表id
     */
    private Long mappingMouduleTableId;

    /**
     * 字段注释
     */
    private String fieldComment;

    /**
     * 字段值
     */
    private String fieldValue;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * jdbcType
     */
    private String jdbcType;

    /**
     * 是否主键
     */
    private Boolean isPk;

    /**
     * 是否系统字段
     */
    private Boolean isSys;

    /**
     * 计算字段sql
     */
    private String computeSql;

    /**
     * 回显表
     */
    private String textTable;
    /**
     * 回显字段
     */
    private String textColumn;
    /**
     * 回显表主键字段
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

    /**
     * 获取拼接后的value #
     * @return
     */
    public String getFieldValueInModuleTableIdWithWell() {
        return String.format("#{%s}",this.getFieldValue());
    }


    /**
     * 获取拼接后的value
     * @return
     */
    public String getFieldValueUnderline() {
        return String.format("%s_%d",this.getFieldValue(),this.getModuleTableId());
    }

    /**
     * 获取拼接后的value
     * @return
     */
    public String getMappingFieldValueUnderline() {
        return String.format("%s_%d",this.getMappingFieldName(),this.getMappingMouduleTableId());
    }

    /**
     * 获取拼接后的value #
     * @return
     */
    public String getFieldValueWithWell(Boolean withJdbcType) {
        if (Objects.isNull(this.getModuleTableId())) {
            return String.format("#{%s %s}",this.getFieldValue(),this.getJdbcType(this,withJdbcType));
        }else {
            return String.format("#{%s_%d %s}",this.getFieldValue(),this.getModuleTableId(),this.getJdbcType(this,withJdbcType));
        }
    }

    /**
     * 统一处理获取字段的jdbcType
     * @param tableField
     * @return
     */
    public String getJdbcType(TableField tableField,Boolean withJdbcType) {
        if (!withJdbcType) return "";
        Set<String> dateType = Sets.newHashSet(JdbcType.DATE.name(), JdbcType.TIMESTAMP.name(),JdbcType.TIME.name());
        if (StrUtil.isNotEmpty(tableField.getJdbcType())) {
            if (dateType.contains(tableField.getJdbcType()) && !tableField.isSys) {
                return String.format(",typeHandler=%s","com.joyintech.yuntai.framework.mybatis.core.type.DateTypeHandler");
            }
            return String.format(",jdbcType=%s",tableField.getJdbcType());
        }
        return "";
    }

    /**
     * 获取拼接后的value $
     * @return
     */
    public String getFieldValueWith$() {
        if (Objects.isNull(this.getModuleTableId())) {
            return String.format("${%s}",this.getFieldValue());
        }else {
            return String.format("${%s_%d}",this.getFieldValue(),this.getModuleTableId());
        }
    }

    /**
     * 构建字段
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public static TableField newField(String fieldName, String fieldValue,Long moduleTableId) {
        TableField tableField = new TableField();
        tableField.setFieldName(fieldName);
        tableField.setFieldValue(fieldValue);
        tableField.setModuleTableId(moduleTableId);
        return tableField;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableField field = (TableField) o;
        return Objects.equals(tableId, field.tableId) && Objects.equals(moduleTableId, field.moduleTableId)
                && Objects.equals(fieldId, field.fieldId) && Objects.equals(fieldName, field.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableId, moduleTableId,fieldId, fieldName);
    }
}
