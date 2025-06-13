package com.joyintech.yuntai.module.cfg.controller.admin.pageinfo;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joyintech.yuntai.framework.apilog.core.annotation.ApiAccessLog;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.framework.common.util.tree.TreeNode;
import com.joyintech.yuntai.framework.excel.core.util.ExcelUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.pageinfo.vo.PageInfoPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageinfo.vo.PageInfoRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageinfo.vo.PageInfoSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageinfo.PageInfoDO;
import com.joyintech.yuntai.module.cfg.service.outsystemtable.OutSystemTableService;
import com.joyintech.yuntai.module.cfg.service.pageinfo.PageInfoService;
import com.joyintech.yuntai.module.system.api.user.AdminUserApi;
import com.joyintech.yuntai.module.system.api.user.dto.AdminUserRespDTO;

import cn.hutool.core.collection.CollectionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理后台 - 页面基本信息")
@RestController
@RequestMapping("/cfg/page-info")
@Validated
public class PageInfoController {

    @Resource
    private PageInfoService pageInfoService;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private OutSystemTableService outSystemTableService;

    @PostMapping("/create")
    @Operation(summary = "创建页面基本信息")
    @PreAuthorize("@ss.hasPermission('cfg:page-info:create')")
    public CommonResult<Long> createPageInfo(@Valid @RequestBody PageInfoSaveReqVO createReqVO) {
        return success(pageInfoService.createPageApiInfo(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新页面基本信息")
    @PreAuthorize("@ss.hasPermission('cfg:page-info:update')")
    public CommonResult<Boolean> updatePageInfo(@Valid @RequestBody PageInfoSaveReqVO updateReqVO) {
        pageInfoService.updatePageInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除页面基本信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-info:delete')")
    public CommonResult<Boolean> deletePageInfo(@RequestParam("id") Long id) {
        pageInfoService.deletePageInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面基本信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-info:query')")
    public CommonResult<PageInfoRespVO> getPageInfo(@RequestParam("id") Long id) {
        PageInfoRespVO pageInfo = pageInfoService.getPageInfo(id);
        return success(pageInfo);
    }

    @GetMapping("/getflow")
    @Operation(summary = "获得页面基本信息")
    @Parameter(name = "id", description = "编号", required = false, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-info:query')")
    public CommonResult<PageInfoRespVO> getPageInfoByFlow(@RequestParam(value = "pageId", required = false) Long pageId,
            @RequestParam(value = "flowId", required = false) Long flowId,
            @RequestParam(value = "pageDataId", required = false) Long pageDataId) {
        if(pageDataId!=null){
            Long oldPageId = outSystemTableService.getPageId(null, pageDataId);
            if(oldPageId != null){
                PageInfoRespVO pageInfo = pageInfoService.getPageInfo(oldPageId);
                return success(pageInfo);
            }
        }
        if(pageId!=null){
            PageInfoRespVO pageInfo = pageInfoService.getPageInfo(pageId);
            return success(pageInfo);
        }
        if(flowId!=null){
            Long oldPageId = outSystemTableService.getPageId(flowId, null);
            if(oldPageId != null){
                PageInfoRespVO pageInfo = pageInfoService.getPageInfo(oldPageId);
                return success(pageInfo);
            }
        }
        PageInfoRespVO pageInfo = new PageInfoRespVO();
        return success(pageInfo);
    }

    @GetMapping("/list-page")
    @Operation(summary = "页面基本信息列表")
    @PreAuthorize("@ss.hasPermission('cfg:page-info:query')")
    public CommonResult<List<TreeNode>> listPage() {
        return success(pageInfoService.listPage());
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面基本信息分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-info:query')")
    public CommonResult<PageResult<PageInfoRespVO>> getPageInfoPage(@Valid PageInfoPageReqVO pageReqVO) {
        PageResult<PageInfoDO> pageResult = pageInfoService.getPageInfoPage(pageReqVO);
        PageResult<PageInfoRespVO> result = BeanUtils.toBean(pageResult, PageInfoRespVO.class);
        if (CollectionUtil.isNotEmpty(result.getList())) {
            Set<Long> userId =  result.getList().stream().map(key->Long.valueOf(key.getUpdater())).collect(Collectors.toSet());
            List<AdminUserRespDTO> userList = adminUserApi.getUserList(userId);
            Map<Long,AdminUserRespDTO> userMap = userList.stream().collect(Collectors.toMap(AdminUserRespDTO::getId, key->key));
            result.getList().forEach(key-> {
                if (userMap.get(Long.valueOf(key.getUpdater())) != null) {
                    key.setUpdater(userMap.get(Long.valueOf(key.getUpdater())).getNickname());
                }
            });
        }
        return success(result);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页面基本信息 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageInfoExcel(@Valid PageInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageInfoDO> list = pageInfoService.getPageInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页面基本信息.xls", "数据", PageInfoRespVO.class,
                        BeanUtils.toBean(list, PageInfoRespVO.class));
    }

    @GetMapping("/copy")
    @Operation(summary = "复制页面基本信息")
    @PreAuthorize("@ss.hasPermission('cfg:page-info:create')")
    public CommonResult<Boolean> copyPageInfo(@RequestParam(value = "id") Long id, @RequestParam(value = "type") String type) {
        pageInfoService.copyPageInfo(id, type);
        return success(true);
    }

}
