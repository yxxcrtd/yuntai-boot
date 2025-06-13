package com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import com.joyintech.yuntai.framework.excel.core.util.ExcelUtils;

import com.joyintech.yuntai.framework.apilog.core.annotation.ApiAccessLog;
import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.*;

import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfighttp.DataSetConfigHttpDO;
import com.joyintech.yuntai.module.cfg.service.datasetconfighttp.DataSetConfigHttpService;

@Tag(name = "管理后台 - 数据集-http请求内容")
@RestController
@RequestMapping("/infra/data-set-config-http")
@Validated
public class DataSetConfigHttpController {

    @Resource
    private DataSetConfigHttpService dataSetConfigHttpService;

    @PostMapping("/create")
    @Operation(summary = "创建数据集-http请求内容")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config-http:create')")
    public CommonResult<Long> createDataSetConfigHttp(@Valid @RequestBody DataSetConfigHttpSaveReqVO createReqVO) {
        return success(dataSetConfigHttpService.createDataSetConfigHttp(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新数据集-http请求内容")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config-http:update')")
    public CommonResult<Boolean> updateDataSetConfigHttp(@Valid @RequestBody DataSetConfigHttpSaveReqVO updateReqVO) {
        dataSetConfigHttpService.updateDataSetConfigHttp(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除数据集-http请求内容")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('infra:data-set-config-http:delete')")
    public CommonResult<Boolean> deleteDataSetConfigHttp(@RequestParam("id") Long id) {
        dataSetConfigHttpService.deleteDataSetConfigHttp(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得数据集-http请求内容")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config-http:query')")
    public CommonResult<DataSetConfigHttpRespVO> getDataSetConfigHttp(@RequestParam("id") Long id) {
        DataSetConfigHttpDO dataSetConfigHttp = dataSetConfigHttpService.getDataSetConfigHttp(id);
        return success(BeanUtils.toBean(dataSetConfigHttp, DataSetConfigHttpRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得数据集-http请求内容分页")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config-http:query')")
    public CommonResult<PageResult<DataSetConfigHttpRespVO>> getDataSetConfigHttpPage(@Valid DataSetConfigHttpPageReqVO pageReqVO) {
        PageResult<DataSetConfigHttpDO> pageResult = dataSetConfigHttpService.getDataSetConfigHttpPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DataSetConfigHttpRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出数据集-http请求内容 Excel")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config-http:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDataSetConfigHttpExcel(@Valid DataSetConfigHttpPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DataSetConfigHttpDO> list = dataSetConfigHttpService.getDataSetConfigHttpPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "数据集-http请求内容.xls", "数据", DataSetConfigHttpRespVO.class,
                        BeanUtils.toBean(list, DataSetConfigHttpRespVO.class));
    }

}