package com.joyintech.yuntai.module.cfg.controller.admin.processdata;

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
import com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo.ProcessDataPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo.ProcessDataRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo.ProcessDataSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processdata.ProcessDataDO;
import com.joyintech.yuntai.module.cfg.service.processdata.ProcessDataService;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 流程Log日志")
@RestController
@RequestMapping("/log/process-data")
@Validated
public class ProcessDataController {

    @Resource
    private ProcessDataService processDataService;

    @PostMapping("/create")
    @Operation(summary = "创建流程Log日志")
    @PreAuthorize("@ss.hasPermission('log:process-data:create')")
    public CommonResult<Long> createProcessData(@Valid @RequestBody ProcessDataSaveReqVO createReqVO) {
        return success(processDataService.createProcessData(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新流程Log日志")
    @PreAuthorize("@ss.hasPermission('log:process-data:update')")
    public CommonResult<Boolean> updateProcessData(@Valid @RequestBody ProcessDataSaveReqVO updateReqVO) {
        processDataService.updateProcessData(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除流程Log日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('log:process-data:delete')")
    public CommonResult<Boolean> deleteProcessData(@RequestParam("id") Long id) {
        processDataService.deleteProcessData(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得流程Log日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('log:process-data:query')")
    public CommonResult<ProcessDataRespVO> getProcessData(@RequestParam("id") Long id) {
        ProcessDataDO processData = processDataService.getProcessData(id);
        return success(BeanUtils.toBean(processData, ProcessDataRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得流程Log日志分页")
    @PreAuthorize("@ss.hasPermission('log:process-data:query')")
    public CommonResult<PageResult<ProcessDataRespVO>> getProcessDataPage(@Valid ProcessDataPageReqVO pageReqVO) {
        PageResult<ProcessDataDO> pageResult = processDataService.getProcessDataPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ProcessDataRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出流程Log日志 Excel")
    @PreAuthorize("@ss.hasPermission('log:process-data:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProcessDataExcel(@Valid ProcessDataPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ProcessDataDO> list = processDataService.getProcessDataPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "流程Log日志.xls", "数据", ProcessDataRespVO.class,
                        BeanUtils.toBean(list, ProcessDataRespVO.class));
    }

}