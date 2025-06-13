package com.joyintech.yuntai.module.cfg.controller.admin.templategroup;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import com.joyintech.yuntai.module.cfg.controller.admin.templategroup.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templategroup.TemplateGroupDO;
import com.joyintech.yuntai.module.cfg.service.templategroup.TemplateGroupService;

@Tag(name = "管理后台 - 模版分组")
@RestController
@RequestMapping("/cfg/template-group")
@Validated
public class TemplateGroupController {

    @Resource
    private TemplateGroupService templateGroupService;

    @PostMapping("/create")
    @Operation(summary = "创建模版分组")
    @PreAuthorize("@ss.hasPermission('cfg:template-group:create')")
    public CommonResult<Long> createTemplateGroup(@Valid @RequestBody TemplateGroupSaveReqVO createReqVO) {
        return success(templateGroupService.createTemplateGroup(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新模版分组")
    @PreAuthorize("@ss.hasPermission('cfg:template-group:update')")
    public CommonResult<Boolean> updateTemplateGroup(@Valid @RequestBody TemplateGroupSaveReqVO updateReqVO) {
        templateGroupService.updateTemplateGroup(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除模版分组")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:template-group:delete')")
    public CommonResult<Boolean> deleteTemplateGroup(@RequestParam("id") Long id) {
        templateGroupService.deleteTemplateGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得模版分组")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:template-group:query')")
    public CommonResult<TemplateGroupRespVO> getTemplateGroup(@RequestParam("id") Long id) {
        TemplateGroupDO templateGroup = templateGroupService.getTemplateGroup(id);
        return success(BeanUtils.toBean(templateGroup, TemplateGroupRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得模版分组分页")
    @PreAuthorize("@ss.hasPermission('cfg:template-group:query')")
    public CommonResult<PageResult<TemplateGroupRespVO>> getTemplateGroupPage(@Valid TemplateGroupPageReqVO pageReqVO) {
        PageResult<TemplateGroupDO> pageResult = templateGroupService.getTemplateGroupPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TemplateGroupRespVO.class));
    }

    @GetMapping("/getTree")
    @Operation(summary = "树形结构")
    @PreAuthorize("@ss.hasPermission('cfg:component-group:query')")
    public CommonResult<List<TemplateGroupRespVO>> getComponentGroupTree() {
        List<TemplateGroupRespVO> list = templateGroupService.getTree();
        return success(list);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出模版分组 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:template-group:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTemplateGroupExcel(@Valid TemplateGroupPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TemplateGroupDO> list = templateGroupService.getTemplateGroupPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "模版分组.xls", "数据", TemplateGroupRespVO.class,
                        BeanUtils.toBean(list, TemplateGroupRespVO.class));
    }

}
