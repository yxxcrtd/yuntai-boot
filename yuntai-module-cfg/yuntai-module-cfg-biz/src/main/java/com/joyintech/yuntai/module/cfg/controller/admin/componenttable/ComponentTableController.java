package com.joyintech.yuntai.module.cfg.controller.admin.componenttable;

import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.ComponentAttributeRespVO;
import com.joyintech.yuntai.module.cfg.service.componentattribute.ComponentAttributeService;
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
import static org.bouncycastle.asn1.cmc.CMCStatus.success;

import com.joyintech.yuntai.module.cfg.controller.admin.componenttable.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componenttable.ComponentTableDO;
import com.joyintech.yuntai.module.cfg.service.componenttable.ComponentTableService;

@Tag(name = "管理后台 - 组件")
@RestController
@RequestMapping("/cfg/component-table")
@Validated
public class ComponentTableController {

    @Resource
    private ComponentTableService componentTableService;

    @Resource
    private ComponentAttributeService componentAttributeService;

    @PostMapping("/create")
    @Operation(summary = "创建组件")
    @PreAuthorize("@ss.hasPermission('cfg:component-table:create')")
    public CommonResult<Long> createComponentTable(@Valid @RequestBody ComponentTableSaveReqVO createReqVO) {
        return success(componentTableService.createComponentTable(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新组件")
    @PreAuthorize("@ss.hasPermission('cfg:component-table:update')")
    public CommonResult<Boolean> updateComponentTable(@Valid @RequestBody ComponentTableSaveReqVO updateReqVO) {
        componentTableService.updateComponentTable(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除组件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:component-table:delete')")
    public CommonResult<Boolean> deleteComponentTable(@RequestParam("id") Long id) {
        componentTableService.deleteComponentTable(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得组件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:component-table:query')")
    public CommonResult<ComponentTableRespVO> getComponentTable(@RequestParam("id") Long id) {
        ComponentTableDO componentTable = componentTableService.getComponentTable(id);
        List<ComponentAttributeRespVO> componentAttributeRespVOS = componentAttributeService.selectComponent(id);
        ComponentTableRespVO bean = BeanUtils.toBean(componentTable, ComponentTableRespVO.class);
        bean.setComponentAttributeList(componentAttributeRespVOS);
        return success(bean);
    }

    @GetMapping("/getBycomponentCode")
    @Operation(summary = "获得组件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:component-table:query')")
    public CommonResult<ComponentTableRespVO> getComponentTableByComponentCode(@RequestParam("componentCode") String componentCode) {
        ComponentTableDO componentTable = componentTableService.getComponentTableByCode(componentCode);
        List<ComponentAttributeRespVO> componentAttributeRespVOS = componentAttributeService.selectComponent(componentTable.getId());
        ComponentTableRespVO bean = BeanUtils.toBean(componentTable, ComponentTableRespVO.class);
        bean.setComponentAttributeList(componentAttributeRespVOS);
        return success(bean);
    }

    @GetMapping("/page")
    @Operation(summary = "获得组件分页")
    @PreAuthorize("@ss.hasPermission('cfg:component-table:query')")
    public CommonResult<PageResult<ComponentTableRespVO>> getComponentTablePage(@Valid ComponentTablePageReqVO pageReqVO) {
        PageResult<ComponentTableDO> pageResult = componentTableService.getComponentTablePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ComponentTableRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "根据分组ID获取组件分页")
    @PreAuthorize("@ss.hasPermission('cfg:component-table:query')")
    public CommonResult<PageResult<ComponentTableRespVO>> getComponentTableList(@Valid ComponentTablePageReqVO pageReqVO) {
        PageResult<ComponentTableDO> pageResult = componentTableService.getComponentTablePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ComponentTableRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出组件 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:component-table:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportComponentTableExcel(@Valid ComponentTablePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ComponentTableDO> list = componentTableService.getComponentTablePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "组件.xls", "数据", ComponentTableRespVO.class,
                        BeanUtils.toBean(list, ComponentTableRespVO.class));
    }

}
