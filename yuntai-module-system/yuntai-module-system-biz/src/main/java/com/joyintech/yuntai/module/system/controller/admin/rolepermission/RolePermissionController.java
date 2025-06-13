package com.joyintech.yuntai.module.system.controller.admin.rolepermission;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import com.joyintech.yuntai.framework.excel.core.util.ExcelUtils;

import com.joyintech.yuntai.framework.apilog.core.annotation.ApiAccessLog;
import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.*;

import com.joyintech.yuntai.module.system.controller.admin.rolepermission.vo.RolePermissionPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.rolepermission.vo.RolePermissionRespVO;
import com.joyintech.yuntai.module.system.controller.admin.rolepermission.vo.RolePermissionSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.rolepermission.RolePermissionDO;
import com.joyintech.yuntai.module.system.service.rolepermission.RolePermissionService;

@Tag(name = "管理后台 - 角色按钮菜单权限")
@RestController
@RequestMapping("/sys/role-permission")
@Validated
public class RolePermissionController {

    @Resource
    private RolePermissionService rolePermissionService;

    @PostMapping("/create")
    @Operation(summary = "创建角色按钮菜单权限")
    @PreAuthorize("@ss.hasPermission('sys:role-permission:create')")
    public CommonResult<String> createRolePermission(@Valid @RequestBody RolePermissionSaveReqVO createReqVO) {
        return success(rolePermissionService.createRolePermission(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新角色按钮菜单权限")
    @PreAuthorize("@ss.hasPermission('sys:role-permission:update')")
    public CommonResult<Boolean> updateRolePermission(@Valid @RequestBody RolePermissionSaveReqVO updateReqVO) {
        rolePermissionService.updateRolePermission(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除角色按钮菜单权限")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sys:role-permission:delete')")
    public CommonResult<Boolean> deleteRolePermission(@RequestParam("id") String id) {
        rolePermissionService.deleteRolePermission(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得角色按钮菜单权限")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sys:role-permission:query')")
    public CommonResult<RolePermissionRespVO> getRolePermission(@RequestParam("id") String id) {
        RolePermissionDO rolePermission = rolePermissionService.getRolePermission(id);
        return success(BeanUtils.toBean(rolePermission, RolePermissionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得角色按钮菜单权限分页")
    @PreAuthorize("@ss.hasPermission('sys:role-permission:query')")
    public CommonResult<PageResult<RolePermissionRespVO>> getRolePermissionPage(@Valid RolePermissionPageReqVO pageReqVO) {
        PageResult<RolePermissionDO> pageResult = rolePermissionService.getRolePermissionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RolePermissionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出角色按钮菜单权限 Excel")
    @PreAuthorize("@ss.hasPermission('sys:role-permission:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRolePermissionExcel(@Valid RolePermissionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RolePermissionDO> list = rolePermissionService.getRolePermissionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "角色按钮菜单权限.xls", "数据", RolePermissionRespVO.class,
                        BeanUtils.toBean(list, RolePermissionRespVO.class));
    }

}