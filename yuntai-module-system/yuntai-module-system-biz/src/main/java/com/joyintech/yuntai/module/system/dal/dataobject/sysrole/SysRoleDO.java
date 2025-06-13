package com.joyintech.yuntai.module.system.dal.dataobject.sysrole;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 角色 DO
 *
 * @author 兆尹云台
 */
@TableName("sys_role")
@Data
/*@EqualsAndHashCode(callSuper = true)*/
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDO /*extends BaseDO*/ {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 
     */
    private String roleName;
    /**
     * 
     */
    private String roleCode;
    /**
     * 
     */
    private String tenantCode;
    /**
     * 
     */
    private Boolean roleType;

}