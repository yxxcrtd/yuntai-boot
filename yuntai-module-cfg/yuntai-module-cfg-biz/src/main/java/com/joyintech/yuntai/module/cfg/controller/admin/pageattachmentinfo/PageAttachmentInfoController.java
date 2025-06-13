package com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
import com.joyintech.yuntai.framework.excel.core.util.ExcelUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo.PageAttachmentInfoPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo.PageAttachmentInfoRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo.PageAttachmentInfoSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentinfo.PageAttachmentInfoDO;
import com.joyintech.yuntai.module.cfg.service.pageattachmentinfo.PageAttachmentInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理后台 - 表单页配置-附件管理")
@RestController
@RequestMapping("/cfg/page-attachment-info")
@Validated
public class PageAttachmentInfoController {

    @Resource
    private PageAttachmentInfoService pageAttachmentInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建表单页配置-附件管理")
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-info:create')")
    public CommonResult<Long> createPageAttachmentInfo(@Valid @RequestBody PageAttachmentInfoSaveReqVO createReqVO) {
        return success(pageAttachmentInfoService.createPageAttachmentInfo(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新表单页配置-附件管理")
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-info:update')")
    public CommonResult<Boolean> updatePageAttachmentInfo(@Valid @RequestBody PageAttachmentInfoSaveReqVO updateReqVO) {
        pageAttachmentInfoService.updatePageAttachmentInfo(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除表单页配置-附件管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-info:delete')")
    public CommonResult<Boolean> deletePageAttachmentInfo(@RequestParam("id") Long id) {
        pageAttachmentInfoService.deletePageAttachmentInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得表单页配置-附件管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-info:query')")
    public CommonResult<PageAttachmentInfoRespVO> getPageAttachmentInfo(@RequestParam("id") Long id) {
        PageAttachmentInfoDO pageAttachmentInfo = pageAttachmentInfoService.getPageAttachmentInfo(id);
        return success(BeanUtils.toBean(pageAttachmentInfo, PageAttachmentInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得表单页配置-附件管理分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-info:query')")
    public CommonResult<PageResult<PageAttachmentInfoRespVO>> getPageAttachmentInfoPage(@Valid PageAttachmentInfoPageReqVO pageReqVO) {
        PageResult<PageAttachmentInfoDO> pageResult = pageAttachmentInfoService.getPageAttachmentInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PageAttachmentInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出表单页配置-附件管理 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageAttachmentInfoExcel(@Valid PageAttachmentInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageAttachmentInfoDO> list = pageAttachmentInfoService.getPageAttachmentInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "表单页配置-附件管理.xls", "数据", PageAttachmentInfoRespVO.class,
                        BeanUtils.toBean(list, PageAttachmentInfoRespVO.class));
    }

}