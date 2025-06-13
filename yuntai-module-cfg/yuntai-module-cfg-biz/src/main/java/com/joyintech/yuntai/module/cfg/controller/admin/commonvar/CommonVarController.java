package com.joyintech.yuntai.module.cfg.controller.admin.commonvar;

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

import com.joyintech.yuntai.module.cfg.controller.admin.commonvar.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.commonvar.CommonVarDO;
import com.joyintech.yuntai.module.cfg.service.commonvar.CommonVarService;

@Tag(name = "管理后台 - 公共变量")
@RestController
@RequestMapping("/cfg/common-var")
@Validated
public class CommonVarController {

    @Resource
    private CommonVarService commonVarService;

    @PostMapping("/create")
    @Operation(summary = "创建公共变量")
    @PreAuthorize("@ss.hasPermission('cfg:common-var:create')")
    public CommonResult<Long> createCommonVar(@Valid @RequestBody CommonVarSaveReqVO createReqVO) {
        return success(commonVarService.createCommonVar(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新公共变量")
    @PreAuthorize("@ss.hasPermission('cfg:common-var:update')")
    public CommonResult<Boolean> updateCommonVar(@Valid @RequestBody CommonVarSaveReqVO updateReqVO) {
        commonVarService.updateCommonVar(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公共变量")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:common-var:delete')")
    public CommonResult<Boolean> deleteCommonVar(@RequestParam("id") Long id) {
        commonVarService.deleteCommonVar(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公共变量")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:common-var:query')")
    public CommonResult<CommonVarRespVO> getCommonVar(@RequestParam("id") Long id) {
        CommonVarDO commonVar = commonVarService.getCommonVar(id);
        return success(BeanUtils.toBean(commonVar, CommonVarRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公共变量分页")
    @PreAuthorize("@ss.hasPermission('cfg:common-var:query')")
    public CommonResult<PageResult<CommonVarRespVO>> getCommonVarPage(@Valid CommonVarPageReqVO pageReqVO) {
        PageResult<CommonVarDO> pageResult = commonVarService.getCommonVarPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CommonVarRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公共变量 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:common-var:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCommonVarExcel(@Valid CommonVarPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CommonVarDO> list = commonVarService.getCommonVarPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "公共变量.xls", "数据", CommonVarRespVO.class,
                        BeanUtils.toBean(list, CommonVarRespVO.class));
    }

}