package com.joyintech.yuntai.module.cfg.controller.admin.outsystemtable;

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

import com.joyintech.yuntai.module.cfg.controller.admin.outsystemtable.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable.OutSystemTableDO;
import com.joyintech.yuntai.module.cfg.service.outsystemtable.OutSystemTableService;

@Tag(name = "管理后台 - 外部系统关联")
@RestController
@RequestMapping("/cfg/out-system-table")
@Validated
public class OutSystemTableController {

    @Resource
    private OutSystemTableService outSystemTableService;

    @PostMapping("/create")
    @Operation(summary = "创建外部系统关联")
    @PreAuthorize("@ss.hasPermission('cfg:out-system-table:create')")
    public CommonResult<Long> createOutSystemTable(@Valid @RequestBody OutSystemTableSaveReqVO createReqVO) {
        return success(outSystemTableService.createOutSystemTable(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新外部系统关联")
    @PreAuthorize("@ss.hasPermission('cfg:out-system-table:update')")
    public CommonResult<Boolean> updateOutSystemTable(@Valid @RequestBody OutSystemTableSaveReqVO updateReqVO) {
        outSystemTableService.updateOutSystemTable(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除外部系统关联")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:out-system-table:delete')")
    public CommonResult<Boolean> deleteOutSystemTable(@RequestParam("id") Long id) {
        outSystemTableService.deleteOutSystemTable(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得外部系统关联")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:out-system-table:query')")
    public CommonResult<OutSystemTableRespVO> getOutSystemTable(@RequestParam("id") Long id) {
        OutSystemTableDO outSystemTable = outSystemTableService.getOutSystemTable(id);
        return success(BeanUtils.toBean(outSystemTable, OutSystemTableRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得外部系统关联分页")
    @PreAuthorize("@ss.hasPermission('cfg:out-system-table:query')")
    public CommonResult<PageResult<OutSystemTableRespVO>> getOutSystemTablePage(@Valid OutSystemTablePageReqVO pageReqVO) {
        PageResult<OutSystemTableDO> pageResult = outSystemTableService.getOutSystemTablePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OutSystemTableRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出外部系统关联 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:out-system-table:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOutSystemTableExcel(@Valid OutSystemTablePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OutSystemTableDO> list = outSystemTableService.getOutSystemTablePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "外部系统关联.xls", "数据", OutSystemTableRespVO.class,
                        BeanUtils.toBean(list, OutSystemTableRespVO.class));
    }

}