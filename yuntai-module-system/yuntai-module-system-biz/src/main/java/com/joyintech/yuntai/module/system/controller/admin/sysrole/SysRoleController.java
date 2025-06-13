package com.joyintech.yuntai.module.system.controller.admin.sysrole;

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

import com.joyintech.yuntai.module.system.controller.admin.sysrole.vo.SysRolePageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.sysrole.vo.SysRoleRespVO;
import com.joyintech.yuntai.module.system.controller.admin.sysrole.vo.SysRoleSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.sysrole.SysRoleDO;
import com.joyintech.yuntai.module.system.service.sysrole.SysRoleService;
import com.joyintech.yuntai.module.system.sysuser.model.SysUser;

@Tag(name = "管理后台 - 角色")
@RestController
@RequestMapping("/sys/sysrole")
@Validated
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @PostMapping("/create")
    @Operation(summary = "创建角色")
    @PreAuthorize("@ss.hasPermission('sys:sysrole:create')")
    public CommonResult<String> createSysRole(@Valid @RequestBody SysRoleSaveReqVO createReqVO) {
        return success(sysRoleService.createSysRole(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新角色")
    @PreAuthorize("@ss.hasPermission('sys:sysrole:update')")
    public CommonResult<Boolean> updateSysRole(@Valid @RequestBody SysRoleSaveReqVO updateReqVO) {
        sysRoleService.updateSysRole(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除角色")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sys:sysrole:delete')")
    public CommonResult<Boolean> deleteSysRole(@RequestParam("id") String id) {
        sysRoleService.deleteSysRole(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得角色")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sys:sysrole:query')")
    public CommonResult<SysRoleRespVO> getSysRole(@RequestParam("id") String id) {
        SysRoleDO role = sysRoleService.getSysRole(id);
        return success(BeanUtils.toBean(role, SysRoleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得角色分页")
    @PreAuthorize("@ss.hasPermission('sys:sysrole:query')")
    public CommonResult<PageResult<SysRoleRespVO>> getSysRolePage(@Valid SysRolePageReqVO pageReqVO) {
        PageResult<SysRoleDO> pageResult = sysRoleService.getSysRolePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysRoleRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出角色 Excel")
    @PreAuthorize("@ss.hasPermission('sys:sysrole:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSysRoleExcel(@Valid SysRolePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SysRoleDO> list = sysRoleService.getSysRolePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "角色.xls", "数据", SysRoleRespVO.class,
                        BeanUtils.toBean(list, SysRoleRespVO.class));
    }

    @GetMapping("/getUserList")
    public CommonResult<List<SysUser>> getUserList(@RequestParam("idList") String idList) {
        // 根据角色（多个）获取用户
        List<String> ids= Arrays.asList(idList.split(","));
        List<SysUser> userList = sysRoleService.getUsers(ids);
        return success(userList);
    }

}