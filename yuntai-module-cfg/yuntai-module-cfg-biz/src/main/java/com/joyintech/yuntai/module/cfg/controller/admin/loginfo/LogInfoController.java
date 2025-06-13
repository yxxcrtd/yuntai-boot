package com.joyintech.yuntai.module.cfg.controller.admin.loginfo;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import com.joyintech.yuntai.module.cfg.controller.admin.loginfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.loginfo.LogInfoDO;
import com.joyintech.yuntai.module.cfg.service.loginfo.LogInfoService;

@Tag(name = "管理后台 - 日志记录")
@RestController
@RequestMapping("/cfg/log-info")
@Validated
public class LogInfoController {

    @Resource
    private LogInfoService logInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建日志记录")
    @PreAuthorize("@ss.hasPermission('cfg:log-info:create')")
    public CommonResult<Long> createLogInfo(@Valid @RequestBody LogInfoSaveReqVO createReqVO) {
        return success(logInfoService.createLogInfo(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新日志记录")
    @PreAuthorize("@ss.hasPermission('cfg:log-info:update')")
    public CommonResult<Boolean> updateLogInfo(@Valid @RequestBody LogInfoSaveReqVO updateReqVO) {
        logInfoService.updateLogInfo(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除日志记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:log-info:delete')")
    public CommonResult<Boolean> deleteLogInfo(@RequestParam("id") Long id) {
        logInfoService.deleteLogInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得日志记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:log-info:query')")
    public CommonResult<LogInfoRespVO> getLogInfo(@RequestParam("id") Long id) {
        LogInfoDO logInfo = logInfoService.getLogInfo(id);
        return success(BeanUtils.toBean(logInfo, LogInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得日志记录分页")
    @PreAuthorize("@ss.hasPermission('cfg:log-info:query')")
    public CommonResult<PageResult<LogInfoRespVO>> getLogInfoPage(@Valid LogInfoPageReqVO pageReqVO) {
        PageResult<LogInfoDO> pageResult = logInfoService.getLogInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, LogInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出日志记录 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:log-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportLogInfoExcel(@Valid LogInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<LogInfoDO> list = logInfoService.getLogInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "日志记录.xls", "数据", LogInfoRespVO.class,
                        BeanUtils.toBean(list, LogInfoRespVO.class));
    }

}