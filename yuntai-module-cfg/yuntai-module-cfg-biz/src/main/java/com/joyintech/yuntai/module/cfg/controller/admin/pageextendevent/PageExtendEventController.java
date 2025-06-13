package com.joyintech.yuntai.module.cfg.controller.admin.pageextendevent;

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

import com.joyintech.yuntai.module.cfg.controller.admin.pageextendevent.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageextendevent.PageExtendEventDO;
import com.joyintech.yuntai.module.cfg.service.pageextendevent.PageExtendEventService;

@Tag(name = "管理后台 - 页面事件扩展配置")
@RestController
@RequestMapping("/cfg/page-extend-event")
@Validated
public class PageExtendEventController {

    @Resource
    private PageExtendEventService pageExtendEventService;

    @PostMapping("/create")
    @Operation(summary = "创建页面事件扩展配置")
    @PreAuthorize("@ss.hasPermission('cfg:page-extend-event:create')")
    public CommonResult<Long> createPageExtendEvent(@Valid @RequestBody PageExtendEventSaveReqVO createReqVO) {
        return success(pageExtendEventService.createPageExtendEvent(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面事件扩展配置")
    @PreAuthorize("@ss.hasPermission('cfg:page-extend-event:update')")
    public CommonResult<Boolean> updatePageExtendEvent(@Valid @RequestBody PageExtendEventSaveReqVO updateReqVO) {
        pageExtendEventService.updatePageExtendEvent(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除页面事件扩展配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-extend-event:delete')")
    public CommonResult<Boolean> deletePageExtendEvent(@RequestParam("id") Long id) {
        pageExtendEventService.deletePageExtendEvent(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面事件扩展配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-extend-event:query')")
    public CommonResult<PageExtendEventRespVO> getPageExtendEvent(@RequestParam("id") Long id) {
        PageExtendEventDO pageExtendEvent = pageExtendEventService.getPageExtendEvent(id);
        return success(BeanUtils.toBean(pageExtendEvent, PageExtendEventRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面事件扩展配置分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-extend-event:query')")
    public CommonResult<PageResult<PageExtendEventRespVO>> getPageExtendEventPage(@Valid PageExtendEventPageReqVO pageReqVO) {
        PageResult<PageExtendEventDO> pageResult = pageExtendEventService.getPageExtendEventPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PageExtendEventRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面事件扩展配置 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-extend-event:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageExtendEventExcel(@Valid PageExtendEventPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageExtendEventDO> list = pageExtendEventService.getPageExtendEventPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面事件扩展配置.xls", "数据", PageExtendEventRespVO.class,
                        BeanUtils.toBean(list, PageExtendEventRespVO.class));
    }

}