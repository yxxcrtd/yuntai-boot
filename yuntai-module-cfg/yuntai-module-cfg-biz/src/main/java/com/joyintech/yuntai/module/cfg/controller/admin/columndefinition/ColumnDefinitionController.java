package com.joyintech.yuntai.module.cfg.controller.admin.columndefinition;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.joyintech.yuntai.framework.apilog.core.annotation.ApiAccessLog;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.framework.excel.core.util.ExcelUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnDefinitionRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnDefinitionTempVO;
import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnTypeVO;
import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.ComponentAttributeSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition.ColumnDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componenttable.ComponentTableDO;
import com.joyintech.yuntai.module.cfg.service.columndefinition.ColumnDefinitionService;
import com.joyintech.yuntai.module.cfg.service.componenttable.ComponentTableService;
import com.joyintech.yuntai.module.cfg.service.dbtypeconfig.DbTypeConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 字段定义")
@RestController
@RequestMapping("/cfg/column-definition")
@Validated
public class ColumnDefinitionController {

    @Resource
    private ColumnDefinitionService columnDefinitionService;
    @Resource
    private ComponentTableService componentTableService;

    @Resource
    private DbTypeConfigService dbTypeConfigService;

    @PostMapping("/save")
    @Operation(summary = "保存字段定义")
    @PreAuthorize("@ss.hasPermission('cfg:column-definition:save')")
    public CommonResult<Boolean> createColumnDefinition(@RequestBody ColumnDefinitionTempVO data) {
        List<ColumnDefinitionSaveReqVO> createReqVOs = data.getCreateReqVOs();
        if (createReqVOs.isEmpty()) {
            return success(true);
        }
        columnDefinitionService.saveColumnDefinition(createReqVOs, false, data.getTableSql());
        return success(true);
    }

    @PostMapping("/temp-save")
    @Operation(summary = "保存字段定义")
    @PreAuthorize("@ss.hasPermission('cfg:column-definition:save')")
    public CommonResult<Boolean> createTempColumnDefinition(@RequestBody ColumnDefinitionTempVO data) {
        List<ColumnDefinitionSaveReqVO> createReqVOs = data.getCreateReqVOs();
        if (createReqVOs.isEmpty()) {
            return success(true);
        }
        columnDefinitionService.saveColumnDefinition(createReqVOs, true, data.getTableSql());
        return success(true);
    }

    @PostMapping("/create")
    @Operation(summary = "创建字段定义")
    @PreAuthorize("@ss.hasPermission('cfg:column-definition:create')")
    public CommonResult<Long> createColumnDefinition(@Valid @RequestBody ColumnDefinitionSaveReqVO createReqVO) {
        return success(columnDefinitionService.createColumnDefinition(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新字段定义")
    @PreAuthorize("@ss.hasPermission('cfg:column-definition:update')")
    public CommonResult<Boolean> updateColumnDefinition(@Valid @RequestBody ColumnDefinitionSaveReqVO updateReqVO) {
        columnDefinitionService.updateColumnDefinition(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除字段定义")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:column-definition:delete')")
    public CommonResult<Boolean> deleteColumnDefinition(@RequestParam("id") Long id) {
        columnDefinitionService.deleteColumnDefinition(id);
        return success(true);
    }

    @GetMapping("/reload-table")
    @Operation(summary = "重新加载表结构")
    @Parameter(name = "tableId", description = "表id", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:column-definition:query')")
    public CommonResult<String> reloadTable(@RequestParam("tableId") Long tableId) {
        columnDefinitionService.reloadTable(tableId);
        return success("加载成功");
    }

    @GetMapping("/get")
    @Operation(summary = "获得字段定义")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:column-definition:query')")
    public CommonResult<ColumnDefinitionRespVO> getColumnDefinition(@RequestParam("id") Long id) {
        ColumnDefinitionDO columnDefinition = columnDefinitionService.getColumnDefinition(id);
        return success(BeanUtils.toBean(columnDefinition, ColumnDefinitionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得字段定义分页")
    @PreAuthorize("@ss.hasPermission('cfg:column-definition:query')")
    public CommonResult<PageResult<ColumnDefinitionRespVO>> getColumnDefinitionPage(
            @Valid ColumnDefinitionPageReqVO pageReqVO) {
        PageResult<ColumnDefinitionDO> pageResult = columnDefinitionService.getColumnDefinitionPage(pageReqVO);
        if (CollUtil.isNotEmpty(pageResult.getList())) {
            PageResult<ColumnDefinitionRespVO> temp = BeanUtils.toBean(pageResult, ColumnDefinitionRespVO.class);
            Set<Long> ids = pageResult.getList().stream().map(ColumnDefinitionDO::getId).collect(Collectors.toSet());
            List<ComponentAttributeSaveReqVO> attrList = columnDefinitionService.getAttrList(ids);
            Map<Long, List<ComponentAttributeSaveReqVO>> attrMap = new HashMap<>();
            if (CollUtil.isNotEmpty(attrList)) {
                attrMap.putAll(attrList.stream().collect(Collectors.groupingBy(ComponentAttributeSaveReqVO::getColumnId)));
            }
            Set<String> codes = pageResult.getList().stream().filter(item-> StrUtil.isNotEmpty(item.getComponentCode())).map(ColumnDefinitionDO::getComponentCode).collect(Collectors.toSet());
            Map<String, ComponentTableDO> componentTableMap = new HashMap<>();
            if (CollUtil.isNotEmpty(codes)) {
                List<ComponentTableDO> list = componentTableService.getComponentTableByCode(codes);
                if (CollUtil.isNotEmpty(list)) {
                    componentTableMap.putAll(list.stream().collect(Collectors.toMap(ComponentTableDO::getComponentCode, item -> item)));
                }
            }
            temp.getList().forEach(item -> {
                item.setPropsList(BeanUtils.toBean(attrMap.get(item.getId()), ComponentAttributeSaveReqVO.class));
                if (Objects.nonNull(item.getDataDomainId())) {
                    item.setColumnTypeWithId(String.format("%d:%s", item.getDataDomainId(), item.getColumnType()));
                } else {
                    item.setColumnTypeWithId(item.getColumnType());
                }
                if (Objects.nonNull(componentTableMap.get(item.getComponentCode()))) {
                    item.setComponentName(componentTableMap.get(item.getComponentCode()).getComponentName());
                }
            });
            return success(temp);
        }
        return success(PageResult.empty());
    }

    @GetMapping("/type")
    @Operation(summary = "获得字段列类型")
    @PreAuthorize("@ss.hasPermission('cfg:column-definition:query')")
    public CommonResult<List<ColumnTypeVO>> getTypeList(@RequestParam("dataSourceId") Long dataSourceId) {
        return success(dbTypeConfigService.getColumnTypeList(dataSourceId));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出字段定义 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:column-definition:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportColumnDefinitionExcel(@Valid ColumnDefinitionPageReqVO pageReqVO,
                                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ColumnDefinitionDO> list = columnDefinitionService.getColumnDefinitionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "字段定义.xls", "数据", ColumnDefinitionRespVO.class,
                BeanUtils.toBean(list, ColumnDefinitionRespVO.class));
    }

}
