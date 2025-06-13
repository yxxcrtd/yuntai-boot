package com.joyintech.yuntai.module.system.api.user;

import com.joyintech.yuntai.module.system.api.user.dto.ApiSysUser;

public interface SysUserApi {
    ApiSysUser getUser(String id);
    ApiSysUser getUserByUsername(String username);
}
