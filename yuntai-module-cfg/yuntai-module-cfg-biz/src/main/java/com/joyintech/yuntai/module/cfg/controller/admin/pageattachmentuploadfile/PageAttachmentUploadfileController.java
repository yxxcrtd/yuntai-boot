package com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile;

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
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo.PageAttachmentUploadfilePageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo.PageAttachmentUploadfileRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo.PageAttachmentUploadfileSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentuploadfile.PageAttachmentUploadfileDO;
import com.joyintech.yuntai.module.cfg.service.pageattachmentuploadfile.PageAttachmentUploadfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理后台 - 表单页配置-附件管理-指定上传文件")
@RestController
@RequestMapping("/cfg/page-attachment-uploadfile")
@Validated
public class PageAttachmentUploadfileController {

    @Resource
    private PageAttachmentUploadfileService pageAttachmentUploadfileService;

    @PostMapping("/create")
    @Operation(summary = "创建表单页配置-附件管理-指定上传文件")
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-uploadfile:create')")
    public CommonResult<Long> createPageAttachmentUploadfile(@Valid @RequestBody PageAttachmentUploadfileSaveReqVO createReqVO) {
        return success(pageAttachmentUploadfileService.createPageAttachmentUploadfile(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新表单页配置-附件管理-指定上传文件")
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-uploadfile:update')")
    public CommonResult<Boolean> updatePageAttachmentUploadfile(@Valid @RequestBody PageAttachmentUploadfileSaveReqVO updateReqVO) {
        pageAttachmentUploadfileService.updatePageAttachmentUploadfile(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除表单页配置-附件管理-指定上传文件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-uploadfile:delete')")
    public CommonResult<Boolean> deletePageAttachmentUploadfile(@RequestParam("id") Long id) {
        pageAttachmentUploadfileService.deletePageAttachmentUploadfile(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得表单页配置-附件管理-指定上传文件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-uploadfile:query')")
    public CommonResult<PageAttachmentUploadfileRespVO> getPageAttachmentUploadfile(@RequestParam("id") Long id) {
        PageAttachmentUploadfileDO pageAttachmentUploadfile = pageAttachmentUploadfileService.getPageAttachmentUploadfile(id);
        return success(BeanUtils.toBean(pageAttachmentUploadfile, PageAttachmentUploadfileRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得表单页配置-附件管理-指定上传文件分页")
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-uploadfile:query')")
    public CommonResult<PageResult<PageAttachmentUploadfileRespVO>> getPageAttachmentUploadfilePage(@Valid PageAttachmentUploadfilePageReqVO pageReqVO) {
        PageResult<PageAttachmentUploadfileDO> pageResult = pageAttachmentUploadfileService.getPageAttachmentUploadfilePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PageAttachmentUploadfileRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出表单页配置-附件管理-指定上传文件 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:page-attachment-uploadfile:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPageAttachmentUploadfileExcel(@Valid PageAttachmentUploadfilePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PageAttachmentUploadfileDO> list = pageAttachmentUploadfileService.getPageAttachmentUploadfilePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "表单页配置-附件管理-指定上传文件.xls", "数据", PageAttachmentUploadfileRespVO.class,
                        BeanUtils.toBean(list, PageAttachmentUploadfileRespVO.class));
    }

}