package com.joyintech.yuntai.module.system.api.menu;

import cn.hutool.core.collection.CollUtil;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.system.api.menu.dto.MenuDTO;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.menu.MenuSaveVO;
import com.joyintech.yuntai.module.system.dal.dataobject.permission.MenuDO;
import com.joyintech.yuntai.module.system.enums.permission.MenuTypeEnum;
import com.joyintech.yuntai.module.system.service.permission.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述
 * 菜单操作
 * @Author Administrator
 * @Date 2024/11/7
 */
@Service
public class MenuApiImpl implements MenuApi {
    @Resource
    private MenuService menuService;

    @Override
    public void createButtonMenu(List<MenuDTO> btList) {
        menuService.createButtonMenu(BeanUtils.toBean(btList, MenuSaveVO.class));
    }

    @Override
    public void createMenu(MenuDTO menu) {
        menuService.createMenu(BeanUtils.toBean(menu, MenuSaveVO.class));
    }

    @Override
    public void updateMenu(MenuDTO menu) {
        menuService.updateMenu(BeanUtils.toBean(menu, MenuSaveVO.class));
    }

    @Override
    public void delete(Long id) {
        menuService.deleteMenu(id);
    }

    @Override
    public MenuDTO getByFunctionId(Long functionId, MenuTypeEnum menuType) {
        MenuListReqVO reqVO = new MenuListReqVO();
        reqVO.setFunctionId(functionId);
        List<MenuDO> list = menuService.getMenuList(reqVO);
        if(CollUtil.isEmpty(list))return null;
        for(MenuDO item : list){
            if(item.getType().equals(menuType.getType())){
                return BeanUtils.toBean(item,MenuDTO.class);
            }
        }
        return null;
    }

    @Override
    public MenuDTO getByPageId(Long pageId, MenuTypeEnum menuType) {
        MenuListReqVO reqVO = new MenuListReqVO();
        reqVO.setPageId(pageId);
        List<MenuDO> list = menuService.getMenuList(reqVO);
        if(CollUtil.isEmpty(list))return null;
        for(MenuDO item : list){
            if(item.getType().equals(menuType.getType())){
                return BeanUtils.toBean(item,MenuDTO.class);
            }
        }
        return null;
    }

    @Override
    public List<MenuDTO> selectList(String path, MenuTypeEnum menuType) {
        MenuListReqVO reqVO = new MenuListReqVO();
        reqVO.setPath(path);
        reqVO.setType(menuType.getType());
        List<MenuDO> list = menuService.getMenuList(reqVO);
        return BeanUtils.toBean(list,MenuDTO.class);
    }

    /**
     * 复制菜单
     *
     * @param oldPageId
     * @param newPageId
     */
    @Override
    public void copyButtonMenu(Long oldPageId, Long newPageId) {
        MenuListReqVO reqVO = new MenuListReqVO();
        reqVO.setPageId(oldPageId);
        List<MenuDO> menuList = menuService.getMenuList(reqVO);
        if(!menuList.isEmpty()){
            for(MenuDO menu : menuList){
                menu.setOldId(menu.getId());
                menu.setId(null);
                menu.setCreator(null);
                menu.setCreateTime(null);
                menu.setUpdater(null);
                menu.setUpdateTime(null);

                menu.setPageId(newPageId);
                if(StringUtils.isNotBlank(menu.getPermission())){
                    menu.setPermission(menu.getPermission().replace(String.valueOf(oldPageId), String.valueOf(newPageId)));
                }
            }
            menuService.insertBatch(menuList);
        }
    }
}
