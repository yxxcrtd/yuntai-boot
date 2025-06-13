package com.joyintech.yuntai.module.cfg.controller.admin.pagelistcondition;

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

import com.joyintech.yuntai.module.cfg.controller.admin.pagelistcondition.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistcondition.PageListConditionDO;
import com.joyintech.yuntai.module.cfg.service.pagelistcondition.PageListConditionService;

@Tag(name = "管理后台 - 表单页查询条件（待定）")
@RestController
@RequestMapping("/cfg/page-list-condition")
@Validated
public class PageListConditionController {

    @Resource
    private PageListConditionService pageListConditionService;

    @PostMapping("/create")
    @Operation(summary = "创建表单页查询条件（待定）")
    @PreAuthorize("@ss.hasPermission('cfg:page-list-condition:create')")
    public CommonResult<Long> createPageListCondition(@Valid @RequestBody PageListConditionSaveReqVO createReqVO) {
        return success(pageListConditionService.createPageListCondition(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新表单页查询条件（待定）")
    @PreAuthorize("@ss.hasPermission('cfg:page-list-condition:update')")
    public CommonResult<Boolean> updatePageListCondition(@Valid @RequestBody PageListConditionSaveReqVO updateReqVO) {
        pageListConditionService.updatePageListCondition(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除表单页查询条件（待定）")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-list-condition:delete')")
    public CommonResult<Boolean> deletePageListCondition(@RequestParam("id") Long id) {
        pageListConditionService.deletePageListCondition(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得表单页查询条件（待定）")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-list-condition:query')")
    public CommonResult<PageListConditionRespVO> getPageListCondition(@RequestParam("id") Long id) {
        PageListConditionDO pageListCondition = pageListConditionService.getPageListCondition(id);
        return success(BeanUtils.toBean(pageListCondition, PageListConditionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得表单页查询条件（待定）分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-list-condition:query')")
    public CommonResult<PageResult<PageListConditionRespVO>> getPageListConditionPage(@Valid PageListConditionPageReqVO pageReqVO) {
        PageResult<PageListConditionDO> pageResult = pageListConditionService.getPageListConditionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PageListConditionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出表单页查询条件（待定） Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-list-condition:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageListConditionExcel(@Valid PageListConditionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageListConditionDO> list = pageListConditionService.getPageListConditionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "表单页查询条件（待定）.xls", "数据", PageListConditionRespVO.class,
                        BeanUtils.toBean(list, PageListConditionRespVO.class));
    }

}