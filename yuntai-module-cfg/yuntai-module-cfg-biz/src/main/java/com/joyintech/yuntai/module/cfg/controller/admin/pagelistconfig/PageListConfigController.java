package com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig;

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

import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistconfig.PageListConfigDO;
import com.joyintech.yuntai.module.cfg.service.pagelistconfig.PageListConfigService;

@Tag(name = "管理后台 - 列表页配置")
@RestController
@RequestMapping("/cfg/page-list-config")
@Validated
public class PageListConfigController {

    @Resource
    private PageListConfigService pageListConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建列表页配置")
    @PreAuthorize("@ss.hasPermission('cfg:page-list-config:create')")
    public CommonResult<Long> createPageListConfig(@Valid @RequestBody PageListConfigSaveReqVO createReqVO) {
        return success(pageListConfigService.createPageListConfig(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新列表页配置")
    @PreAuthorize("@ss.hasPermission('cfg:page-list-config:update')")
    public CommonResult<Boolean> updatePageListConfig(@Valid @RequestBody PageListConfigSaveReqVO updateReqVO) {
        pageListConfigService.updatePageListConfig(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除列表页配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-list-config:delete')")
    public CommonResult<Boolean> deletePageListConfig(@RequestParam("id") Long id) {
        pageListConfigService.deletePageListConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得列表页配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-list-config:query')")
    public CommonResult<PageListConfigRespVO> getPageListConfig(@RequestParam("id") Long id) {
        PageListConfigDO pageListConfig = pageListConfigService.getPageListConfig(id);
        return success(BeanUtils.toBean(pageListConfig, PageListConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得列表页配置分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-list-config:query')")
    public CommonResult<PageResult<PageListConfigRespVO>> getPageListConfigPage(@Valid PageListConfigPageReqVO pageReqVO) {
        PageResult<PageListConfigDO> pageResult = pageListConfigService.getPageListConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PageListConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出列表页配置 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-list-config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageListConfigExcel(@Valid PageListConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageListConfigDO> list = pageListConfigService.getPageListConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "列表页配置.xls", "数据", PageListConfigRespVO.class,
                        BeanUtils.toBean(list, PageListConfigRespVO.class));
    }

}