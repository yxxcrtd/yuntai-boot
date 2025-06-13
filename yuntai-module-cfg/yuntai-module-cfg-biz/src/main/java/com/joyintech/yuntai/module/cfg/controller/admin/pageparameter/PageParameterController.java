package com.joyintech.yuntai.module.cfg.controller.admin.pageparameter;

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

import com.joyintech.yuntai.module.cfg.controller.admin.pageparameter.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageparameter.PageParameterDO;
import com.joyintech.yuntai.module.cfg.service.pageparameter.PageParameterService;

@Tag(name = "管理后台 - 页面参数")
@RestController
@RequestMapping("/cfg/page-parameter")
@Validated
public class PageParameterController {

    @Resource
    private PageParameterService pageParameterService;

    @PostMapping("/create")
    @Operation(summary = "创建页面参数")
    @PreAuthorize("@ss.hasPermission('cfg:page-parameter:create')")
    public CommonResult<Long> createPageParameter(@Valid @RequestBody PageParameterSaveReqVO createReqVO) {
        return success(pageParameterService.createPageParameter(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面参数")
    @PreAuthorize("@ss.hasPermission('cfg:page-parameter:update')")
    public CommonResult<Boolean> updatePageParameter(@Valid @RequestBody PageParameterSaveReqVO updateReqVO) {
        pageParameterService.updatePageParameter(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除页面参数")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-parameter:delete')")
    public CommonResult<Boolean> deletePageParameter(@RequestParam("id") Long id) {
        pageParameterService.deletePageParameter(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面参数")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-parameter:query')")
    public CommonResult<PageParameterRespVO> getPageParameter(@RequestParam("id") Long id) {
        PageParameterDO pageParameter = pageParameterService.getPageParameter(id);
        return success(BeanUtils.toBean(pageParameter, PageParameterRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面参数分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-parameter:query')")
    public CommonResult<PageResult<PageParameterRespVO>> getPageParameterPage(@Valid PageParameterPageReqVO pageReqVO) {
        PageResult<PageParameterDO> pageResult = pageParameterService.getPageParameterPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PageParameterRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面参数 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-parameter:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageParameterExcel(@Valid PageParameterPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageParameterDO> list = pageParameterService.getPageParameterPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面参数.xls", "数据", PageParameterRespVO.class,
                        BeanUtils.toBean(list, PageParameterRespVO.class));
    }

}