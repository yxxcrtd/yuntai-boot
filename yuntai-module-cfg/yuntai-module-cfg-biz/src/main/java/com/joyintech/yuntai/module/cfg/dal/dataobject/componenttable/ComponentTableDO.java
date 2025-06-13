package com.joyintech.yuntai.module.cfg.dal.dataobject.componenttable;

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
 * 组件 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_component_table")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentTableDO extends BaseDO {

    /**
     * 主键编号
     */
    @TableId
    private Long id;
    /**
     * 组件分组表ID
     */
    private Long groupId;
    /**
     * 组件分组表名称
     */
    @TableField(exist = false)
    private String groupName;
    /**
     * 组件名称
     */
    private String componentName;
    /**
     * 组件编码
     */
    private String componentCode;
    /**
     * 状态（0->开启； 1->停用）
     */
    private Integer status;

}