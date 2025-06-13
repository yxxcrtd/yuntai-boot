package com.joyintech.yuntai.module.cfg.controller.admin.commonmodel;

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

import com.joyintech.yuntai.module.cfg.controller.admin.commonmodel.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.commonmodel.CommonModelDO;
import com.joyintech.yuntai.module.cfg.service.commonmodel.CommonModelService;

@Tag(name = "管理后台 - 公共模型")
@RestController
@RequestMapping("/cfg/common-model")
@Validated
public class CommonModelController {

    @Resource
    private CommonModelService commonModelService;

    @PostMapping("/create")
    @Operation(summary = "创建公共模型")
    @PreAuthorize("@ss.hasPermission('cfg:common-model:create')")
    public CommonResult<Long> createCommonModel(@Valid @RequestBody CommonModelSaveReqVO createReqVO) {
        return success(commonModelService.createCommonModel(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新公共模型")
    @PreAuthorize("@ss.hasPermission('cfg:common-model:update')")
    public CommonResult<Boolean> updateCommonModel(@Valid @RequestBody CommonModelSaveReqVO updateReqVO) {
        commonModelService.updateCommonModel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公共模型")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:common-model:delete')")
    public CommonResult<Boolean> deleteCommonModel(@RequestParam("id") Long id) {
        commonModelService.deleteCommonModel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公共模型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:common-model:query')")
    public CommonResult<CommonModelRespVO> getCommonModel(@RequestParam("id") Long id) {
        CommonModelDO commonModel = commonModelService.getCommonModel(id);
        return success(BeanUtils.toBean(commonModel, CommonModelRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公共模型分页")
    @PreAuthorize("@ss.hasPermission('cfg:common-model:query')")
    public CommonResult<PageResult<CommonModelRespVO>> getCommonModelPage(@Valid CommonModelPageReqVO pageReqVO) {
        PageResult<CommonModelDO> pageResult = commonModelService.getCommonModelPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CommonModelRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公共模型 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:common-model:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCommonModelExcel(@Valid CommonModelPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CommonModelDO> list = commonModelService.getCommonModelPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "公共模型.xls", "数据", CommonModelRespVO.class,
                        BeanUtils.toBean(list, CommonModelRespVO.class));
    }

}