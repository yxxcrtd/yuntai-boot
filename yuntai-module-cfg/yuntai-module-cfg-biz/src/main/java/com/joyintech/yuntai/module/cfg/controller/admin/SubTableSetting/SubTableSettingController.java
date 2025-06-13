package com.joyintech.yuntai.module.cfg.controller.admin.SubTableSetting;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
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

import com.joyintech.yuntai.module.cfg.controller.admin.SubTableSetting.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.SubTableSetting.SubTableSettingDO;
import com.joyintech.yuntai.module.cfg.service.SubTableSetting.SubTableSettingService;

@Tag(name = "管理后台 - 子表设置")
@RestController
@RequestMapping("/cfg/sub-table-setting")
@Validated
public class SubTableSettingController {

    @Resource
    private SubTableSettingService subTableSettingService;

    @PostMapping("/create")
    @Operation(summary = "创建子表设置")
    @PreAuthorize("@ss.hasPermission('cfg:sub-table-setting:create')")
    public CommonResult<Long> createSubTableSetting(@Valid @RequestBody SubTableSettingSaveReqVO createReqVO) {
        return success(subTableSettingService.createSubTableSetting(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新子表设置")
    @PreAuthorize("@ss.hasPermission('cfg:sub-table-setting:update')")
    public CommonResult<Boolean> updateSubTableSetting(@Valid @RequestBody SubTableSettingSaveReqVO updateReqVO) {
        subTableSettingService.updateSubTableSetting(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除子表设置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:sub-table-setting:delete')")
    public CommonResult<Boolean> deleteSubTableSetting(@RequestParam("id") Long id) {
        subTableSettingService.deleteSubTableSetting(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得子表设置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:sub-table-setting:query')")
    public CommonResult<SubTableSettingRespVO> getSubTableSetting(@RequestParam("id") Long id) {
        SubTableSettingDO subTableSetting = subTableSettingService.getSubTableSetting(id);
        return success(BeanUtils.toBean(subTableSetting, SubTableSettingRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得子表设置分页")
    @PreAuthorize("@ss.hasPermission('cfg:sub-table-setting:query')")
    public CommonResult<PageResult<SubTableSettingRespVO>> getSubTableSettingPage(@Valid SubTableSettingPageReqVO pageReqVO) {
        PageResult<SubTableSettingDO> pageResult = subTableSettingService.getSubTableSettingPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SubTableSettingRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出子表设置 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:sub-table-setting:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSubTableSettingExcel(@Valid SubTableSettingPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SubTableSettingDO> list = subTableSettingService.getSubTableSettingPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "子表设置.xls", "数据", SubTableSettingRespVO.class,
                        BeanUtils.toBean(list, SubTableSettingRespVO.class));
    }

}