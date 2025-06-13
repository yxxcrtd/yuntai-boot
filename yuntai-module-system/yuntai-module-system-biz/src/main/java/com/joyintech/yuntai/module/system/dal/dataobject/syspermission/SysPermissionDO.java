package com.joyintech.yuntai.module.system.dal.dataobject.syspermission;

import lombok.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 按钮菜单 DO
 *
 * @author 兆尹云台
 */
@TableName("sys_permission")
@Data
/*@EqualsAndHashCode(callSuper = true)*/
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysPermissionDO /*extends BaseDO*/ {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 
     */
    private String parentId;
    /**
     * 
     */
    private String code;
    /**
     * 
     */
    private String name;
    /**
     * 
     */
    private Integer canFavorite;
    /**
     * 
     */
    private String menuAlias;
    /**
     * 
     */
    private String busParams;
    /**
     * 
     */
    private String busGroup;
    /**
     * 
     */
    private String url;
    /**
     * 
     */
    private String component;
    /**
     * 
     */
    private String componentName;
    /**
     * 
     */
    private String redirect;
    /**
     * 
     */
    private Integer menuType;
    /**
     * 
     */
    private String perms;
    /**
     * 
     */
    private String permsType;
    /**
     * 
     */
    private BigDecimal sortNo;
    /**
     * 
     */
    private Integer alwaysShow;
    /**
     * 
     */
    private String icon;
    /**
     * 
     */
    private Integer isRoute;
    /**
     * 
     */
    private Integer isLeaf;
    /**
     * 
     */
    private Integer keepAlive;
    /**
     * 
     */
    private Integer hidden;
    /**
     * 
     */
    private String description;
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
    private Integer delFlag;
    /**
     * 
     */
    private Integer ruleFlag;
    /**
     * 
     */
    private String status;
    /**
     * 
     */
    private Integer internalOrExternal;
    /**
     * 
     */
    private String systemType;
    /**
     * 
     */
    private Boolean hiddenType;

}