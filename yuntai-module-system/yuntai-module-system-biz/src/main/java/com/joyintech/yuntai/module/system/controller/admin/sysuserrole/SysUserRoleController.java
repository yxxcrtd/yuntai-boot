package com.joyintech.yuntai.module.system.controller.admin.sysuserrole;

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

import com.joyintech.yuntai.module.system.controller.admin.sysuserrole.vo.SysUserRolePageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.sysuserrole.vo.SysUserRoleRespVO;
import com.joyintech.yuntai.module.system.controller.admin.sysuserrole.vo.SysUserRoleSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.sysuserrole.SysUserRoleDO;
import com.joyintech.yuntai.module.system.service.sysuserrole.SysUserRoleService;

@Tag(name = "管理后台 - 用户角色")
@RestController
@RequestMapping("/sys/sys-user-role")
@Validated
public class SysUserRoleController {

    @Resource
    private SysUserRoleService sysUserRoleService;

    @PostMapping("/create")
    @Operation(summary = "创建用户角色")
    @PreAuthorize("@ss.hasPermission('sys:sys-user-role:create')")
    public CommonResult<String> createSysUserRole(@Valid @RequestBody SysUserRoleSaveReqVO createReqVO) {
        return success(sysUserRoleService.createSysUserRole(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新用户角色")
    @PreAuthorize("@ss.hasPermission('sys:sys-user-role:update')")
    public CommonResult<Boolean> updateSysUserRole(@Valid @RequestBody SysUserRoleSaveReqVO updateReqVO) {
        sysUserRoleService.updateSysUserRole(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除用户角色")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sys:sys-user-role:delete')")
    public CommonResult<Boolean> deleteSysUserRole(@RequestParam("id") String id) {
        sysUserRoleService.deleteSysUserRole(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户角色")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sys:sys-user-role:query')")
    public CommonResult<SysUserRoleRespVO> getSysUserRole(@RequestParam("id") String id) {
        SysUserRoleDO userRole = sysUserRoleService.getSysUserRole(id);
        return success(BeanUtils.toBean(userRole, SysUserRoleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户角色分页")
    @PreAuthorize("@ss.hasPermission('sys:sys-user-role:query')")
    public CommonResult<PageResult<SysUserRoleRespVO>> getSysUserRolePage(@Valid SysUserRolePageReqVO pageReqVO) {
        PageResult<SysUserRoleDO> pageResult = sysUserRoleService.getSysUserRolePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysUserRoleRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户角色 Excel")
    @PreAuthorize("@ss.hasPermission('sys:sys-user-role:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSysUserRoleExcel(@Valid SysUserRolePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SysUserRoleDO> list = sysUserRoleService.getSysUserRolePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户角色.xls", "数据", SysUserRoleRespVO.class,
                        BeanUtils.toBean(list, SysUserRoleRespVO.class));
    }

}