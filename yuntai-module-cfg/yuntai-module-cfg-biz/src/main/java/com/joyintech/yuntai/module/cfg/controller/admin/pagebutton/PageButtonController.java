package com.joyintech.yuntai.module.cfg.controller.admin.pagebutton;

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

import com.joyintech.yuntai.module.cfg.controller.admin.pagebutton.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagebutton.PageButtonDO;
import com.joyintech.yuntai.module.cfg.service.pagebutton.PageButtonService;

@Tag(name = "管理后台 - 页面操作按钮")
@RestController
@RequestMapping("/cfg/page-button")
@Validated
public class PageButtonController {

    @Resource
    private PageButtonService pageButtonService;

    @PostMapping("/create")
    @Operation(summary = "创建页面操作按钮")
    @PreAuthorize("@ss.hasPermission('cfg:page-button:create')")
    public CommonResult<Long> createPageButton(@Valid @RequestBody PageButtonSaveReqVO createReqVO) {
        return success(pageButtonService.createPageButton(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面操作按钮")
    @PreAuthorize("@ss.hasPermission('cfg:page-button:update')")
    public CommonResult<Boolean> updatePageButton(@Valid @RequestBody PageButtonSaveReqVO updateReqVO) {
        pageButtonService.updatePageButton(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除页面操作按钮")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-button:delete')")
    public CommonResult<Boolean> deletePageButton(@RequestParam("id") Long id) {
        pageButtonService.deletePageButton(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面操作按钮")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-button:query')")
    public CommonResult<PageButtonRespVO> getPageButton(@RequestParam("id") Long id) {
        PageButtonDO pageButton = pageButtonService.getPageButton(id);
        return success(BeanUtils.toBean(pageButton, PageButtonRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面操作按钮分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-button:query')")
    public CommonResult<PageResult<PageButtonRespVO>> getPageButtonPage(@Valid PageButtonPageReqVO pageReqVO) {
        PageResult<PageButtonDO> pageResult = pageButtonService.getPageButtonPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PageButtonRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面操作按钮 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-button:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageButtonExcel(@Valid PageButtonPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageButtonDO> list = pageButtonService.getPageButtonPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面操作按钮.xls", "数据", PageButtonRespVO.class,
                        BeanUtils.toBean(list, PageButtonRespVO.class));
    }

}