package com.joyintech.yuntai.module.system.service.sysuserrole;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.system.controller.admin.sysuserrole.vo.SysUserRolePageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.sysuserrole.vo.SysUserRoleSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.sysuserrole.SysUserRoleDO;
import com.joyintech.yuntai.module.system.dal.mysql.sysuserrole.SysUserRoleMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.USER_ROLE_NOT_EXISTS;

/**
 * 用户角色 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public String createSysUserRole(SysUserRoleSaveReqVO createReqVO) {
        // 插入
        SysUserRoleDO userRole = BeanUtils.toBean(createReqVO, SysUserRoleDO.class);
        sysUserRoleMapper.insert(userRole);
        // 返回
        return userRole.getId();
    }

    @Override
    public void updateSysUserRole(SysUserRoleSaveReqVO updateReqVO) {
        // 校验存在
        validateSysUserRoleExists(updateReqVO.getId());
        // 更新
        SysUserRoleDO updateObj = BeanUtils.toBean(updateReqVO, SysUserRoleDO.class);
        sysUserRoleMapper.updateById(updateObj);
    }

    @Override
    public void deleteSysUserRole(String id) {
        // 校验存在
        validateSysUserRoleExists(id);
        // 删除
        sysUserRoleMapper.deleteById(id);
    }

    private void validateSysUserRoleExists(String id) {
        if (sysUserRoleMapper.selectById(id) == null) {
            throw exception(USER_ROLE_NOT_EXISTS);
        }
    }

    @Override
    public SysUserRoleDO getSysUserRole(String id) {
        return sysUserRoleMapper.selectById(id);
    }

    @Override
    public PageResult<SysUserRoleDO> getSysUserRolePage(SysUserRolePageReqVO pageReqVO) {
        return sysUserRoleMapper.selectPage(pageReqVO);
    }

}