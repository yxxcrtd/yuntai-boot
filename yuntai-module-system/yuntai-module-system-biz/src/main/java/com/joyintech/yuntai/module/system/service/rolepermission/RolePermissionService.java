package com.joyintech.yuntai.module.system.service.rolepermission;

import javax.validation.*;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.system.controller.admin.rolepermission.vo.RolePermissionPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.rolepermission.vo.RolePermissionSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.rolepermission.RolePermissionDO;

/**
 * 角色按钮菜单权限 Service 接口
 *
 * @author 兆尹云台
 */
public interface RolePermissionService {

    /**
     * 创建角色按钮菜单权限
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createRolePermission(@Valid RolePermissionSaveReqVO createReqVO);

    /**
     * 更新角色按钮菜单权限
     *
     * @param updateReqVO 更新信息
     */
    void updateRolePermission(@Valid RolePermissionSaveReqVO updateReqVO);

    /**
     * 删除角色按钮菜单权限
     *
     * @param id 编号
     */
    void deleteRolePermission(String id);

    /**
     * 获得角色按钮菜单权限
     *
     * @param id 编号
     * @return 角色按钮菜单权限
     */
    RolePermissionDO getRolePermission(String id);

    /**
     * 获得角色按钮菜单权限分页
     *
     * @param pageReqVO 分页查询
     * @return 角色按钮菜单权限分页
     */
    PageResult<RolePermissionDO> getRolePermissionPage(RolePermissionPageReqVO pageReqVO);

}