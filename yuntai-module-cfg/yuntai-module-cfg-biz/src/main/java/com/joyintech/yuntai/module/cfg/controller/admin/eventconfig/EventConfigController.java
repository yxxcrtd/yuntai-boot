package com.joyintech.yuntai.module.cfg.controller.admin.eventconfig;

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

import com.joyintech.yuntai.module.cfg.controller.admin.eventconfig.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.eventconfig.EventConfigDO;
import com.joyintech.yuntai.module.cfg.service.eventconfig.EventConfigService;

@Tag(name = "管理后台 - 页面事件配置")
@RestController
@RequestMapping("/cfg/event-config")
@Validated
public class EventConfigController {

    @Resource
    private EventConfigService eventConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建页面事件配置")
    @PreAuthorize("@ss.hasPermission('cfg:event-config:create')")
    public CommonResult<Long> createEventConfig(@Valid @RequestBody EventConfigSaveReqVO createReqVO) {
        return success(eventConfigService.createEventConfig(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面事件配置")
    @PreAuthorize("@ss.hasPermission('cfg:event-config:update')")
    public CommonResult<Boolean> updateEventConfig(@Valid @RequestBody EventConfigSaveReqVO updateReqVO) {
        eventConfigService.updateEventConfig(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除页面事件配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:event-config:delete')")
    public CommonResult<Boolean> deleteEventConfig(@RequestParam("id") Long id) {
        eventConfigService.deleteEventConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面事件配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:event-config:query')")
    public CommonResult<EventConfigRespVO> getEventConfig(@RequestParam("id") Long id) {
        EventConfigDO eventConfig = eventConfigService.getEventConfig(id);
        return success(BeanUtils.toBean(eventConfig, EventConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面事件配置分页")
    @PreAuthorize("@ss.hasPermission('cfg:event-config:query')")
    public CommonResult<PageResult<EventConfigRespVO>> getEventConfigPage(@Valid EventConfigPageReqVO pageReqVO) {
        PageResult<EventConfigDO> pageResult = eventConfigService.getEventConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, EventConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面事件配置 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:event-config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEventConfigExcel(@Valid EventConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<EventConfigDO> list = eventConfigService.getEventConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面事件配置.xls", "数据", EventConfigRespVO.class,
                        BeanUtils.toBean(list, EventConfigRespVO.class));
    }

}