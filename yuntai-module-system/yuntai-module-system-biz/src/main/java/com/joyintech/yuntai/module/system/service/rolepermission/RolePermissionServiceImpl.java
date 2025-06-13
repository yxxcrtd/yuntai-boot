package com.joyintech.yuntai.module.system.service.rolepermission;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.system.controller.admin.rolepermission.vo.RolePermissionPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.rolepermission.vo.RolePermissionSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.rolepermission.RolePermissionDO;
import com.joyintech.yuntai.module.system.dal.mysql.rolepermission.RolePermissionMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.ROLE_PERMISSION_NOT_EXISTS;

/**
 * 角色按钮菜单权限 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class RolePermissionServiceImpl implements RolePermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public String createRolePermission(RolePermissionSaveReqVO createReqVO) {
        // 插入
        RolePermissionDO rolePermission = BeanUtils.toBean(createReqVO, RolePermissionDO.class);
        rolePermissionMapper.insert(rolePermission);
        // 返回
        return rolePermission.getId();
    }

    @Override
    public void updateRolePermission(RolePermissionSaveReqVO updateReqVO) {
        // 校验存在
        validateRolePermissionExists(updateReqVO.getId());
        // 更新
        RolePermissionDO updateObj = BeanUtils.toBean(updateReqVO, RolePermissionDO.class);
        rolePermissionMapper.updateById(updateObj);
    }

    @Override
    public void deleteRolePermission(String id) {
        // 校验存在
        validateRolePermissionExists(id);
        // 删除
        rolePermissionMapper.deleteById(id);
    }

    private void validateRolePermissionExists(String id) {
        if (rolePermissionMapper.selectById(id) == null) {
            throw exception(ROLE_PERMISSION_NOT_EXISTS);
        }
    }

    @Override
    public RolePermissionDO getRolePermission(String id) {
        return rolePermissionMapper.selectById(id);
    }

    @Override
    public PageResult<RolePermissionDO> getRolePermissionPage(RolePermissionPageReqVO pageReqVO) {
        return rolePermissionMapper.selectPage(pageReqVO);
    }

}