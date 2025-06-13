package com.joyintech.yuntai.module.system.api.user;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.system.api.user.dto.ApiSysUser;
import com.joyintech.yuntai.module.system.sysuser.model.SysUser;
import com.joyintech.yuntai.module.system.sysuser.service.SysUserService;

/**
 * 类描述：查询业务系统用户信息
 *
 * @author liuyanlong
 * @version 1.0
 * @since 2025/4/12
 */
@Service
public class SysUserApiImpl implements SysUserApi {

    @Resource
    private SysUserService sysUserService;

    @Override
    public ApiSysUser getUser(String id) {
        SysUser sysUser = sysUserService.getUser(id);
        return BeanUtils.toBean(sysUser, ApiSysUser.class);
    }

    @Override
    public ApiSysUser getUserByUsername(String username) {
        SysUser userByUsername = sysUserService.getUserByUsername(username);
        return BeanUtils.toBean(userByUsername, ApiSysUser.class);
    }
}
