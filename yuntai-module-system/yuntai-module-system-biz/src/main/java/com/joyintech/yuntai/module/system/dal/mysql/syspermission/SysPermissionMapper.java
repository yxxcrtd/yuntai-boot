package com.joyintech.yuntai.module.system.dal.mysql.syspermission;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.syspermission.SysPermissionPageReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.syspermission.SysPermissionDO;

/**
 * 按钮菜单 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface SysPermissionMapper extends BaseMapperX<SysPermissionDO> {

    default PageResult<SysPermissionDO> selectPage(SysPermissionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysPermissionDO>()
                .eqIfPresent(SysPermissionDO::getParentId, reqVO.getParentId())
                .eqIfPresent(SysPermissionDO::getCode, reqVO.getCode())
                .likeIfPresent(SysPermissionDO::getName, reqVO.getName())
                .eqIfPresent(SysPermissionDO::getCanFavorite, reqVO.getCanFavorite())
                .eqIfPresent(SysPermissionDO::getMenuAlias, reqVO.getMenuAlias())
                .eqIfPresent(SysPermissionDO::getBusParams, reqVO.getBusParams())
                .eqIfPresent(SysPermissionDO::getBusGroup, reqVO.getBusGroup())
                .eqIfPresent(SysPermissionDO::getUrl, reqVO.getUrl())
                .eqIfPresent(SysPermissionDO::getComponent, reqVO.getComponent())
                .likeIfPresent(SysPermissionDO::getComponentName, reqVO.getComponentName())
                .eqIfPresent(SysPermissionDO::getRedirect, reqVO.getRedirect())
                .eqIfPresent(SysPermissionDO::getMenuType, reqVO.getMenuType())
                .eqIfPresent(SysPermissionDO::getPerms, reqVO.getPerms())
                .eqIfPresent(SysPermissionDO::getPermsType, reqVO.getPermsType())
                .eqIfPresent(SysPermissionDO::getSortNo, reqVO.getSortNo())
                .eqIfPresent(SysPermissionDO::getAlwaysShow, reqVO.getAlwaysShow())
                .eqIfPresent(SysPermissionDO::getIcon, reqVO.getIcon())
                .eqIfPresent(SysPermissionDO::getIsRoute, reqVO.getIsRoute())
                .eqIfPresent(SysPermissionDO::getIsLeaf, reqVO.getIsLeaf())
                .eqIfPresent(SysPermissionDO::getKeepAlive, reqVO.getKeepAlive())
                .eqIfPresent(SysPermissionDO::getHidden, reqVO.getHidden())
                .eqIfPresent(SysPermissionDO::getDescription, reqVO.getDescription())
                .eqIfPresent(SysPermissionDO::getCreateBy, reqVO.getCreateBy())
                .eqIfPresent(SysPermissionDO::getUpdateBy, reqVO.getUpdateBy())
                .eqIfPresent(SysPermissionDO::getDelFlag, reqVO.getDelFlag())
                .eqIfPresent(SysPermissionDO::getRuleFlag, reqVO.getRuleFlag())
                .eqIfPresent(SysPermissionDO::getStatus, reqVO.getStatus())
                .eqIfPresent(SysPermissionDO::getInternalOrExternal, reqVO.getInternalOrExternal())
                .eqIfPresent(SysPermissionDO::getSystemType, reqVO.getSystemType())
                .eqIfPresent(SysPermissionDO::getHiddenType, reqVO.getHiddenType())
                .orderByDesc(SysPermissionDO::getId));
    }

}