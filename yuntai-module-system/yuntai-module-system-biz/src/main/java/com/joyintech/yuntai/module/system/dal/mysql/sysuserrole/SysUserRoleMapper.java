package com.joyintech.yuntai.module.system.dal.mysql.sysuserrole;

import java.util.List;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.system.controller.admin.sysuserrole.vo.SysUserRolePageReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.permission.UserRoleDO;
import com.joyintech.yuntai.module.system.dal.dataobject.sysuserrole.SysUserRoleDO;

/**
 * 用户角色 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapperX<SysUserRoleDO> {

    default PageResult<SysUserRoleDO> selectPage(SysUserRolePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysUserRoleDO>()
                .eqIfPresent(SysUserRoleDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SysUserRoleDO::getRoleId, reqVO.getRoleId())
                .eqIfPresent(SysUserRoleDO::getTenantCode, reqVO.getTenantCode())
                .orderByDesc(SysUserRoleDO::getId));
    }

    default List<SysUserRoleDO> selectListByUserId(String userId) {
        return selectList(SysUserRoleDO::getUserId, userId);
    }
}