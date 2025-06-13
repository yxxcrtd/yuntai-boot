package com.joyintech.yuntai.module.system.controller.admin.dictnew;

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
import com.joyintech.yuntai.module.system.controller.admin.dictnew.vo.DictPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.dictnew.vo.DictRespVO;
import com.joyintech.yuntai.module.system.controller.admin.dictnew.vo.DictSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dictnew.DictDO;
import com.joyintech.yuntai.module.system.service.dictnew.DictService;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.*;



@Tag(name = "管理后台 - 字典主表")
@RestController
@RequestMapping("/sys/dict")
@Validated
public class DictController {

    @Resource
    private DictService dictService;

    @PostMapping("/create")
    @Operation(summary = "创建字典主表")
    @PreAuthorize("@ss.hasPermission('sys:dict:create')")
    public CommonResult<String> createDict(@Valid @RequestBody DictSaveReqVO createReqVO) {
        return success(dictService.createDict(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新字典主表")
    @PreAuthorize("@ss.hasPermission('sys:dict:update')")
    public CommonResult<Boolean> updateDict(@Valid @RequestBody DictSaveReqVO updateReqVO) {
        dictService.updateDict(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除字典主表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sys:dict:delete')")
    public CommonResult<Boolean> deleteDict(@RequestParam("id") String id) {
        dictService.deleteDict(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得字典主表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sys:dict:query')")
    public CommonResult<DictRespVO> getDict(@RequestParam("id") String id) {
        DictDO dict = dictService.getDict(id);
        return success(BeanUtils.toBean(dict, DictRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得字典主表分页")
    @PreAuthorize("@ss.hasPermission('sys:dict:query')")
    public CommonResult<PageResult<DictRespVO>> getDictPage(@Valid DictPageReqVO pageReqVO) {
        PageResult<DictDO> pageResult = dictService.getDictPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DictRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出字典主表 Excel")
    @PreAuthorize("@ss.hasPermission('sys:dict:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDictExcel(@Valid DictPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DictDO> list = dictService.getDictPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "字典主表.xls", "数据", DictRespVO.class,
                        BeanUtils.toBean(list, DictRespVO.class));
    }

}