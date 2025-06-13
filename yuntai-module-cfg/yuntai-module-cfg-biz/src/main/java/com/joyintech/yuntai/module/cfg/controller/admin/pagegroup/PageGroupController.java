package com.joyintech.yuntai.module.cfg.controller.admin.pagegroup;

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

import com.joyintech.yuntai.module.cfg.controller.admin.pagegroup.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagegroup.PageGroupDO;
import com.joyintech.yuntai.module.cfg.service.pagegroup.PageGroupService;

@Tag(name = "管理后台 - 页面分组")
@RestController
@RequestMapping("/cfg/page-group")
@Validated
public class PageGroupController {

    @Resource
    private PageGroupService pageGroupService;

    @PostMapping("/create")
    @Operation(summary = "创建页面分组")
    @PreAuthorize("@ss.hasPermission('cfg:page-group:create')")
    public CommonResult<Long> createPageGroup(@Valid @RequestBody PageGroupSaveReqVO createReqVO) {
        return success(pageGroupService.createPageGroup(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面分组")
    @PreAuthorize("@ss.hasPermission('cfg:page-group:update')")
    public CommonResult<Boolean> updatePageGroup(@Valid @RequestBody PageGroupSaveReqVO updateReqVO) {
        pageGroupService.updatePageGroup(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除页面分组")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-group:delete')")
    public CommonResult<Boolean> deletePageGroup(@RequestParam("id") Long id) {
        pageGroupService.deletePageGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面分组")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-group:query')")
    public CommonResult<PageGroupRespVO> getPageGroup(@RequestParam("id") Long id) {
        PageGroupDO pageGroup = pageGroupService.getPageGroup(id);
        return success(BeanUtils.toBean(pageGroup, PageGroupRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面分组分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-group:query')")
    public CommonResult<PageResult<PageGroupRespVO>> getPageGroupPage(@Valid PageGroupPageReqVO pageReqVO) {
        PageResult<PageGroupDO> pageResult = pageGroupService.getPageGroupPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PageGroupRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面分组 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-group:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageGroupExcel(@Valid PageGroupPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageGroupDO> list = pageGroupService.getPageGroupPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面分组.xls", "数据", PageGroupRespVO.class,
                        BeanUtils.toBean(list, PageGroupRespVO.class));
    }

}