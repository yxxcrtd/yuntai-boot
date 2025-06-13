package com.joyintech.yuntai.module.cfg.dal.dataobject.componentattribute;

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
 * 组件属性 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_component_attribute")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentAttributeDO extends BaseDO {

    /**
     * 主键编号
     */
    @TableId
    private Long id;

    /**
     * 组件表ID
     */
    private Long componentId;

    /**
     * 属性名称
     */
    private String attributeName;

    /**
     * 属性编码
     */
    private String attributeCode;

    /**
     * 属性类型
     */
    private String attributeType;

    /**
     * 属性默认值
     */
    private String attributeValue;

    /**
     * 序号
     */
    private Integer sort;
}
