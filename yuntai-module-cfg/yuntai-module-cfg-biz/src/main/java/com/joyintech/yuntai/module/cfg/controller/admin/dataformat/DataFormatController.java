package com.joyintech.yuntai.module.cfg.controller.admin.dataformat;

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

import com.joyintech.yuntai.module.cfg.controller.admin.dataformat.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataformat.DataFormatDO;
import com.joyintech.yuntai.module.cfg.service.dataformat.DataFormatService;

@Tag(name = "管理后台 - 页面数据格式化")
@RestController
@RequestMapping("/cfg/data-format")
@Validated
public class DataFormatController {

    @Resource
    private DataFormatService dataFormatService;

    @PostMapping("/create")
    @Operation(summary = "创建页面数据格式化")
    @PreAuthorize("@ss.hasPermission('cfg:data-format:create')")
    public CommonResult<Long> createDataFormat(@Valid @RequestBody DataFormatSaveReqVO createReqVO) {
        return success(dataFormatService.createDataFormat(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面数据格式化")
    @PreAuthorize("@ss.hasPermission('cfg:data-format:update')")
    public CommonResult<Boolean> updateDataFormat(@Valid @RequestBody DataFormatSaveReqVO updateReqVO) {
        dataFormatService.updateDataFormat(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除页面数据格式化")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:data-format:delete')")
    public CommonResult<Boolean> deleteDataFormat(@RequestParam("id") Long id) {
        dataFormatService.deleteDataFormat(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面数据格式化")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:data-format:query')")
    public CommonResult<DataFormatRespVO> getDataFormat(@RequestParam("id") Long id) {
        DataFormatDO dataFormat = dataFormatService.getDataFormat(id);
        return success(BeanUtils.toBean(dataFormat, DataFormatRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面数据格式化分页")
    @PreAuthorize("@ss.hasPermission('cfg:data-format:query')")
    public CommonResult<PageResult<DataFormatRespVO>> getDataFormatPage(@Valid DataFormatPageReqVO pageReqVO) {
        PageResult<DataFormatDO> pageResult = dataFormatService.getDataFormatPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DataFormatRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面数据格式化 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:data-format:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDataFormatExcel(@Valid DataFormatPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DataFormatDO> list = dataFormatService.getDataFormatPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面数据格式化.xls", "数据", DataFormatRespVO.class,
                        BeanUtils.toBean(list, DataFormatRespVO.class));
    }

}