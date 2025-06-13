package com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable;

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

import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.conditionaltable.ConditionalTableDO;
import com.joyintech.yuntai.module.cfg.service.conditionaltable.ConditionalTableService;

@Tag(name = "管理后台 - 条件")
@RestController
@RequestMapping("/cfg/conditional-table")
@Validated
public class ConditionalTableController {

    @Resource
    private ConditionalTableService conditionalTableService;

    @PostMapping("/create")
    @Operation(summary = "创建条件")
    @PreAuthorize("@ss.hasPermission('cfg:conditional-table:create')")
    public CommonResult<Long> createConditionalTable(@Valid @RequestBody ConditionalTableSaveReqVO createReqVO) {
        return success(conditionalTableService.createConditionalTable(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新条件")
    @PreAuthorize("@ss.hasPermission('cfg:conditional-table:update')")
    public CommonResult<Boolean> updateConditionalTable(@Valid @RequestBody ConditionalTableSaveReqVO updateReqVO) {
        conditionalTableService.updateConditionalTable(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除条件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:conditional-table:delete')")
    public CommonResult<Boolean> deleteConditionalTable(@RequestParam("id") Long id) {
        conditionalTableService.deleteConditionalTable(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得条件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:conditional-table:query')")
    public CommonResult<ConditionalTableRespVO> getConditionalTable(@RequestParam("id") Long id) {
        ConditionalTableDO conditionalTable = conditionalTableService.getConditionalTable(id);
        return success(BeanUtils.toBean(conditionalTable, ConditionalTableRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得条件分页")
    @PreAuthorize("@ss.hasPermission('cfg:conditional-table:query')")
    public CommonResult<PageResult<ConditionalTableRespVO>> getConditionalTablePage(@Valid ConditionalTablePageReqVO pageReqVO) {
        PageResult<ConditionalTableDO> pageResult = conditionalTableService.getConditionalTablePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ConditionalTableRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出条件 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:conditional-table:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportConditionalTableExcel(@Valid ConditionalTablePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ConditionalTableDO> list = conditionalTableService.getConditionalTablePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "条件.xls", "数据", ConditionalTableRespVO.class,
                        BeanUtils.toBean(list, ConditionalTableRespVO.class));
    }

}