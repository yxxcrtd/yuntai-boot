package com.joyintech.yuntai.module.cfg.dal.dataobject.componentgroup;

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
 * 组件分组 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_component_group")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentGroupDO extends BaseDO {

    /**
     * 主键编号
     */
    @TableId
    private Long id;
    /**
     * 组件分组名称
     */
    private String groupName;
    /**
     * 编号排序
     */
    private Integer numSort;
    /**
     *
     */
    private Long parentId;

}