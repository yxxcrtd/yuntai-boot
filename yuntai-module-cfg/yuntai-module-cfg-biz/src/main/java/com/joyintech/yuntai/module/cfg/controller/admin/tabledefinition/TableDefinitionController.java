package com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import cn.hutool.core.collection.CollUtil;
import com.joyintech.yuntai.module.infra.api.db.DataSourceConfigApi;
import com.joyintech.yuntai.module.infra.api.db.dto.DataSourceConfigRespDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joyintech.yuntai.framework.apilog.core.annotation.ApiAccessLog;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.framework.excel.core.util.ExcelUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.tabledefinition.TableDefinitionDO;
import com.joyintech.yuntai.module.cfg.service.tabledefinition.TableDefinitionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理后台 - 表定义")
@RestController
@RequestMapping("/cfg/table-definition")
@Validated
public class TableDefinitionController {

    @Resource
    private TableDefinitionService tableDefinitionService;
    @Resource
    private DataSourceConfigApi dataSourceConfigApi;


    @PostMapping("/create")
    @Operation(summary = "创建表定义")
    @PreAuthorize("@ss.hasPermission('cfg:table-definition:create')")
    public CommonResult<Long> createTableDefinition(@Valid @RequestBody TableDefinitionSaveReqVO createReqVO) {
        return success(tableDefinitionService.createTableDefinition(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新表定义")
    @PreAuthorize("@ss.hasPermission('cfg:table-definition:update')")
    public CommonResult<Boolean> updateTableDefinition(@Valid @RequestBody TableDefinitionSaveReqVO updateReqVO) {
        tableDefinitionService.updateTableDefinition(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除表定义")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:table-definition:delete')")
    public CommonResult<Boolean> deleteTableDefinition(@RequestParam("id") Long id) {
        tableDefinitionService.deleteTableDefinition(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得表定义")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:table-definition:query')")
    public CommonResult<TableDefinitionRespVO> getTableDefinition(@RequestParam("id") Long id) {
        TableDefinitionDO tableDefinition = tableDefinitionService.getTableDefinition(id);
        TableDefinitionRespVO vo = BeanUtils.toBean(tableDefinition, TableDefinitionRespVO.class);
        DataSourceConfigRespDTO ds = dataSourceConfigApi.getDataSourceConfig(tableDefinition.getDatasourceId());
        if(ds != null) {
            vo.setDatasourceName(ds.getName());
        }
        return success(vo);
    }

    @GetMapping("/page")
    @Operation(summary = "获得表定义分页")
    @PreAuthorize("@ss.hasPermission('cfg:table-definition:query')")
    public CommonResult<PageResult<TableDefinitionRespVO>> getTableDefinitionPage(@Valid TableDefinitionPageReqVO pageReqVO) {
        PageResult<TableDefinitionDO> pageResult = tableDefinitionService.getTableDefinitionPage(pageReqVO);
        PageResult<TableDefinitionRespVO> page = BeanUtils.toBean(pageResult, TableDefinitionRespVO.class);
        if (CollUtil.isNotEmpty(page.getList())) {
            Set<Long> dataSourceIds = page.getList().stream().map(TableDefinitionRespVO::getDatasourceId).collect(Collectors.toSet());
            List<DataSourceConfigRespDTO> dataSourceList = dataSourceConfigApi.getDataSourceList(dataSourceIds);
            if (CollUtil.isNotEmpty(dataSourceList)) {
                Map<Long,DataSourceConfigRespDTO> map = dataSourceList.stream().collect(Collectors.toMap(DataSourceConfigRespDTO::getId, dataSource -> dataSource));
                page.getList().forEach(item -> {item.setDatasourceName(null != map.get(item.getDatasourceId()) ? map.get(item.getDatasourceId()).getName() : null);});
            }
        }
        return success(page);
    }

    @GetMapping("/list")
    @Operation(summary = "获得表定义列表")
    @PreAuthorize("@ss.hasPermission('cfg:table-definition:list')")
    public CommonResult<List<TableDefinitionRespVO>> getTableDefinitionList(@Valid TableDefinitionPageReqVO pageReqVO) {
        List<TableDefinitionDO> list = tableDefinitionService.getTableDefinitionList(pageReqVO);
        return success(BeanUtils.toBean(list, TableDefinitionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出表定义 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:table-definition:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTableDefinitionExcel(@Valid TableDefinitionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TableDefinitionDO> list = tableDefinitionService.getTableDefinitionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "表定义.xls", "数据", TableDefinitionRespVO.class,
                        BeanUtils.toBean(list, TableDefinitionRespVO.class));
    }

}
