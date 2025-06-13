package com.joyintech.yuntai.module.system.service.sysuserrole;

import javax.validation.*;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.system.controller.admin.sysuserrole.vo.SysUserRolePageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.sysuserrole.vo.SysUserRoleSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.sysuserrole.SysUserRoleDO;

/**
 * 用户角色 Service 接口
 *
 * @author 兆尹云台
 */
public interface SysUserRoleService {

    /**
     * 创建用户角色
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createSysUserRole(@Valid SysUserRoleSaveReqVO createReqVO);

    /**
     * 更新用户角色
     *
     * @param updateReqVO 更新信息
     */
    void updateSysUserRole(@Valid SysUserRoleSaveReqVO updateReqVO);

    /**
     * 删除用户角色
     *
     * @param id 编号
     */
    void deleteSysUserRole(String id);

    /**
     * 获得用户角色
     *
     * @param id 编号
     * @return 用户角色
     */
    SysUserRoleDO getSysUserRole(String id);

    /**
     * 获得用户角色分页
     *
     * @param pageReqVO 分页查询
     * @return 用户角色分页
     */
    PageResult<SysUserRoleDO> getSysUserRolePage(SysUserRolePageReqVO pageReqVO);

}