package com.joyintech.yuntai.module.cfg.controller.admin.dataconversion;

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

import com.joyintech.yuntai.module.cfg.controller.admin.dataconversion.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataconversion.DataConversionDO;
import com.joyintech.yuntai.module.cfg.service.dataconversion.DataConversionService;

@Tag(name = "管理后台 - 数据转换")
@RestController
@RequestMapping("/cfg/data-conversion")
@Validated
public class DataConversionController {

    @Resource
    private DataConversionService dataConversionService;

    @PostMapping("/create")
    @Operation(summary = "创建数据转换")
    @PreAuthorize("@ss.hasPermission('cfg:data-conversion:create')")
    public CommonResult<Long> createDataConversion(@Valid @RequestBody DataConversionSaveReqVO createReqVO) {
        return success(dataConversionService.createDataConversion(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新数据转换")
    @PreAuthorize("@ss.hasPermission('cfg:data-conversion:update')")
    public CommonResult<Boolean> updateDataConversion(@Valid @RequestBody DataConversionSaveReqVO updateReqVO) {
        dataConversionService.updateDataConversion(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除数据转换")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:data-conversion:delete')")
    public CommonResult<Boolean> deleteDataConversion(@RequestParam("id") Long id) {
        dataConversionService.deleteDataConversion(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得数据转换")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:data-conversion:query')")
    public CommonResult<DataConversionRespVO> getDataConversion(@RequestParam("id") Long id) {
        DataConversionDO dataConversion = dataConversionService.getDataConversion(id);
        return success(BeanUtils.toBean(dataConversion, DataConversionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得数据转换分页")
    @PreAuthorize("@ss.hasPermission('cfg:data-conversion:query')")
    public CommonResult<PageResult<DataConversionRespVO>> getDataConversionPage(@Valid DataConversionPageReqVO pageReqVO) {
        PageResult<DataConversionDO> pageResult = dataConversionService.getDataConversionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DataConversionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出数据转换 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:data-conversion:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDataConversionExcel(@Valid DataConversionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DataConversionDO> list = dataConversionService.getDataConversionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "数据转换.xls", "数据", DataConversionRespVO.class,
                        BeanUtils.toBean(list, DataConversionRespVO.class));
    }

}