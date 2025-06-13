package com.joyintech.yuntai.module.system.service.sysrole;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.system.controller.admin.sysrole.vo.SysRolePageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.sysrole.vo.SysRoleSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.sysrole.SysRoleDO;
import com.joyintech.yuntai.module.system.dal.dataobject.sysuserrole.SysUserRoleDO;
import com.joyintech.yuntai.module.system.dal.mysql.sysrole.SysRoleMapper;
import com.joyintech.yuntai.module.system.dal.mysql.sysuserrole.SysUserRoleMapper;
import com.joyintech.yuntai.module.system.sysuser.mapper.SysUserMapper;
import com.joyintech.yuntai.module.system.sysuser.model.SysUser;

import cn.hutool.core.collection.CollectionUtil;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.ROLE_NOT_EXISTS;

/**
 * 角色 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public String createSysRole(SysRoleSaveReqVO createReqVO) {
        // 插入
        SysRoleDO role = BeanUtils.toBean(createReqVO, SysRoleDO.class);
        sysRoleMapper.insert(role);
        // 返回
        return role.getId();
    }

    @Override
    public void updateSysRole(SysRoleSaveReqVO updateReqVO) {
        // 校验存在
        validateSysRoleExists(updateReqVO.getId());
        // 更新
        SysRoleDO updateObj = BeanUtils.toBean(updateReqVO, SysRoleDO.class);
        sysRoleMapper.updateById(updateObj);
    }

    @Override
    public void deleteSysRole(String id) {
        // 校验存在
        validateSysRoleExists(id);
        // 删除
        sysRoleMapper.deleteById(id);
    }

    private void validateSysRoleExists(String id) {
        if (sysRoleMapper.selectById(id) == null) {
            throw exception(ROLE_NOT_EXISTS);
        }
    }

    @Override
    public SysRoleDO getSysRole(String id) {
        return sysRoleMapper.selectById(id);
    }

    @Override
    public PageResult<SysRoleDO> getSysRolePage(SysRolePageReqVO pageReqVO) {
        return sysRoleMapper.selectPage(pageReqVO);
    }

    /**
     * 根据角色（多个）获取用户
     *
     * @param ids
     * @return
     */
    @Override
    public List<SysUser> getUsers(List<String> ids) {
        if(ids==null || ids.isEmpty()){
            return null;
        }
        List<SysUserRoleDO> roleList = sysUserRoleMapper.selectList("role_id", ids);
        if(roleList!=null && !roleList.isEmpty()){
            List<String> userIdList = roleList.stream().map(SysUserRoleDO::getUserId).collect(Collectors.toList());
            if(!userIdList.isEmpty()){
                return sysUserMapper.selectList("id", userIdList);
            }
        }
        return null;
    }

}