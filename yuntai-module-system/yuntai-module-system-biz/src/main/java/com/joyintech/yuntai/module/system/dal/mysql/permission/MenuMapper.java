package com.joyintech.yuntai.module.system.dal.mysql.permission;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.permission.MenuDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapperX<MenuDO> {

    default MenuDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(MenuDO::getParentId, parentId, MenuDO::getName, name);
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(MenuDO::getParentId, parentId);
    }
    default List<MenuDO> selectChildList(Long parentId) {
        return selectList(new LambdaQueryWrapperX<MenuDO>()
                .eqIfPresent(MenuDO::getParentId, parentId));
    }

    default List<MenuDO> selectList(MenuListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MenuDO>()
                .likeIfPresent(MenuDO::getName, reqVO.getName())
                .eqIfPresent(MenuDO::getStatus, reqVO.getStatus())
                .eqIfPresent(MenuDO::getModuleType, reqVO.getModuleType())
                .eqIfPresent(MenuDO::getFunctionId, reqVO.getFunctionId())
                .eqIfPresent(MenuDO::getParentId, reqVO.getId())
                .likeIfPresent(MenuDO::getPath, reqVO.getPath())
                .eqIfPresent(MenuDO::getType, reqVO.getType())
                .eqIfPresent(MenuDO::getPageId, reqVO.getPageId()));

    }

    default List<MenuDO> selectListByPermission(String permission) {
        return selectList(MenuDO::getPermission, permission);
    }
}
