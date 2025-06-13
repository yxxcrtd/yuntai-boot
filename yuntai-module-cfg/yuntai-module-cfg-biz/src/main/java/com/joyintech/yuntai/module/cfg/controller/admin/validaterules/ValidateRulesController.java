package com.joyintech.yuntai.module.cfg.controller.admin.validaterules;

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

import com.joyintech.yuntai.module.cfg.controller.admin.validaterules.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.validaterules.ValidateRulesDO;
import com.joyintech.yuntai.module.cfg.service.validaterules.ValidateRulesService;

@Tag(name = "管理后台 - 页面校验规则")
@RestController
@RequestMapping("/cfg/validate-rules")
@Validated
public class ValidateRulesController {

    @Resource
    private ValidateRulesService validateRulesService;

    @PostMapping("/create")
    @Operation(summary = "创建页面校验规则")
    @PreAuthorize("@ss.hasPermission('cfg:validate-rules:create')")
    public CommonResult<Long> createValidateRules(@Valid @RequestBody ValidateRulesSaveReqVO createReqVO) {
        return success(validateRulesService.createValidateRules(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面校验规则")
    @PreAuthorize("@ss.hasPermission('cfg:validate-rules:update')")
    public CommonResult<Boolean> updateValidateRules(@Valid @RequestBody ValidateRulesSaveReqVO updateReqVO) {
        validateRulesService.updateValidateRules(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除页面校验规则")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:validate-rules:delete')")
    public CommonResult<Boolean> deleteValidateRules(@RequestParam("id") Long id) {
        validateRulesService.deleteValidateRules(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面校验规则")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:validate-rules:query')")
    public CommonResult<ValidateRulesRespVO> getValidateRules(@RequestParam("id") Long id) {
        ValidateRulesDO validateRules = validateRulesService.getValidateRules(id);
        return success(BeanUtils.toBean(validateRules, ValidateRulesRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面校验规则分页")
    @PreAuthorize("@ss.hasPermission('cfg:validate-rules:query')")
    public CommonResult<PageResult<ValidateRulesRespVO>> getValidateRulesPage(@Valid ValidateRulesPageReqVO pageReqVO) {
        PageResult<ValidateRulesDO> pageResult = validateRulesService.getValidateRulesPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ValidateRulesRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面校验规则 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:validate-rules:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportValidateRulesExcel(@Valid ValidateRulesPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ValidateRulesDO> list = validateRulesService.getValidateRulesPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面校验规则.xls", "数据", ValidateRulesRespVO.class,
                        BeanUtils.toBean(list, ValidateRulesRespVO.class));
    }

}