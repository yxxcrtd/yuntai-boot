package com.joyintech.yuntai.module.cfg.service.menu;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.joyintech.yuntai.module.cfg.controller.admin.pagebutton.vo.PageButtonSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.functioninfo.FunctionInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageinfo.PageInfoDO;
import com.joyintech.yuntai.module.cfg.enums.CfgFunctionTypeEnum;
import com.joyintech.yuntai.module.system.api.menu.MenuApi;
import com.joyintech.yuntai.module.system.api.menu.dto.MenuDTO;
import com.joyintech.yuntai.module.system.enums.permission.MenuTypeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/7
 */
@Service
public class MenuApiServiceImpl {
    @Resource
    private MenuApi menuApi;

    /**
     * 创建菜单
     *
     * @param functionInfo
     */
    public void createFunction(FunctionInfoDO functionInfo) {
        this.saveMenu(functionInfo);
    }

    /**
     * 更新菜单
     *
     * @param updateObj
     * @param old
     */
    public void updateFunction(FunctionInfoDO updateObj, FunctionInfoDO old) {
        if (CfgFunctionTypeEnum.MODULE.getCode().equals(updateObj.getFunctionType())) {
            MenuDTO menuDTO = menuApi.getByFunctionId(updateObj.getId(), MenuTypeEnum.DIR);
            if (menuDTO == null) {
                this.saveMenu(updateObj);
            } else if (!old.getParentId().equals(updateObj.getParentId())) {
                MenuDTO parentMenuDTO = menuApi.getByFunctionId(updateObj.getParentId(), MenuTypeEnum.DIR);
                if (Objects.nonNull(parentMenuDTO)) {
                    menuDTO.setParentId(parentMenuDTO.getId());
                    menuApi.updateMenu(menuDTO);
                }
            }
        }
    }

    /**
     * 删除菜单
     */
    public void deleteFunction(FunctionInfoDO infoDO) {
        if (CfgFunctionTypeEnum.MODULE.getCode().equals(infoDO.getFunctionType())) {
            MenuDTO menuDTO = menuApi.getByFunctionId(infoDO.getId(), MenuTypeEnum.DIR);
            if (Objects.nonNull(menuDTO)) {
                menuApi.delete(menuDTO.getId());
            }
        }
    }

    /**
     * 保存菜单
     *
     * @param functionInfo
     */
    private void saveMenu(FunctionInfoDO functionInfo) {
        if (CfgFunctionTypeEnum.MODULE.getCode().equals(functionInfo.getFunctionType())) {
            MenuDTO menu = menuApi.newMenu(functionInfo.getFunctionName(), null, functionInfo.getId(), null, 1, MenuTypeEnum.DIR, true);
            menu.setPath(String.format("/%s", functionInfo.getFunctionCode()));
            List<MenuDTO> menuList = menuApi.selectList(menu.getPath(),MenuTypeEnum.DIR);
            if (CollUtil.isNotEmpty(menuList)) {
                menu.setPath(String.format("/%s%d", functionInfo.getFunctionCode(), menuList.size()));
            }
            menu.setIcon(functionInfo.getFunctionIcon());
            if (functionInfo.getParentId() == 0) {
                menu.setParentId(0L);
            } else {
                MenuDTO parentMenu = menuApi.getByFunctionId(functionInfo.getParentId(), MenuTypeEnum.DIR);
                if (Objects.nonNull(parentMenu)) {
                    menu.setParentId(parentMenu.getId());
                }
            }
            menuApi.createMenu(menu);
        }
    }

    /**
     * 创建页面时创建菜单
     *
     * @param pageInfo
     */
    public void createPage(PageInfoDO pageInfo) {
        MenuDTO parent = menuApi.getByFunctionId(pageInfo.getMenuId(), MenuTypeEnum.DIR);
        if (Objects.nonNull(parent)) {
            MenuDTO menu = null;
            if (pageInfo.getPageType().equals("list")) {//列表默认展示
                menu = menuApi.newMenu(pageInfo.getPageName(), pageInfo.getId(), pageInfo.getMenuId(), pageInfo.getId() + "/list", 1, MenuTypeEnum.MENU, true);
            } else {
                menu = menuApi.newMenu(pageInfo.getPageName(), pageInfo.getId(), pageInfo.getMenuId(), pageInfo.getId() + "/form", 1, MenuTypeEnum.MENU, false);
            }
            menu.setParentId(parent.getId());
            menu.setComponent("/Redirect/pageView");
            menuApi.createMenu(menu);
        }
    }

    /**
     * 创建按钮
     *
     * @param btList
     */
    public void createButton(List<PageButtonSaveReqVO> btList, Long menuId) {
        Long pageId = btList.get(0).getPageId();
        MenuDTO menu = menuApi.getByPageId(pageId, MenuTypeEnum.MENU);
        if (Objects.nonNull(menu)) {
            List<MenuDTO> menuList = new ArrayList<>();
            int i = 1;
            for (PageButtonSaveReqVO bt : btList) {
                MenuDTO menuDTO = menuApi.newMenu(bt.getButtonName(), pageId, menuId, null, i, MenuTypeEnum.BUTTON, true);
                menuDTO.setParentId(menu.getId());
                if(StrUtil.isNotEmpty(bt.getPermissionSign())){
                    menuDTO.setPermission(String.format("%d:%s", pageId, bt.getPermissionSign()));
                }
                menuList.add(menuDTO);
                i++;
            }
            menuApi.createButtonMenu(menuList);
        }
    }

    /**
     * 删除菜单
     */
    public void deletePage(PageInfoDO infoDO) {
        MenuDTO menuDTO = menuApi.getByPageId(infoDO.getId(), MenuTypeEnum.MENU);
        if (Objects.nonNull(menuDTO)) {
            menuApi.delete(menuDTO.getId());
        }
    }

    /**
     * 复制菜单
     *
     * @param oldPageId
     * @param newPageId
     */
    public void copyButtonMenu(Long oldPageId, Long newPageId) {
        menuApi.copyButtonMenu(oldPageId, newPageId);
    }
}
