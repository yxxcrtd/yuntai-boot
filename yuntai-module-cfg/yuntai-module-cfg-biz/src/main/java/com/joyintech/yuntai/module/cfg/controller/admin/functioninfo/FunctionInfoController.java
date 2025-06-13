package com.joyintech.yuntai.module.cfg.controller.admin.functioninfo;

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

import com.joyintech.yuntai.module.cfg.controller.admin.functioninfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.functioninfo.FunctionInfoDO;
import com.joyintech.yuntai.module.cfg.service.functioninfo.FunctionInfoService;

@Tag(name = "管理后台 - 开发平台功能管理")
@RestController
@RequestMapping("/cfg/function-info")
@Validated
public class FunctionInfoController {

    @Resource
    private FunctionInfoService functionInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建开发平台功能管理")
    @PreAuthorize("@ss.hasPermission('cfg:function-info:create')")
    public CommonResult<Long> createFunctionInfo(@Valid @RequestBody FunctionInfoSaveReqVO createReqVO) {
        return success(functionInfoService.createFunctionInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新开发平台功能管理")
    @PreAuthorize("@ss.hasPermission('cfg:function-info:update')")
    public CommonResult<Boolean> updateFunctionInfo(@Valid @RequestBody FunctionInfoSaveReqVO updateReqVO) {
        functionInfoService.updateFunctionInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除开发平台功能管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:function-info:delete')")
    public CommonResult<Boolean> deleteFunctionInfo(@RequestParam("id") Long id) {
        functionInfoService.deleteFunctionInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得开发平台功能管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:function-info:query')")
    public CommonResult<FunctionInfoRespVO> getFunctionInfo(@RequestParam("id") Long id) {
        FunctionInfoDO functionInfo = functionInfoService.getFunctionInfo(id);
        return success(BeanUtils.toBean(functionInfo, FunctionInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得开发平台功能管理分页")
    @PreAuthorize("@ss.hasPermission('cfg:function-info:query')")
    public CommonResult<PageResult<FunctionInfoRespVO>> getFunctionInfoPage(@Valid FunctionInfoPageReqVO pageReqVO) {
        PageResult<FunctionInfoDO> pageResult = functionInfoService.getFunctionInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, FunctionInfoRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得开发平台功能管理列表")
    @PreAuthorize("@ss.hasPermission('cfg:function-info:query')")
    public CommonResult<List<FunctionInfoRespVO>> getFunctionInfoList(@Valid FunctionInfoPageReqVO pageReqVO) {
        List<FunctionInfoDO> pageResult = functionInfoService.getFunctionInfoList(pageReqVO);
        return success(BeanUtils.toBean(pageResult, FunctionInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出开发平台功能管理 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:function-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportFunctionInfoExcel(@Valid FunctionInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<FunctionInfoDO> list = functionInfoService.getFunctionInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "开发平台功能管理.xls", "数据", FunctionInfoRespVO.class,
                        BeanUtils.toBean(list, FunctionInfoRespVO.class));
    }

}