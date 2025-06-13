package com.joyintech.yuntai.module.system.dal.mysql.rolepermission;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.system.controller.admin.rolepermission.vo.RolePermissionPageReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.rolepermission.RolePermissionDO;

/**
 * 角色按钮菜单权限 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface RolePermissionMapper extends BaseMapperX<RolePermissionDO> {

    default PageResult<RolePermissionDO> selectPage(RolePermissionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RolePermissionDO>()
                .eqIfPresent(RolePermissionDO::getRoleId, reqVO.getRoleId())
                .eqIfPresent(RolePermissionDO::getPermissionId, reqVO.getPermissionId())
                .eqIfPresent(RolePermissionDO::getDataRuleIds, reqVO.getDataRuleIds())
                .betweenIfPresent(RolePermissionDO::getOperateDate, reqVO.getOperateDate())
                .eqIfPresent(RolePermissionDO::getOperateIp, reqVO.getOperateIp())
                .eqIfPresent(RolePermissionDO::getTenantCode, reqVO.getTenantCode())
                .orderByDesc(RolePermissionDO::getId));
    }

}