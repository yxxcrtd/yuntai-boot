package com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage;

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

import com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelinkage.PageLinkageDO;
import com.joyintech.yuntai.module.cfg.service.pagelinkage.PageLinkageService;

@Tag(name = "管理后台 - 页面联动配置")
@RestController
@RequestMapping("/cfg/page-linkage")
@Validated
public class PageLinkageController {

    @Resource
    private PageLinkageService pageLinkageService;

    @PostMapping("/create")
    @Operation(summary = "创建页面联动配置")
    @PreAuthorize("@ss.hasPermission('cfg:page-linkage:create')")
    public CommonResult<Long> createPageLinkage(@Valid @RequestBody PageLinkageSaveReqVO createReqVO) {
        return success(pageLinkageService.createPageLinkage(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面联动配置")
    @PreAuthorize("@ss.hasPermission('cfg:page-linkage:update')")
    public CommonResult<Boolean> updatePageLinkage(@Valid @RequestBody PageLinkageSaveReqVO updateReqVO) {
        pageLinkageService.updatePageLinkage(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除页面联动配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-linkage:delete')")
    public CommonResult<Boolean> deletePageLinkage(@RequestParam("id") Long id) {
        pageLinkageService.deletePageLinkage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面联动配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-linkage:query')")
    public CommonResult<PageLinkageRespVO> getPageLinkage(@RequestParam("id") Long id) {
        PageLinkageDO pageLinkage = pageLinkageService.getPageLinkage(id);
        return success(BeanUtils.toBean(pageLinkage, PageLinkageRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面联动配置分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-linkage:query')")
    public CommonResult<PageResult<PageLinkageRespVO>> getPageLinkagePage(@Valid PageLinkagePageReqVO pageReqVO) {
        PageResult<PageLinkageDO> pageResult = pageLinkageService.getPageLinkagePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PageLinkageRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面联动配置 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-linkage:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageLinkageExcel(@Valid PageLinkagePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageLinkageDO> list = pageLinkageService.getPageLinkagePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面联动配置.xls", "数据", PageLinkageRespVO.class,
                        BeanUtils.toBean(list, PageLinkageRespVO.class));
    }

}