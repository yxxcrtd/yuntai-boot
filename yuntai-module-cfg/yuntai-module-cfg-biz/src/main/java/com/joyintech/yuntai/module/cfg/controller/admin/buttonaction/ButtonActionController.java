package com.joyintech.yuntai.module.cfg.controller.admin.buttonaction;

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

import com.joyintech.yuntai.module.cfg.controller.admin.buttonaction.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.buttonaction.ButtonActionDO;
import com.joyintech.yuntai.module.cfg.service.buttonaction.ButtonActionService;

@Tag(name = "管理后台 - 页面按钮动作")
@RestController
@RequestMapping("/cfg/button-action")
@Validated
public class ButtonActionController {

    @Resource
    private ButtonActionService buttonActionService;

    @PostMapping("/create")
    @Operation(summary = "创建页面按钮动作")
    @PreAuthorize("@ss.hasPermission('cfg:button-action:create')")
    public CommonResult<Long> createButtonAction(@Valid @RequestBody ButtonActionSaveReqVO createReqVO) {
        return success(buttonActionService.createButtonAction(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面按钮动作")
    @PreAuthorize("@ss.hasPermission('cfg:button-action:update')")
    public CommonResult<Boolean> updateButtonAction(@Valid @RequestBody ButtonActionSaveReqVO updateReqVO) {
        buttonActionService.updateButtonAction(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除页面按钮动作")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:button-action:delete')")
    public CommonResult<Boolean> deleteButtonAction(@RequestParam("id") Long id) {
        buttonActionService.deleteButtonAction(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面按钮动作")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:button-action:query')")
    public CommonResult<ButtonActionRespVO> getButtonAction(@RequestParam("id") Long id) {
        ButtonActionDO buttonAction = buttonActionService.getButtonAction(id);
        return success(BeanUtils.toBean(buttonAction, ButtonActionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面按钮动作分页")
    @PreAuthorize("@ss.hasPermission('cfg:button-action:query')")
    public CommonResult<PageResult<ButtonActionRespVO>> getButtonActionPage(@Valid ButtonActionPageReqVO pageReqVO) {
        PageResult<ButtonActionDO> pageResult = buttonActionService.getButtonActionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ButtonActionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面按钮动作 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:button-action:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportButtonActionExcel(@Valid ButtonActionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ButtonActionDO> list = buttonActionService.getButtonActionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面按钮动作.xls", "数据", ButtonActionRespVO.class,
                        BeanUtils.toBean(list, ButtonActionRespVO.class));
    }

}