package com.joyintech.yuntai.module.system.dal.dataobject.treedata;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 字典树子 DO
 *
 * @author 兆尹云台
 */
@TableName("comm_tree_data")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeDataDO extends BaseDO {

    /**
     * 
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 
     */
    private String treeType;
    /**
     * 
     */
    private String nodeCode;
    /**
     * 
     */
    private String nodeText;
    /**
     * 
     */
    private String shortText;
    /**
     * 
     */
    private Integer nodeLevel;
    /**
     * 
     */
    private String parentCode;
    /**
     * 
     */
    private Integer sortIndex;
    /**
     * 
     */
    private String createBy;
    /**
     * 
     */
    private String updateBy;
    /**
     * 
     */
    private Integer status;
    /**
     * 
     */
    private String treeGroup;
    /**
     * 
     */
    private String treeName;

}