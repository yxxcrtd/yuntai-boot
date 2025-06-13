package com.joyintech.yuntai.module.cfg.controller.admin.pageformconfig;

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

import com.joyintech.yuntai.module.cfg.controller.admin.pageformconfig.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageformconfig.PageFormConfigDO;
import com.joyintech.yuntai.module.cfg.service.pageformconfig.PageFormConfigService;

@Tag(name = "管理后台 - 表单页配置")
@RestController
@RequestMapping("/cfg/page-form-config")
@Validated
public class PageFormConfigController {

    @Resource
    private PageFormConfigService pageFormConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建表单页配置")
    @PreAuthorize("@ss.hasPermission('cfg:page-form-config:create')")
    public CommonResult<Long> createPageFormConfig(@Valid @RequestBody PageFormConfigSaveReqVO createReqVO) {
        return success(pageFormConfigService.createPageFormConfig(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新表单页配置")
    @PreAuthorize("@ss.hasPermission('cfg:page-form-config:update')")
    public CommonResult<Boolean> updatePageFormConfig(@Valid @RequestBody PageFormConfigSaveReqVO updateReqVO) {
        pageFormConfigService.updatePageFormConfig(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除表单页配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-form-config:delete')")
    public CommonResult<Boolean> deletePageFormConfig(@RequestParam("id") Long id) {
        pageFormConfigService.deletePageFormConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得表单页配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-form-config:query')")
    public CommonResult<PageFormConfigRespVO> getPageFormConfig(@RequestParam("id") Long id) {
        PageFormConfigDO pageFormConfig = pageFormConfigService.getPageFormConfig(id);
        return success(BeanUtils.toBean(pageFormConfig, PageFormConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得表单页配置分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-form-config:query')")
    public CommonResult<PageResult<PageFormConfigRespVO>> getPageFormConfigPage(@Valid PageFormConfigPageReqVO pageReqVO) {
        PageResult<PageFormConfigDO> pageResult = pageFormConfigService.getPageFormConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PageFormConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出表单页配置 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-form-config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageFormConfigExcel(@Valid PageFormConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageFormConfigDO> list = pageFormConfigService.getPageFormConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "表单页配置.xls", "数据", PageFormConfigRespVO.class,
                        BeanUtils.toBean(list, PageFormConfigRespVO.class));
    }

}