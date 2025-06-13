package com.joyintech.yuntai.module.system.controller.admin.dictitem;

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

import com.joyintech.yuntai.module.system.controller.admin.dictitem.vo.DictItemPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.dictitem.vo.DictItemRespVO;
import com.joyintech.yuntai.module.system.controller.admin.dictitem.vo.DictItemSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dictitem.DictItemDO;
import com.joyintech.yuntai.module.system.service.dictitem.DictItemService;

@Tag(name = "管理后台 - 字典子表")
@RestController
@RequestMapping("/sys/dict-item")
@Validated
public class DictItemController {

    @Resource
    private DictItemService dictItemService;

    @PostMapping("/create")
    @Operation(summary = "创建字典子表")
    @PreAuthorize("@ss.hasPermission('sys:dict-item:create')")
    public CommonResult<String> createDictItem(@Valid @RequestBody DictItemSaveReqVO createReqVO) {
        return success(dictItemService.createDictItem(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新字典子表")
    @PreAuthorize("@ss.hasPermission('sys:dict-item:update')")
    public CommonResult<Boolean> updateDictItem(@Valid @RequestBody DictItemSaveReqVO updateReqVO) {
        dictItemService.updateDictItem(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除字典子表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sys:dict-item:delete')")
    public CommonResult<Boolean> deleteDictItem(@RequestParam("id") String id) {
        dictItemService.deleteDictItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得字典子表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sys:dict-item:query')")
    public CommonResult<DictItemRespVO> getDictItem(@RequestParam("id") String id) {
        DictItemDO dictItem = dictItemService.getDictItem(id);
        return success(BeanUtils.toBean(dictItem, DictItemRespVO.class));
    }

    @GetMapping("/getDictCode")
    @Operation(summary = "获得字典子表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sys:dict-item:query')")
    public CommonResult<List<DictItemRespVO>> getDictItemByCode(@RequestParam("dictCode") String dictCode) {
        List<DictItemDO> dictItem = dictItemService.getDictItemByCode(dictCode);
        return success(BeanUtils.toBean(dictItem, DictItemRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得字典子表分页")
    @PreAuthorize("@ss.hasPermission('sys:dict-item:query')")
    public CommonResult<PageResult<DictItemRespVO>> getDictItemPage(@Valid DictItemPageReqVO pageReqVO) {
        PageResult<DictItemDO> pageResult = dictItemService.getDictItemPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DictItemRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出字典子表 Excel")
    @PreAuthorize("@ss.hasPermission('sys:dict-item:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDictItemExcel(@Valid DictItemPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DictItemDO> list = dictItemService.getDictItemPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "字典子表.xls", "数据", DictItemRespVO.class,
                        BeanUtils.toBean(list, DictItemRespVO.class));
    }

}