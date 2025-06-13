package com.joyintech.yuntai.module.system.dal.dataobject.rolepermission;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 角色按钮菜单权限 DO
 *
 * @author 兆尹云台
 */
@TableName("sys_role_permission")
@Data
/*@EqualsAndHashCode(callSuper = true)*/
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDO /*extends BaseDO*/ {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 
     */
    private String roleId;
    /**
     * 
     */
    private String permissionId;
    /**
     * 
     */
    private String dataRuleIds;
    /**
     * 
     */
    private LocalDateTime operateDate;
    /**
     * 
     */
    private String operateIp;
    /**
     * 
     */
    private String tenantCode;

}