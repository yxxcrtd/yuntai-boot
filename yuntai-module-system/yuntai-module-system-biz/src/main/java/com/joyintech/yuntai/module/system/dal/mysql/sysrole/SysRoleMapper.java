package com.joyintech.yuntai.module.system.dal.mysql.sysrole;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.system.controller.admin.sysrole.vo.SysRolePageReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.sysrole.SysRoleDO;

/**
 * 角色 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface SysRoleMapper extends BaseMapperX<SysRoleDO> {

    default PageResult<SysRoleDO> selectPage(SysRolePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysRoleDO>()
                .likeIfPresent(SysRoleDO::getRoleName, reqVO.getRoleName())
                .eqIfPresent(SysRoleDO::getRoleCode, reqVO.getRoleCode())
                .eqIfPresent(SysRoleDO::getTenantCode, reqVO.getTenantCode())
                .eqIfPresent(SysRoleDO::getRoleType, reqVO.getRoleType())
                .orderByDesc(SysRoleDO::getId));
    }

}