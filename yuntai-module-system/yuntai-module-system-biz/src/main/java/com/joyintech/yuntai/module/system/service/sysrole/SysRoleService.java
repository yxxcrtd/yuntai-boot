package com.joyintech.yuntai.module.system.service.sysrole;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.validation.*;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.system.controller.admin.sysrole.vo.SysRolePageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.sysrole.vo.SysRoleSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.permission.RoleDO;
import com.joyintech.yuntai.module.system.dal.dataobject.sysrole.SysRoleDO;
import com.joyintech.yuntai.module.system.sysuser.model.SysUser;

/**
 * 角色 Service 接口
 *
 * @author 兆尹云台
 */
public interface SysRoleService {

    /**
     * 创建角色
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createSysRole(@Valid SysRoleSaveReqVO createReqVO);

    /**
     * 更新角色
     *
     * @param updateReqVO 更新信息
     */
    void updateSysRole(@Valid SysRoleSaveReqVO updateReqVO);

    /**
     * 删除角色
     *
     * @param id 编号
     */
    void deleteSysRole(String id);

    /**
     * 获得角色
     *
     * @param id 编号
     * @return 角色
     */
    SysRoleDO getSysRole(String id);

    /**
     * 获得角色分页
     *
     * @param pageReqVO 分页查询
     * @return 角色分页
     */
    PageResult<SysRoleDO> getSysRolePage(SysRolePageReqVO pageReqVO);

    /**
     * 根据角色（多个）获取用户
     *
     * @param ids
     * @return
     */
    List<SysUser> getUsers(List<String> ids);
}