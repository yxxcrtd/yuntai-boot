package com.joyintech.yuntai.module.system.api.menu;

import com.joyintech.yuntai.framework.common.enums.CommonStatusEnum;
import com.joyintech.yuntai.module.system.api.menu.dto.MenuDTO;
import com.joyintech.yuntai.module.system.enums.permission.MenuTypeEnum;
import com.joyintech.yuntai.module.system.enums.permission.MoudleTypeEnum;

import java.util.List;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/7
 */
public interface MenuApi {

    /**
     * 批量创建按钮菜单
     * @param btList
     */
    void createButtonMenu(List<MenuDTO> btList);

    /**
     * 创建菜单
     * @param menu
     */
    void createMenu(MenuDTO menu);

    /**
     * 修改菜单
     * @param menu
     */
    void updateMenu(MenuDTO menu);

    /**
     * 删除菜单
     * @param id
     */
    void delete(Long id);

    /**
     * 查询菜单信息
     * @param functionId
     * @return
     */
    MenuDTO getByFunctionId(Long functionId,MenuTypeEnum menuTypeEnum);

    /**
     * 查询菜单信息根据pageId
     * @param pageId
     * @param menuType
     * @return
     */
    MenuDTO getByPageId(Long pageId, MenuTypeEnum menuType);

    /**
     * 查询路径
     * @param path
     * @param menuType
     * @return
     */
    List<MenuDTO> selectList(String path, MenuTypeEnum menuType);


    /**
     * 创建菜单
     * @param menuName
     * @param pageId
     * @param functionId
     * @param path
     * @param sort
     * @param menuType
     * @param visible
     * @return
     */
    default MenuDTO newMenu(String menuName,Long pageId,Long functionId,String path,Integer sort,
                            MenuTypeEnum menuType,Boolean visible) {
        MenuDTO menu = new MenuDTO();
        menu.setName(menuName);
        menu.setStatus(CommonStatusEnum.ENABLE.getStatus());
        menu.setModuleType(MoudleTypeEnum.ADMIN.getType());
        menu.setType(menuType.getType());
        menu.setKeepAlive(true);
        menu.setAlwaysShow(true);
        menu.setVisible(visible);
        menu.setSort(sort);
        menu.setFunctionId(functionId);
        menu.setPageId(pageId);
        menu.setPath(path);
        return menu;
    }

    /**
     * 复制菜单
     *
     * @param oldPageId
     * @param newPageId
     */
    void copyButtonMenu(Long oldPageId, Long newPageId);
}
