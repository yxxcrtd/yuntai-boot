package com.joyintech.yuntai.module.system.controller.admin.treedata;

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

import com.joyintech.yuntai.module.system.controller.admin.treedata.vo.TreeDataPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.treedata.vo.TreeDataRespVO;
import com.joyintech.yuntai.module.system.controller.admin.treedata.vo.TreeDataSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.treedata.TreeDataDO;
import com.joyintech.yuntai.module.system.service.treedata.TreeDataService;

@Tag(name = "管理后台 - 字典树子")
@RestController
@RequestMapping("/comm/tree-data")
@Validated
public class TreeDataController {

    @Resource
    private TreeDataService treeDataService;

    @PostMapping("/create")
    @Operation(summary = "创建字典树子")
    @PreAuthorize("@ss.hasPermission('comm:tree-data:create')")
    public CommonResult<String> createTreeData(@Valid @RequestBody TreeDataSaveReqVO createReqVO) {
        return success(treeDataService.createTreeData(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新字典树子")
    @PreAuthorize("@ss.hasPermission('comm:tree-data:update')")
    public CommonResult<Boolean> updateTreeData(@Valid @RequestBody TreeDataSaveReqVO updateReqVO) {
        treeDataService.updateTreeData(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除字典树子")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('comm:tree-data:delete')")
    public CommonResult<Boolean> deleteTreeData(@RequestParam("id") String id) {
        treeDataService.deleteTreeData(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得字典树子")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('comm:tree-data:query')")
    public CommonResult<TreeDataRespVO> getTreeData(@RequestParam("id") String id) {
        TreeDataDO treeData = treeDataService.getTreeData(id);
        return success(BeanUtils.toBean(treeData, TreeDataRespVO.class));
    }

    @GetMapping("/getTreeData")
    @Operation(summary = "获得字典树子")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('comm:tree-data:query')")
    public CommonResult<List<TreeDataRespVO>> getTreeDataByTreeType(@RequestParam("treeType") String treeType) {
        List<TreeDataDO> treeData = treeDataService.getTreeDataByTreeType(treeType);
        return success(BeanUtils.toBean(treeData, TreeDataRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得字典树子分页")
    @PreAuthorize("@ss.hasPermission('comm:tree-data:query')")
    public CommonResult<PageResult<TreeDataRespVO>> getTreeDataPage(@Valid TreeDataPageReqVO pageReqVO) {
        PageResult<TreeDataDO> pageResult = treeDataService.getTreeDataPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TreeDataRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出字典树子 Excel")
    @PreAuthorize("@ss.hasPermission('comm:tree-data:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTreeDataExcel(@Valid TreeDataPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TreeDataDO> list = treeDataService.getTreeDataPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "字典树子.xls", "数据", TreeDataRespVO.class,
                        BeanUtils.toBean(list, TreeDataRespVO.class));
    }

}