package com.joyintech.yuntai.module.cfg.controller.admin.templatecontent;

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

import com.joyintech.yuntai.module.cfg.controller.admin.templatecontent.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templatecontent.TemplateContentDO;
import com.joyintech.yuntai.module.cfg.service.templatecontent.TemplateContentService;

@Tag(name = "管理后台 - 模版内容")
@RestController
@RequestMapping("/cfg/template-content")
@Validated
public class TemplateContentController {

    @Resource
    private TemplateContentService templateContentService;

    @PostMapping("/create")
    @Operation(summary = "创建模版内容")
    @PreAuthorize("@ss.hasPermission('cfg:template-content:create')")
    public CommonResult<Long> createTemplateContent(@Valid @RequestBody TemplateContentSaveReqVO createReqVO) {
        return success(templateContentService.createTemplateContent(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新模版内容")
    @PreAuthorize("@ss.hasPermission('cfg:template-content:update')")
    public CommonResult<Boolean> updateTemplateContent(@Valid @RequestBody TemplateContentSaveReqVO updateReqVO) {
        templateContentService.updateTemplateContent(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除模版内容")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:template-content:delete')")
    public CommonResult<Boolean> deleteTemplateContent(@RequestParam("id") Long id) {
        templateContentService.deleteTemplateContent(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得模版内容")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:template-content:query')")
    public CommonResult<TemplateContentRespVO> getTemplateContent(@RequestParam("id") Long id) {
        TemplateContentDO templateContent = templateContentService.getTemplateContent(id);
        return success(BeanUtils.toBean(templateContent, TemplateContentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得模版内容分页")
    @PreAuthorize("@ss.hasPermission('cfg:template-content:query')")
    public CommonResult<PageResult<TemplateContentRespVO>> getTemplateContentPage(@Valid TemplateContentPageReqVO pageReqVO) {
        PageResult<TemplateContentDO> pageResult = templateContentService.getTemplateContentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TemplateContentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出模版内容 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:template-content:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTemplateContentExcel(@Valid TemplateContentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TemplateContentDO> list = templateContentService.getTemplateContentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "模版内容.xls", "数据", TemplateContentRespVO.class,
                        BeanUtils.toBean(list, TemplateContentRespVO.class));
    }

}