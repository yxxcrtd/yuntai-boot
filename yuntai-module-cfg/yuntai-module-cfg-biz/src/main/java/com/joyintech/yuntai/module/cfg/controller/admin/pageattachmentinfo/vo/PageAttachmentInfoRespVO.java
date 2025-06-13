package com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 表单页配置-附件管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageAttachmentInfoRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17539")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "附件模式", example = "1")
    @ExcelProperty("附件模式")
    private String attachmentType;

    @Schema(description = "是否必传")
    @ExcelProperty("是否必传")
    private String isRequire;

    @Schema(description = "是否允许下载")
    @ExcelProperty("是否允许下载")
    private String isAllowDownload;

    @Schema(description = "是否允许预览")
    @ExcelProperty("是否允许预览")
    private String isAllowPreview;

    @Schema(description = "允许上传的文件类型")
    @ExcelProperty("允许上传的文件类型")
    private String allowFileSuffix;

    @Schema(description = "允许上传的文件类型")
    private String allowFileSuffixCode;

    @Schema(description = "上传文件大小范围")
    @ExcelProperty("上传文件大小范围")
    private String allowSize;

    @Schema(description = "上传文件大小范围")
    private String allowSizeCode;

    @Schema(description = "文件类型（字典）")
    private String fileSourceDict;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "列表页ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17931")
    @ExcelProperty("列表页ID")
    private Long pageId;

    @Schema(description = "是否允许选择多个文件")
    private String isAllowMultiple;

    @Schema(description = "最大文件限制")
    private BigDecimal maxSize;

    @Schema(description = "最大文件限制单位")
    private String maxSizeUnit;

    @Schema(description = "上传提示语")
    private String uploadTips;
}