package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.joyintech.yuntai.framework.apilog.core.annotation.ApiAccessLog;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.framework.common.util.tree.TreeNode;
import com.joyintech.yuntai.framework.excel.core.util.ExcelUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleInfoPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleInfoRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleInfoSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleTableRelation;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleinfo.ModuleInfoDO;
import com.joyintech.yuntai.module.cfg.service.moduleinfo.ModuleInfoService;
import com.joyintech.yuntai.module.system.api.user.AdminUserApi;
import com.joyintech.yuntai.module.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 模型信息")
@RestController
@RequestMapping("/cfg/module-info")
@Validated
public class ModuleInfoController {
    @Resource
    private ModuleInfoService moduleInfoService;
    @Resource
    private AdminUserApi adminUserApi;


    @PostMapping("/create")
    @Operation(summary = "创建模型", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ModuleInfoSaveReqVO.class)
            )))
    @PreAuthorize("@ss.hasPermission('cfg:module-info:create')")
    public CommonResult<Long> createModuleInfo(@Valid @RequestBody ModuleInfoSaveReqVO createReqVO) {
        return success(moduleInfoService.createModuleInfo(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "修改模型", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ModuleInfoSaveReqVO.class)
            )))
    @PreAuthorize("@ss.hasPermission('cfg:module-info:update')")
    public CommonResult<Boolean> updateModuleInfo(@Valid @RequestBody ModuleInfoSaveReqVO updateReqVO) {
        moduleInfoService.updateModuleInfo(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除模型信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:module-info:delete')")
    public CommonResult<Boolean> deleteModuleInfo(@RequestParam("id") Long id) {
        moduleInfoService.deleteModuleInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得模型信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:module-info:query')")
    public CommonResult<ModuleInfoRespVO> getModuleInfo(@RequestParam("id") Long id) {
        return success(moduleInfoService.getModuleInfo(id));
    }

    @GetMapping("/list-module")
    @Operation(summary = "获得模型列表")
    @PreAuthorize("@ss.hasPermission('cfg:module-info:query')")
    public CommonResult<List<TreeNode>> listModuleInfo() {
        return success(moduleInfoService.listModuleInfo());
    }

    @GetMapping("/page")
    @Operation(summary = "获得模型信息分页")
    @PreAuthorize("@ss.hasPermission('cfg:module-info:query')")
    public CommonResult<PageResult<ModuleInfoRespVO>> getModuleInfoPage(@Valid ModuleInfoPageReqVO pageReqVO) {
        PageResult<ModuleInfoDO> pageResult = moduleInfoService.getModuleInfoPage(pageReqVO);
        PageResult<ModuleInfoRespVO> result = BeanUtils.toBean(pageResult, ModuleInfoRespVO.class);
        if (CollUtil.isNotEmpty(result.getList())) {
           Set<Long> userId =  result.getList().stream().map(key->Long.valueOf(key.getUpdater())).collect(Collectors.toSet());
           List<AdminUserRespDTO> userList = adminUserApi.getUserList(userId);
           Map<Long,AdminUserRespDTO> userMap = userList.stream().collect(Collectors.toMap(AdminUserRespDTO::getId, key->key));
           result.getList().forEach(key-> key.setUpdater(userMap.get(Long.valueOf(key.getUpdater())).getNickname()));
        }
        return success(result);
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出模型信息 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:module-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportModuleInfoExcel(@Valid ModuleInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ModuleInfoDO> list = moduleInfoService.getModuleInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "模型信息.xls", "数据", ModuleInfoRespVO.class,
                        BeanUtils.toBean(list, ModuleInfoRespVO.class));
    }

    @GetMapping("/refresh")
    @Operation(summary = "修改模型", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ModuleInfoSaveReqVO.class)
            )))
    @PreAuthorize("@ss.hasPermission('cfg:module-info:update')")
    public CommonResult<Boolean> refreshModuleInfo(@RequestParam("id") Long id) {
        moduleInfoService.refreshModuleInfo(id);
        return success(true);
    }

    @GetMapping("/copy")
    @Operation(summary = "复制模型信息")
    @PreAuthorize("@ss.hasPermission('cfg:module-info:create')")
    public CommonResult<Boolean> copyPageInfo(@RequestParam(value = "id") Long id) {
        moduleInfoService.copyPageInfo(id);
        return success(true);
    }

    @GetMapping("/refreshCache")
    @Operation(summary = "刷新缓存信息")
    @PreAuthorize("@ss.hasPermission('cfg:module-info:update')")
    public CommonResult<Boolean>  refreshCache(){
        moduleInfoService.refreshCache();
        return success(true);
    }
}
