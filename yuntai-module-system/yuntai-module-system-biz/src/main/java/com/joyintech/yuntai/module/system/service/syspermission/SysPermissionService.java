package com.joyintech.yuntai.module.system.service.syspermission;

import java.util.Collection;
import java.util.Set;
import javax.validation.*;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.syspermission.SysPermissionPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.syspermission.SysPermissionSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.syspermission.SysPermissionDO;

import static java.util.Collections.singleton;

/**
 * 按钮菜单 Service 接口
 *
 * @author 兆尹云台
 */
public interface SysPermissionService {

    /**
     * 创建按钮菜单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createSysPermission(@Valid SysPermissionSaveReqVO createReqVO);

    /**
     * 更新按钮菜单
     *
     * @param updateReqVO 更新信息
     */
    void updateSysPermission(@Valid SysPermissionSaveReqVO updateReqVO);

    /**
     * 删除按钮菜单
     *
     * @param id 编号
     */
    void deleteSysPermission(String id);

    /**
     * 获得按钮菜单
     *
     * @param id 编号
     * @return 按钮菜单
     */
    SysPermissionDO getSysPermission(String id);

    /**
     * 获得按钮菜单分页
     *
     * @param pageReqVO 分页查询
     * @return 按钮菜单分页
     */
    PageResult<SysPermissionDO> getSysPermissionPage(SysPermissionPageReqVO pageReqVO);

    /**
     * 获得用户拥有的角色编号集合
     *
     * @param userId 用户编号
     * @return 角色编号集合
     */
    Set<String> getUserRoleIdListByUserId(String userId);

    /**
     * 获得角色拥有的菜单编号集合
     *
     * @param roleId 角色编号
     * @return 菜单编号集合
     */
    default Set<String> getRoleMenuListByRoleId(String roleId) {
        return getRoleMenuListByRoleId(singleton(roleId));
    }

    /**
     * 获得角色们拥有的菜单编号集合
     *
     * @param roleIds 角色编号数组
     * @return 菜单编号集合
     */
    Set<String> getRoleMenuListByRoleId(Collection<String> roleIds);

}