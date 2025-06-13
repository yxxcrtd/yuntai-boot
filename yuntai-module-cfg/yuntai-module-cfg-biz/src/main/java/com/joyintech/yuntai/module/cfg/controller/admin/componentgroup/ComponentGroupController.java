package com.joyintech.yuntai.module.cfg.controller.admin.componentgroup;

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

import com.joyintech.yuntai.module.cfg.controller.admin.componentgroup.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentgroup.ComponentGroupDO;
import com.joyintech.yuntai.module.cfg.service.componentgroup.ComponentGroupService;

@Tag(name = "管理后台 - 组件分组")
@RestController
@RequestMapping("/cfg/component-group")
@Validated
public class ComponentGroupController {

    @Resource
    private ComponentGroupService componentGroupService;

    @PostMapping("/create")
    @Operation(summary = "创建组件分组")
    @PreAuthorize("@ss.hasPermission('cfg:component-group:create')")
    public CommonResult<Long> createComponentGroup(@Valid @RequestBody ComponentGroupSaveReqVO createReqVO) {
        return success(componentGroupService.createComponentGroup(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新组件分组")
    @PreAuthorize("@ss.hasPermission('cfg:component-group:update')")
    public CommonResult<Boolean> updateComponentGroup(@Valid @RequestBody ComponentGroupSaveReqVO updateReqVO) {
        componentGroupService.updateComponentGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除组件分组")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:component-group:delete')")
    public CommonResult<Boolean> deleteComponentGroup(@RequestParam("id") Long id) {
        componentGroupService.deleteComponentGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得组件分组")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:component-group:query')")
    public CommonResult<ComponentGroupRespVO> getComponentGroup(@RequestParam("id") Long id) {
        ComponentGroupDO componentGroup = componentGroupService.getComponentGroup(id);
        return success(BeanUtils.toBean(componentGroup, ComponentGroupRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得组件分组分页")
    @PreAuthorize("@ss.hasPermission('cfg:component-group:query')")
    public CommonResult<PageResult<ComponentGroupRespVO>> getComponentGroupPage(@Valid ComponentGroupPageReqVO pageReqVO) {
        PageResult<ComponentGroupDO> pageResult = componentGroupService.getComponentGroupPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ComponentGroupRespVO.class));
    }

    @GetMapping("/getTree")
    @Operation(summary = "树形结构")
    @PreAuthorize("@ss.hasPermission('cfg:component-group:query')")
    public CommonResult<List<ComponentGroupRespVO>> getComponentGroupTree() {
        List<ComponentGroupRespVO> list = componentGroupService.getTree();
        return success(list);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出组件分组 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:component-group:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportComponentGroupExcel(@Valid ComponentGroupPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ComponentGroupDO> list = componentGroupService.getComponentGroupPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "组件分组.xls", "数据", ComponentGroupRespVO.class,
                        BeanUtils.toBean(list, ComponentGroupRespVO.class));
    }

}