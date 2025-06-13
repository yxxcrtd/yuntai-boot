package com.joyintech.yuntai.module.cfg.controller.admin.sqlcodegen;

import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.service.sqlgen.SqlCodegenService;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Table;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理后台 - SQL代码生成器")
@RestController
@RequestMapping("/cfg/sql-codegen")
@Validated
public class SqlCodegenController {

    @Resource
    private SqlCodegenService sqlCodegenService;

    @GetMapping("/db/table/list")
    @Operation(summary = "获得数据库自带的表-导入用")
    @Parameters({
            @Parameter(name = "dataSourceConfigId", description = "数据源配置的编号", required = true, example = "0"),
            @Parameter(name = "name", description = "表名，模糊匹配", example = "cfg_"),
            @Parameter(name = "comment", description = "描述，模糊匹配", example = "云台")})
    @PreAuthorize("@ss.hasPermission('cfg:sql-codegen:query')")
    public CommonResult<List<TableDefinitionRespVO>> getDatabaseTableList(
            @RequestParam(value = "dataSourceConfigId") Long dataSourceConfigId,
            @RequestParam(value = "tableName", required = false) String tableName,
            @RequestParam(value = "tableComment", required = false) String tableComment) {
        List<Table> tableList = sqlCodegenService.getTableList(dataSourceConfigId, tableName, tableComment);
        List<TableDefinitionRespVO> convertedList = BeanUtils.toBean(tableList, TableDefinitionRespVO.class);
        convertedList.forEach(item -> item.setDatasourceId(dataSourceConfigId));
        return success(convertedList);
    }

    @Operation(summary = "基于数据库的表结构，初始化数据库的表和字段定义")
    @PostMapping("/create-list")
    @PreAuthorize("@ss.hasPermission('infra:sql-codegen:create')")
    public CommonResult<List<Long>> createCodegenList(@Valid @RequestBody List<TableDefinitionSaveReqVO> tables) {
        sqlCodegenService.initTableFromDatabase(tables);
        return success(null);
    }

    @Operation(summary = "基于数据库的表结构，同步数据库的表和字段定义")
    @PostMapping("/sync-from-db")
    @Parameter(name = "tableId", description = "表编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:sql-codegen:update')")
    public CommonResult<Boolean> syncCodegenFromDB(@RequestParam("tableId") Long tableId) {
        return success(true);
    }

}
