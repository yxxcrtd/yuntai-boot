package com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig.vo.DataSetConfigPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig.vo.DataSetConfigRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig.vo.DataSetConfigSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfig.DataSetConfigDO;
import com.joyintech.yuntai.module.cfg.service.datasetconfig.DataSetConfigService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理后台 - 数据集管理")
@RestController
@RequestMapping("/infra/data-set-config")
@Validated
public class DataSetConfigController {

    @Resource
    private DataSetConfigService dataSetConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建数据集管理")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config:create')")
    public CommonResult<Long> createDataSetConfig(@Valid @RequestBody DataSetConfigSaveReqVO createReqVO) {
        return success(dataSetConfigService.createDataSetConfig(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新数据集管理")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config:update')")
    public CommonResult<Boolean> updateDataSetConfig(@Valid @RequestBody DataSetConfigSaveReqVO updateReqVO) {
        dataSetConfigService.updateDataSetConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除数据集管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('infra:data-set-config:delete')")
    public CommonResult<Boolean> deleteDataSetConfig(@RequestParam("id") Long id) {
        dataSetConfigService.deleteDataSetConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得数据集管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config:query')")
    public CommonResult<DataSetConfigRespVO> getDataSetConfig(@RequestParam("id") Long id) {
        DataSetConfigDO dataSetConfig = dataSetConfigService.getDataSetConfig(id);
        return success(BeanUtils.toBean(dataSetConfig, DataSetConfigRespVO.class));
    }

    @GetMapping("/getcode")
    @Operation(summary = "获得数据集管理")
    @Parameter(name = "code", description = "数据集编码", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config:query')")
    public CommonResult<DataSetConfigRespVO> getDataSetConfigByCode(@RequestParam("code") String code) {
        DataSetConfigDO dataSetConfig = dataSetConfigService.getDataSetConfigByCode(code);
        return success(BeanUtils.toBean(dataSetConfig, DataSetConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得数据集管理分页")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config:query')")
    public CommonResult<PageResult<DataSetConfigRespVO>> getDataSetConfigPage(@Valid DataSetConfigPageReqVO pageReqVO) {
        PageResult<DataSetConfigDO> pageResult = dataSetConfigService.getDataSetConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DataSetConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出数据集管理 Excel")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDataSetConfigExcel(@Valid DataSetConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DataSetConfigDO> list = dataSetConfigService.getDataSetConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "数据集管理.xls", "数据", DataSetConfigRespVO.class,
                        BeanUtils.toBean(list, DataSetConfigRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得数据集管理列表")
    @PreAuthorize("@ss.hasPermission('infra:data-set-config:query')")
    public CommonResult<List<DataSetConfigRespVO>> getDataSourceConfigList(@RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "searchKeyword",required = false) String searchKeyword) {
        if(StringUtils.isNotBlank(searchKeyword)){
            List<DataSetConfigDO> list = dataSetConfigService.getDataSourceConfigList(searchKeyword);
            return success(BeanUtils.toBean(list, DataSetConfigRespVO.class));
        }
        List<DataSetConfigDO> list = dataSetConfigService.getDataSourceConfigList(name);
        return success(BeanUtils.toBean(list, DataSetConfigRespVO.class));
    }

}