package com.joyintech.yuntai.module.cfg.controller.admin.pageapi;

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

import com.joyintech.yuntai.module.cfg.controller.admin.pageapi.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageapi.PageApiDO;
import com.joyintech.yuntai.module.cfg.service.pageapi.PageApiService;

@Tag(name = "管理后台 - 页面api")
@RestController
@RequestMapping("/cfg/page-api")
@Validated
public class PageApiController {

    @Resource
    private PageApiService pageApiService;

    @PostMapping("/create")
    @Operation(summary = "创建页面api")
    @PreAuthorize("@ss.hasPermission('cfg:page-api:create')")
    public CommonResult<Long> createPageApi(@Valid @RequestBody PageApiSaveReqVO createReqVO) {
        return success(pageApiService.createPageApi(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面api")
    @PreAuthorize("@ss.hasPermission('cfg:page-api:update')")
    public CommonResult<Boolean> updatePageApi(@Valid @RequestBody PageApiSaveReqVO updateReqVO) {
        pageApiService.updatePageApi(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除页面api")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-api:delete')")
    public CommonResult<Boolean> deletePageApi(@RequestParam("id") Long id) {
        pageApiService.deletePageApi(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面api")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-api:query')")
    public CommonResult<PageApiRespVO> getPageApi(@RequestParam("id") Long id) {
        PageApiDO pageApi = pageApiService.getPageApi(id);
        return success(BeanUtils.toBean(pageApi, PageApiRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面api分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-api:query')")
    public CommonResult<PageResult<PageApiRespVO>> getPageApiPage(@Valid PageApiPageReqVO pageReqVO) {
        PageResult<PageApiDO> pageResult = pageApiService.getPageApiPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PageApiRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面api Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-api:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageApiExcel(@Valid PageApiPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageApiDO> list = pageApiService.getPageApiPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面api.xls", "数据", PageApiRespVO.class,
                        BeanUtils.toBean(list, PageApiRespVO.class));
    }

}