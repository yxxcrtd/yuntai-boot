package com.joyintech.yuntai.module.system.controller.admin.permission;

import cn.hutool.core.collection.CollUtil;
import com.joyintech.yuntai.framework.common.enums.CommonStatusEnum;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.pageinfo.PageinfoApi;
import com.joyintech.yuntai.module.cfg.pageinfo.dto.PageInfoDTO;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.menu.MenuRespVO;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.menu.MenuSaveVO;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.menu.MenuSimpleRespVO;
import com.joyintech.yuntai.module.system.dal.dataobject.permission.MenuDO;
import com.joyintech.yuntai.module.system.enums.permission.MenuTypeEnum;
import com.joyintech.yuntai.module.system.service.permission.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jodd.util.StringUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 菜单")
@RestController
@RequestMapping("/system/menu")
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;
    @Resource
    private PageinfoApi pageinfoApi;

    @PostMapping("/create")
    @Operation(summary = "创建菜单")
    @PreAuthorize("@ss.hasPermission('system:menu:create')")
    public CommonResult<Long> createMenu(@Valid @RequestBody MenuSaveVO createReqVO) {
        Long menuId = menuService.createMenu(createReqVO);
        return success(menuId);
    }

    @PostMapping("/button/create")
    @Operation(summary = "创建按钮")
    @PreAuthorize("@ss.hasPermission('system:menu:create')")
    public CommonResult<Long> createMenu(@Valid @RequestBody List<MenuSaveVO> btList) {
        Long menuId = menuService.createButtonMenu(btList);
        return success(menuId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改菜单")
    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public CommonResult<Boolean> updateMenu(@Valid @RequestBody MenuSaveVO updateReqVO) {
        menuService.updateMenu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜单")
    @Parameter(name = "id", description = "菜单编号", required= true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:menu:delete')")
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id) {
        menuService.deleteMenu(id);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获取菜单列表", description = "用于【菜单管理】界面")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<List<MenuRespVO>> getMenuList(MenuListReqVO reqVO) {
        List<MenuDO> list = menuService.getMenuList(reqVO);
        if (reqVO.getId() == null) {
            list = list.stream().filter(item -> !Objects.equals(item.getType(), MenuTypeEnum.BUTTON.getType())).collect(Collectors.toList());
        }
        list.sort(Comparator.comparing(MenuDO::getSort));
        List<MenuRespVO> result = BeanUtils.toBean(list, MenuRespVO.class);
        if(reqVO.getFunctionId() != null && CollUtil.isNotEmpty(result)) {
            Set<Long> set = result.stream().map(MenuRespVO::getPageId).filter(Objects::nonNull).collect(Collectors.toSet());
            if (CollUtil.isNotEmpty(set)) {
                List<PageInfoDTO> pageList = pageinfoApi.getPageInfoList(set);
                if (CollUtil.isNotEmpty(pageList)) {
                    Map<Long, PageInfoDTO> pageMap = pageList.stream().collect(Collectors.toMap(PageInfoDTO::getId, pageInfoDTO -> pageInfoDTO));
                    result.forEach(item -> {
                        PageInfoDTO pageInfo = pageMap.get(item.getPageId());
                        if (Objects.nonNull(pageInfo)) {
                            item.setPageName(pageInfo.getPageName());
                            item.setPageType(pageInfo.getPageType());
                        }
                    });
                }
            }
        }
        return success(result);
    }

    @GetMapping({"/list-all-simple", "simple-list"})
    @Operation(summary = "获取菜单精简信息列表", description = "只包含被开启的菜单，用于【角色分配菜单】功能的选项。" +
            "在多租户的场景下，会只返回租户所在套餐有的菜单")
    public CommonResult<List<MenuSimpleRespVO>> getSimpleMenuList(@RequestParam(value = "moduleType",required = false) Integer moduleType) {
        List<MenuDO> list = menuService.getMenuListByTenant(new MenuListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus()));
        if (moduleType != null) {
            list = list.stream().filter(item -> Objects.equals(item.getModuleType(), moduleType)).collect(Collectors.toList());
        }
        list = menuService.filterDisableMenus(list);
        list.sort(Comparator.comparing(MenuDO::getSort));
        return success(BeanUtils.toBean(list, MenuSimpleRespVO.class));
    }

    @GetMapping("/get")
    @Operation(summary = "获取菜单信息")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<MenuRespVO> getMenu(Long id) {
        MenuDO menu = menuService.getMenu(id);
        return success(BeanUtils.toBean(menu, MenuRespVO.class));
    }

}
