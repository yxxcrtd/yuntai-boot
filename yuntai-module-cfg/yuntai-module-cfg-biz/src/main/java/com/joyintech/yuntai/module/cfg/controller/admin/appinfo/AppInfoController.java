package com.joyintech.yuntai.module.cfg.controller.admin.appinfo;

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

import com.joyintech.yuntai.module.cfg.controller.admin.appinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.appinfo.AppInfoDO;
import com.joyintech.yuntai.module.cfg.service.appinfo.AppInfoService;

@Tag(name = "管理后台 - 多应用")
@RestController
@RequestMapping("/cfg/app-info")
@Validated
public class AppInfoController {

    @Resource
    private AppInfoService appInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建多应用")
    @PreAuthorize("@ss.hasPermission('cfg:app-info:create')")
    public CommonResult<Long> createAppInfo(@Valid @RequestBody AppInfoSaveReqVO createReqVO) {
        return success(appInfoService.createAppInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新多应用")
    @PreAuthorize("@ss.hasPermission('cfg:app-info:update')")
    public CommonResult<Boolean> updateAppInfo(@Valid @RequestBody AppInfoSaveReqVO updateReqVO) {
        appInfoService.updateAppInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除多应用")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:app-info:delete')")
    public CommonResult<Boolean> deleteAppInfo(@RequestParam("id") Long id) {
        appInfoService.deleteAppInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得多应用")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:app-info:query')")
    public CommonResult<AppInfoRespVO> getAppInfo(@RequestParam("id") Long id) {
        AppInfoDO appInfo = appInfoService.getAppInfo(id);
        return success(BeanUtils.toBean(appInfo, AppInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得多应用分页")
    @PreAuthorize("@ss.hasPermission('cfg:app-info:query')")
    public CommonResult<PageResult<AppInfoRespVO>> getAppInfoPage(@Valid AppInfoPageReqVO pageReqVO) {
        PageResult<AppInfoDO> pageResult = appInfoService.getAppInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出多应用 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:app-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAppInfoExcel(@Valid AppInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AppInfoDO> list = appInfoService.getAppInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "多应用.xls", "数据", AppInfoRespVO.class,
                        BeanUtils.toBean(list, AppInfoRespVO.class));
    }

}