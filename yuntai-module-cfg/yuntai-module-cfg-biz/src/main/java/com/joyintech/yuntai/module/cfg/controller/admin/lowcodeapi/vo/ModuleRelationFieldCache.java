package com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo;

import lombok.Data;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/11
 */
@Data
public class ModuleRelationFieldCache {
    /**
     * 模型id
     */
    private Long moduleId;
    /**
     * 关联cfg_module_table表的id
     */
    private Long moduleTableId;
    /**
     * 表别名
     */
    private String moduleTableAlias;
    /**
     * 关联cfg_module_table表id
     */
    private Long relationMoudleTableId;
    /**
     * 关联表别名
     */
    private String relationMoudleAlias;
    /**
     * 记录是子表字段关联主表还是主表字段关联子表,
     * 0-子表字段关联主表,1-主表字段关联子表
     */
    private Integer relationDirection;
    /**
     * 表关联字段
     */
    private Long fieldId;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 关联表字段
     */
    private Long relationFieldId;
    /**
     * 关联表字段名称
     */
    private String relationFieldName;

}
