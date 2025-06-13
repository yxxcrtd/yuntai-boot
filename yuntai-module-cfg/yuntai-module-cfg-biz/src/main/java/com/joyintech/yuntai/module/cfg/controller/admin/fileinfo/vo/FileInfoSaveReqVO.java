package com.joyintech.yuntai.module.cfg.controller.admin.fileinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.ExcelProperty;

@Schema(description = "管理后台 - 上传附件新增/修改 Request VO")
@Data
public class FileInfoSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32532")
    private Long id;

    @Schema(description = "列表页ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27499")
    @NotNull(message = "列表页ID不能为空")
    private Long pageId;

    @Schema(description = "流程id", example = "29914")
    private String flowId;

    @Schema(description = "流程节点id", example = "23358")
    private String flowNodeId;

    @Schema(description = "附件文件类型", example = "1")
    private String fileType;

    @Schema(description = "附件文件名称", example = "王五")
    private String fileName;

    @Schema(description = "上传人", example = "25771")
    private String uploadUserId;

    @Schema(description = "上传时间")
    private LocalDateTime uploadDate;

    @Schema(description = "是否必传")
    private Long isRequire;

    @Schema(description = "文件上传统一ID", example = "22272")
    private String fileId;

    @Schema(description = "文件地址", example = "https://www.joyintech.com")
    private String fileUrl;

    @Schema(description = "文件大小")
    private BigDecimal fileSize;

    @Schema(description = "附件类型", example = "22272")
    @ExcelProperty("附件类型")
    private String attachmentType;

    @Schema(description = "附件文件类型", example = "29914")
    private String fileTypeText;

    @Schema(description = "附件id", example = "29914")
    private String oriAttachmentId;

    @Schema(description = "是否对外披露")
    private boolean isPublic;

    @Schema(description = "文件标题")
    private String fileTitle;
}