package com.joyintech.yuntai.module.cfg.controller.admin.componentattribute;

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

import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentattribute.ComponentAttributeDO;
import com.joyintech.yuntai.module.cfg.service.componentattribute.ComponentAttributeService;

@Tag(name = "管理后台 - 组件属性")
@RestController
@RequestMapping("/cfg/component-attribute")
@Validated
public class ComponentAttributeController {

    @Resource
    private ComponentAttributeService componentAttributeService;

    @PostMapping("/create")
    @Operation(summary = "创建组件属性")
    @PreAuthorize("@ss.hasPermission('cfg:component-attribute:create')")
    public CommonResult<Long> createComponentAttribute(@Valid @RequestBody ComponentAttributeSaveReqVO createReqVO) {
        return success(componentAttributeService.createComponentAttribute(createReqVO));
    }

    @PostMapping("/createList")
    @Operation(summary = "批量创建组件属性")
    @PreAuthorize("@ss.hasPermission('cfg:component-attribute:create')")
    public CommonResult<?> createComponentAttributeList(@Valid List<ComponentAttributeSaveReqVO> createReqVO) {
        return success(componentAttributeService.createComponentAttributeList(createReqVO));
    }



    @PostMapping("/update")
    @Operation(summary = "更新组件属性")
    @PreAuthorize("@ss.hasPermission('cfg:component-attribute:update')")
    public CommonResult<Boolean> updateComponentAttribute(@Valid @RequestBody ComponentAttributeSaveReqVO updateReqVO) {
        componentAttributeService.updateComponentAttribute(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除组件属性")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:component-attribute:delete')")
    public CommonResult<Boolean> deleteComponentAttribute(@RequestParam("id") Long id) {
        componentAttributeService.deleteComponentAttribute(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得组件属性")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:component-attribute:query')")
    public CommonResult<ComponentAttributeRespVO> getComponentAttribute(@RequestParam("id") Long id) {
        ComponentAttributeDO componentAttribute = componentAttributeService.getComponentAttribute(id);
        return success(BeanUtils.toBean(componentAttribute, ComponentAttributeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得组件属性分页")
    @PreAuthorize("@ss.hasPermission('cfg:component-attribute:query')")
    public CommonResult<PageResult<ComponentAttributeRespVO>> getComponentAttributePage(@Valid ComponentAttributePageReqVO pageReqVO) {
        PageResult<ComponentAttributeDO> pageResult = componentAttributeService.getComponentAttributePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ComponentAttributeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出组件属性 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:component-attribute:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportComponentAttributeExcel(@Valid ComponentAttributePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ComponentAttributeDO> list = componentAttributeService.getComponentAttributePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "组件属性.xls", "数据", ComponentAttributeRespVO.class,
                        BeanUtils.toBean(list, ComponentAttributeRespVO.class));
    }

}