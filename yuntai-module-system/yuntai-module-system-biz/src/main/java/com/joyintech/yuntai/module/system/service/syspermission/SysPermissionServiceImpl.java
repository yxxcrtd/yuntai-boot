package com.joyintech.yuntai.module.system.service.syspermission;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.system.controller.admin.permission.vo.syspermission.SysPermissionPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.syspermission.SysPermissionSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.permission.MenuDO;
import com.joyintech.yuntai.module.system.dal.dataobject.permission.RoleMenuDO;
import com.joyintech.yuntai.module.system.dal.dataobject.permission.UserRoleDO;
import com.joyintech.yuntai.module.system.dal.dataobject.rolepermission.RolePermissionDO;
import com.joyintech.yuntai.module.system.dal.dataobject.syspermission.SysPermissionDO;
import com.joyintech.yuntai.module.system.dal.dataobject.sysrole.SysRoleDO;
import com.joyintech.yuntai.module.system.dal.dataobject.sysuserrole.SysUserRoleDO;
import com.joyintech.yuntai.module.system.dal.mysql.permission.UserRoleMapper;
import com.joyintech.yuntai.module.system.dal.mysql.rolepermission.RolePermissionMapper;
import com.joyintech.yuntai.module.system.dal.mysql.syspermission.SysPermissionMapper;
import com.joyintech.yuntai.module.system.dal.mysql.sysrole.SysRoleMapper;
import com.joyintech.yuntai.module.system.dal.mysql.sysuserrole.SysUserRoleMapper;
import com.joyintech.yuntai.module.system.enums.permission.RoleCodeEnum;
import com.joyintech.yuntai.module.system.service.permission.RoleService;
import com.joyintech.yuntai.module.system.service.sysrole.SysRoleService;

import cn.hutool.core.collection.CollUtil;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.framework.common.util.collection.CollectionUtils.convertSet;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.SYSPERMISSION_NOT_EXISTS;

/**
 * 按钮菜单 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;


    @Override
    public String createSysPermission(SysPermissionSaveReqVO createReqVO) {
        // 插入
        SysPermissionDO sysPermission = BeanUtils.toBean(createReqVO, SysPermissionDO.class);
        sysPermissionMapper.insert(sysPermission);
        // 返回
        return sysPermission.getId();
    }

    @Override
    public void updateSysPermission(SysPermissionSaveReqVO updateReqVO) {
        // 校验存在
        validateSysPermissionExists(updateReqVO.getId());
        // 更新
        SysPermissionDO updateObj = BeanUtils.toBean(updateReqVO, SysPermissionDO.class);
        sysPermissionMapper.updateById(updateObj);
    }

    @Override
    public void deleteSysPermission(String id) {
        // 校验存在
        validateSysPermissionExists(id);
        // 删除
        sysPermissionMapper.deleteById(id);
    }

    private void validateSysPermissionExists(String id) {
        if (sysPermissionMapper.selectById(id) == null) {
            throw exception(SYSPERMISSION_NOT_EXISTS);
        }
    }

    @Override
    public SysPermissionDO getSysPermission(String id) {
        return sysPermissionMapper.selectById(id);
    }

    @Override
    public PageResult<SysPermissionDO> getSysPermissionPage(SysPermissionPageReqVO pageReqVO) {
        return sysPermissionMapper.selectPage(pageReqVO);
    }

    @Override
    public Set<String> getUserRoleIdListByUserId(String userId) {
        return convertSet(sysUserRoleMapper.selectListByUserId(userId), SysUserRoleDO::getRoleId);
    }

    @Override
    public Set<String> getRoleMenuListByRoleId(Collection<String> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptySet();
        }

        List<SysRoleDO> roleList = sysRoleMapper.selectList("id", roleIds);
        if(roleList!=null && !roleList.isEmpty()){
            for(SysRoleDO role : roleList){
                // 如果是管理员的情况下，获取全部菜单编号
                if(RoleCodeEnum.SUPER_ROLE.getCode().equals(role.getRoleCode())){
                    return convertSet(sysPermissionMapper.selectList(), SysPermissionDO::getPerms);
                }
            }
            // 如果是非管理员的情况下，获得拥有的菜单编号
            Set<String> permissionIds = convertSet(rolePermissionMapper.selectList("role_id", roleIds), RolePermissionDO::getPermissionId);
            return convertSet(sysPermissionMapper.selectBatchIds(permissionIds), SysPermissionDO::getPerms);
        }
        return Collections.emptySet();
    }

}