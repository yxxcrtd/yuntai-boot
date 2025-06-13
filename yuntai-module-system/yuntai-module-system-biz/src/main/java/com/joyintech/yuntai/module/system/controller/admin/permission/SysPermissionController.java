package com.joyintech.yuntai.module.system.controller.admin.permission;

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
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.syspermission.SysPermissionPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.syspermission.SysPermissionRespVO;
import com.joyintech.yuntai.module.system.controller.admin.permission.vo.syspermission.SysPermissionSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.syspermission.SysPermissionDO;
import com.joyintech.yuntai.module.system.service.syspermission.SysPermissionService;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.*;


@Tag(name = "管理后台 - 按钮菜单")
@RestController
@RequestMapping("/sys/syspermission")
@Validated
public class SysPermissionController {

    @Resource
    private SysPermissionService sysPermissionService;

    @PostMapping("/create")
    @Operation(summary = "创建按钮菜单")
    @PreAuthorize("@ss.hasPermission('sys:syspermission:create')")
    public CommonResult<String> createSysPermission(@Valid @RequestBody SysPermissionSaveReqVO createReqVO) {
        return success(sysPermissionService.createSysPermission(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新按钮菜单")
    @PreAuthorize("@ss.hasPermission('sys:syspermission:update')")
    public CommonResult<Boolean> updateSysPermission(@Valid @RequestBody SysPermissionSaveReqVO updateReqVO) {
        sysPermissionService.updateSysPermission(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除按钮菜单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sys:syspermission:delete')")
    public CommonResult<Boolean> deleteSysPermission(@RequestParam("id") String id) {
        sysPermissionService.deleteSysPermission(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得按钮菜单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sys:syspermission:query')")
    public CommonResult<SysPermissionRespVO> getSysPermission(@RequestParam("id") String id) {
        SysPermissionDO syspermission = sysPermissionService.getSysPermission(id);
        return success(BeanUtils.toBean(syspermission, SysPermissionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得按钮菜单分页")
    @PreAuthorize("@ss.hasPermission('sys:syspermission:query')")
    public CommonResult<PageResult<SysPermissionRespVO>> getSysPermissionPage(@Valid SysPermissionPageReqVO pageReqVO) {
        PageResult<SysPermissionDO> pageResult = sysPermissionService.getSysPermissionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysPermissionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出按钮菜单 Excel")
    @PreAuthorize("@ss.hasPermission('sys:syspermission:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSysPermissionExcel(@Valid SysPermissionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SysPermissionDO> list = sysPermissionService.getSysPermissionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "按钮菜单.xls", "数据", SysPermissionRespVO.class,
                        BeanUtils.toBean(list, SysPermissionRespVO.class));
    }

}