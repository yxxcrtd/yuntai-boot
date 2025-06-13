package com.joyintech.yuntai.module.system.dal.dataobject.sysuserrole;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户角色 DO
 *
 * @author 兆尹云台
 */
@TableName("sys_user_role")
@Data
//@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRoleDO/* extends BaseDO*/ {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 
     */
    private String userId;
    /**
     * 
     */
    private String roleId;
    /**
     * 
     */
    private String tenantCode;

}