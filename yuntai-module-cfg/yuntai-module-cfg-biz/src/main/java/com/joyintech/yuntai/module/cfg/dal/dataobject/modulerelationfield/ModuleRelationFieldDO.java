package com.joyintech.yuntai.module.cfg.dal.dataobject.modulerelationfield;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 模型关联字段 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_module_relation_field")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRelationFieldDO extends BaseDO {

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
     * 关联cfg_module_table表的id
     */
    private Long moduleTableId;
    /**
     * 关联cfg_module_table表id
     */
    private Long relationModuleTableId;
    /**
     * 记录是子表字段关联主表还是主表字段关联子表,0-子表字段关联主表,1-主表字段关联子表
     */
    private Integer relationDirection;
    /**
     * 表关联字段
     */
    private Long fieldId;
    /**
     * 关联表字段
     */
    private Long relationFieldId;

    /**
     * 关联表tableKey
     */
    private String relationTableKey;

    /**
     * 复制数据的id
     */
    private Long oldId;
}
