package com.joyintech.yuntai.module.cfg.controller.admin.paramterlist;

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

import com.joyintech.yuntai.module.cfg.controller.admin.paramterlist.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.paramterlist.ParamterListDO;
import com.joyintech.yuntai.module.cfg.service.paramterlist.ParamterListService;

@Tag(name = "管理后台 - 页面路由参数")
@RestController
@RequestMapping("/cfg/paramter-list")
@Validated
public class ParamterListController {

    @Resource
    private ParamterListService paramterListService;

    @PostMapping("/create")
    @Operation(summary = "创建页面路由参数")
    @PreAuthorize("@ss.hasPermission('cfg:paramter-list:create')")
    public CommonResult<Long> createParamterList(@Valid @RequestBody ParamterListSaveReqVO createReqVO) {
        return success(paramterListService.createParamterList(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面路由参数")
    @PreAuthorize("@ss.hasPermission('cfg:paramter-list:update')")
    public CommonResult<Boolean> updateParamterList(@Valid @RequestBody ParamterListSaveReqVO updateReqVO) {
        paramterListService.updateParamterList(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除页面路由参数")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:paramter-list:delete')")
    public CommonResult<Boolean> deleteParamterList(@RequestParam("id") Long id) {
        paramterListService.deleteParamterList(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面路由参数")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:paramter-list:query')")
    public CommonResult<ParamterListRespVO> getParamterList(@RequestParam("id") Long id) {
        ParamterListDO paramterList = paramterListService.getParamterList(id);
        return success(BeanUtils.toBean(paramterList, ParamterListRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面路由参数分页")
    @PreAuthorize("@ss.hasPermission('cfg:paramter-list:query')")
    public CommonResult<PageResult<ParamterListRespVO>> getParamterListPage(@Valid ParamterListPageReqVO pageReqVO) {
        PageResult<ParamterListDO> pageResult = paramterListService.getParamterListPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ParamterListRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面路由参数 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:paramter-list:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportParamterListExcel(@Valid ParamterListPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ParamterListDO> list = paramterListService.getParamterListPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面路由参数.xls", "数据", ParamterListRespVO.class,
                        BeanUtils.toBean(list, ParamterListRespVO.class));
    }

}