package com.joyintech.yuntai.module.system.sysuser.service;

import java.util.List;
import com.joyintech.yuntai.module.system.dal.dataobject.user.AdminUserDO;
import com.joyintech.yuntai.module.system.sysuser.model.SysUser;

public interface SysUserService {
    SysUser getUserByUsername(String username);

    String create(String digestType, String id, String userCode, String password, String salt);

    SysUser getUser(String id);

    List<SysUser> getUserList(List<String> idList);
}
