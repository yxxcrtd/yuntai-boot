package com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition.vo.IndexDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition.vo.IndexDefinitionRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition.vo.IndexDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.indexdefinition.IndexDefinitionDO;
import com.joyintech.yuntai.module.cfg.service.indexdefinition.IndexDefinitionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理后台 - 索引定义")
@RestController
@RequestMapping("/cfg/index-definition")
@Validated
public class IndexDefinitionController {

    @Resource
    private IndexDefinitionService indexDefinitionService;

    @PostMapping(value = "/create")
    @Operation(summary = "创建索引定义", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = IndexDefinitionSaveReqVO.class)
            )))
    @PreAuthorize("@ss.hasPermission('cfg:index-definition:create')")
    public CommonResult<Long> createIndexDefinition(@Valid @RequestBody IndexDefinitionSaveReqVO createReqVO) {
        return success(indexDefinitionService.createIndexDefinition(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新索引定义")
    @PreAuthorize("@ss.hasPermission('cfg:index-definition:update')")
    public CommonResult<Boolean> updateIndexDefinition(@Valid @RequestBody IndexDefinitionSaveReqVO updateReqVO) {
        indexDefinitionService.updateIndexDefinition(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除索引定义")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:index-definition:delete')")
    public CommonResult<Boolean> deleteIndexDefinition(@RequestParam("id") Long id) {
        indexDefinitionService.deleteIndexDefinition(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得索引定义")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:index-definition:query')")
    public CommonResult<IndexDefinitionRespVO> getIndexDefinition(@RequestParam("id") Long id) {
        IndexDefinitionDO indexDefinition = indexDefinitionService.getIndexDefinition(id);
        return success(BeanUtils.toBean(indexDefinition, IndexDefinitionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得索引定义分页")
    @PreAuthorize("@ss.hasPermission('cfg:index-definition:query')")
    public CommonResult<PageResult<IndexDefinitionRespVO>> getIndexDefinitionPage(@Valid IndexDefinitionPageReqVO pageReqVO) {
        PageResult<IndexDefinitionDO> pageResult = indexDefinitionService.getIndexDefinitionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, IndexDefinitionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出索引定义 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:index-definition:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportIndexDefinitionExcel(@Valid IndexDefinitionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<IndexDefinitionDO> list = indexDefinitionService.getIndexDefinitionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "索引定义.xls", "数据", IndexDefinitionRespVO.class,
                        BeanUtils.toBean(list, IndexDefinitionRespVO.class));
    }

}