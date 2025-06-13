package com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 字段管理组件 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_column_component_attribute")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnComponentAttributeDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 列主键
     */
    private Long columnId;
    /**
     * 组件属性id
     */
    private Long attributeId;
    /**
     * 组件属性值
     */
    private String attributeValue;

    /**
     * 页面id
     */
    private Long pageId;

    /**
     * 优先级
     */
    private Long priority;

    /**
     * 属性
     */
    @TableField(exist = false)
    private String attributeCode;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 页面联动配置id
     */
    private Long linkageId;
}
