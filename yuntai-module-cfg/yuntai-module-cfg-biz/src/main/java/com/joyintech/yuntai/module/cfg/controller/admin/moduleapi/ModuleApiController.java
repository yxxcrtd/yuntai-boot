package com.joyintech.yuntai.module.cfg.controller.admin.moduleapi;

import cn.hutool.core.collection.CollUtil;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleApiParamVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleinfo.ModuleInfoDO;
import com.joyintech.yuntai.module.cfg.service.moduleinfo.ModuleInfoService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import static com.joyintech.yuntai.framework.common.util.collection.CollectionUtils.convertList;

import com.joyintech.yuntai.module.cfg.controller.admin.moduleapi.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleapi.ModuleApiDO;
import com.joyintech.yuntai.module.cfg.service.moduleapi.ModuleApiService;

@Tag(name = "管理后台 - 模型API")
@RestController
@RequestMapping("/cfg/module-api")
@Validated
public class ModuleApiController {

    @Resource
    private ModuleApiService moduleApiService;

    @Resource
    private ModuleInfoService moduleInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建模型API", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ModuleApiSaveReqVO.class)
            )))
    @PreAuthorize("@ss.hasPermission('cfg:module-api:create')")
    public CommonResult<Long> createModuleApi(@Valid @RequestBody ModuleApiSaveReqVO createReqVO) {
        return success(moduleApiService.createModuleApi(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新模型API", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ModuleApiSaveReqVO.class)
            )))
    @PreAuthorize("@ss.hasPermission('cfg:module-api:update')")
    public CommonResult<Boolean> updateModuleApi(@Valid @RequestBody ModuleApiSaveReqVO updateReqVO) {
        moduleApiService.updateModuleApi(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除模型API")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:module-api:delete')")
    public CommonResult<Boolean> deleteModuleApi(@RequestParam("id") Long id) {
        moduleApiService.deleteModuleApi(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得模型API")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:module-api:query')")
    public CommonResult<ModuleApiRespVO> getModuleApi(@RequestParam("id") Long id) {
        ModuleApiDO moduleApi = moduleApiService.getModuleApi(id);
        return success(BeanUtils.toBean(moduleApi, ModuleApiRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得模型API分页")
    @PreAuthorize("@ss.hasPermission('cfg:module-api:query')")
    public CommonResult<PageResult<ModuleApiRespVO>> getModuleApiPage(@Valid ModuleApiPageReqVO pageReqVO) {
        PageResult<ModuleApiDO> pageResult = moduleApiService.getModuleApiPage(pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }
        // 拼接数据
        Map<Long, ModuleInfoDO> deptMap = moduleInfoService.getModuleInfoMap(convertList(pageResult.getList(), ModuleApiDO::getModuleId));
        PageResult<ModuleApiRespVO> tempPageResult = BeanUtils.toBean(pageResult, ModuleApiRespVO.class);
        for (ModuleApiRespVO respVO : tempPageResult.getList()) {
            ModuleInfoDO moduleInfo = deptMap.get(respVO.getModuleId());
            if (moduleInfo == null) {
                continue;
            }
            respVO.setModuleName(moduleInfo.getModuleName());
        }
        return success(tempPageResult);
    }

    @GetMapping("/list")
    @Operation(summary = "获得模型API列表")
    @PreAuthorize("@ss.hasPermission('cfg:module-api:query')")
    public CommonResult<List<ModuleApiRespVO>> getModuleApiList(@RequestParam("moduleId") Long moduleId) {
        List<ModuleApiDO> list = moduleApiService.getModuleApiList(moduleId);
        if (CollUtil.isEmpty(list)) {
            return success(new ArrayList<>());
        }
        // 拼接数据
        Map<Long, ModuleInfoDO> deptMap = moduleInfoService.getModuleInfoMap(convertList(list, ModuleApiDO::getModuleId));
        List<ModuleApiRespVO> tempList = BeanUtils.toBean(list, ModuleApiRespVO.class);
        for (ModuleApiRespVO respVO : tempList) {
            ModuleInfoDO moduleInfo = deptMap.get(respVO.getModuleId());
            if (moduleInfo == null) {
                continue;
            }
            respVO.setModuleName(moduleInfo.getModuleName());
        }
        return success(tempList);
    }

    @GetMapping("/param/list")
    @Operation(summary = "获得模型API参数列表")
    @PreAuthorize("@ss.hasPermission('cfg:module-api:query')")
    public CommonResult<List<ModuleApiParamVO>> getModuleApiParamList(@RequestParam("apiId") Long apiId) {
        List<ModuleApiParamVO> list = moduleApiService.getModuleApiParamList(apiId);
        if (CollUtil.isEmpty(list)) {
            return success(new ArrayList<>());
        }
        return success(list);
    }

    @GetMapping("/param-list")
    @Operation(summary = "获得模型API参数列表")
    @PreAuthorize("@ss.hasPermission('cfg:module-api:query')")
    public CommonResult<Map<Long,List<ModuleApiParamVO>>> getModuleApiParamList(@RequestParam("apiIds") String apiIds) {
        Map<Long,List<ModuleApiParamVO>> list = moduleApiService.getParamList(apiIds);
        if (CollUtil.isEmpty(list)) {
            return success(new HashMap<>());
        }
        return success(list);
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出模型API Excel")
    @PreAuthorize("@ss.hasPermission('cfg:module-api:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportModuleApiExcel(@Valid ModuleApiPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ModuleApiDO> list = moduleApiService.getModuleApiPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "模型API.xls", "数据", ModuleApiRespVO.class,
                        BeanUtils.toBean(list, ModuleApiRespVO.class));
    }

}
