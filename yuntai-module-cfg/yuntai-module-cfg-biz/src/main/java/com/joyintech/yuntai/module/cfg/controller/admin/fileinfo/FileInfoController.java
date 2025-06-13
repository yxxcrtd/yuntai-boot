package com.joyintech.yuntai.module.cfg.controller.admin.fileinfo;

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

import com.joyintech.yuntai.module.cfg.controller.admin.fileinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.fileinfo.FileInfoDO;
import com.joyintech.yuntai.module.cfg.service.fileinfo.FileInfoService;

@Tag(name = "管理后台 - 上传附件")
@RestController
@RequestMapping("/cfg/file-info")
@Validated
public class FileInfoController {

    @Resource
    private FileInfoService fileInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建上传附件")
    @PreAuthorize("@ss.hasPermission('cfg:file-info:create')")
    public CommonResult<Long> createFileInfo(@Valid @RequestBody FileInfoSaveReqVO createReqVO) {
        return success(fileInfoService.createFileInfo(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新上传附件")
    @PreAuthorize("@ss.hasPermission('cfg:file-info:update')")
    public CommonResult<Boolean> updateFileInfo(@Valid @RequestBody FileInfoSaveReqVO updateReqVO) {
        fileInfoService.updateFileInfo(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除上传附件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:file-info:delete')")
    public CommonResult<Boolean> deleteFileInfo(@RequestParam("id") Long id) {
        fileInfoService.deleteFileInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得上传附件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:file-info:query')")
    public CommonResult<FileInfoRespVO> getFileInfo(@RequestParam("id") Long id) {
        FileInfoDO fileInfo = fileInfoService.getFileInfo(id);
        return success(BeanUtils.toBean(fileInfo, FileInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得上传附件分页")
    @PreAuthorize("@ss.hasPermission('cfg:file-info:query')")
    public CommonResult<PageResult<FileInfoRespVO>> getFileInfoPage(@Valid FileInfoPageReqVO pageReqVO) {
        PageResult<FileInfoDO> pageResult = fileInfoService.getFileInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, FileInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出上传附件 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:file-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportFileInfoExcel(@Valid FileInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<FileInfoDO> list = fileInfoService.getFileInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "上传附件.xls", "数据", FileInfoRespVO.class,
                        BeanUtils.toBean(list, FileInfoRespVO.class));
    }

    @GetMapping("/getFileList")
    @Operation(summary = "根据id批量获得上传附件")
    @Parameter(name = "fileIds", description = "文件id集合", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:file-info:query')")
    public CommonResult<List<FileInfoDO>> getFileList(@RequestParam("fileIds") String fileIds) {
        String[] split = fileIds.split(",");
        List<FileInfoDO> attachmentList = fileInfoService.getFileListByFileId(Arrays.asList(split));
        return success(attachmentList);
    }

}