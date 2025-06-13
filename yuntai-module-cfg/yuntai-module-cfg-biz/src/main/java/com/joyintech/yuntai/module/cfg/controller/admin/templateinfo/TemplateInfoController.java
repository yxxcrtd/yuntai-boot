package com.joyintech.yuntai.module.cfg.controller.admin.templateinfo;

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

import com.joyintech.yuntai.module.cfg.controller.admin.templateinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templateinfo.TemplateInfoDO;
import com.joyintech.yuntai.module.cfg.service.templateinfo.TemplateInfoService;

@Tag(name = "管理后台 - 模版")
@RestController
@RequestMapping("/cfg/template-info")
@Validated
public class TemplateInfoController {

    @Resource
    private TemplateInfoService templateInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建模版")
    @PreAuthorize("@ss.hasPermission('cfg:template-info:create')")
    public CommonResult<Long> createTemplateInfo(@Valid @RequestBody TemplateInfoSaveReqVO createReqVO) {
        return success(templateInfoService.createTemplateInfo(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新模版")
    @PreAuthorize("@ss.hasPermission('cfg:template-info:update')")
    public CommonResult<Boolean> updateTemplateInfo(@Valid @RequestBody TemplateInfoSaveReqVO updateReqVO) {
        templateInfoService.updateTemplateInfo(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除模版")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:template-info:delete')")
    public CommonResult<Boolean> deleteTemplateInfo(@RequestParam("id") Long id) {
        templateInfoService.deleteTemplateInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得模版")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:template-info:query')")
    public CommonResult<TemplateInfoRespVO> getTemplateInfo(@RequestParam("id") Long id) {
        TemplateInfoRespVO templateInfo = templateInfoService.getTemplateInfo(id);
        return success(templateInfo);
    }

    @GetMapping("/page")
    @Operation(summary = "获得模版分页")
    @PreAuthorize("@ss.hasPermission('cfg:template-info:query')")
    public CommonResult<PageResult<TemplateInfoRespVO>> getTemplateInfoPage(@Valid TemplateInfoPageReqVO pageReqVO) {
        PageResult<TemplateInfoDO> pageResult = templateInfoService.getTemplateInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TemplateInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出模版 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:template-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTemplateInfoExcel(@Valid TemplateInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TemplateInfoDO> list = templateInfoService.getTemplateInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "模版.xls", "数据", TemplateInfoRespVO.class,
                        BeanUtils.toBean(list, TemplateInfoRespVO.class));
    }

}
