package com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo.PageAttachmentUploadfileSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 表单页配置-附件管理新增/修改 Request VO")
@Data
public class PageAttachmentInfoSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17539")
    private Long id;

    @Schema(description = "附件模式", example = "1")
    private String attachmentType;

    @Schema(description = "是否必传")
    private String isRequire;

    @Schema(description = "是否允许下载")
    private String isAllowDownload;

    @Schema(description = "是否允许预览")
    private String isAllowPreview;

    @Schema(description = "允许上传的文件类型")
    private List<String> allowFileSuffix;

    @Schema(description = "允许上传的文件类型")
    private String allowFileSuffixCode;

    @Schema(description = "上传文件大小范围")
    private List<String> allowSize;

    @Schema(description = "上传文件大小范围")
    private String allowSizeCode;

    @Schema(description = "文件类型（字典）")
    private String fileSourceDict;

    @Schema(description = "列表页ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17931")
    @NotNull(message = "列表页ID不能为空")
    private Long pageId;

    @Schema(description = "指定上传文件")
    private List<PageAttachmentUploadfileSaveReqVO> uploadFileList;

    @Schema(description = "是否允许选择多个文件")
    private String isAllowMultiple;

    @Schema(description = "最大文件限制")
    private BigDecimal maxSize;

    @Schema(description = "最大文件限制单位")
    private String maxSizeUnit;

    @Schema(description = "上传提示语")
    private String uploadTips;

    @Schema(description = "上传文件过滤类型配置")
    private List<String> filterFileType;

    @Schema(description = "上传文件过滤类型配置")
    private String filterFileTypeValue;

    @Schema(description = "是否查询业务系统附件")
    private Boolean isQueryBusinessFileList;

    @Schema(description = "附件业务类型")
    private String dirType;

    @Schema(description = "是否禁止上传")
    private Boolean isNotAllowUpload;
}