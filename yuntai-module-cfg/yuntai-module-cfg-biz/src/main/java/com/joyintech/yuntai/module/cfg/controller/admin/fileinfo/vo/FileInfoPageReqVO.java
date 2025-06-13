package com.joyintech.yuntai.module.cfg.controller.admin.fileinfo.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.alibaba.excel.annotation.ExcelProperty;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 上传附件分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FileInfoPageReqVO extends PageParam {

    @Schema(description = "列表页ID", example = "27499")
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
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] uploadDate;

    @Schema(description = "是否必传")
    private Long isRequire;

    @Schema(description = "文件上传统一ID", example = "22272")
    private String fileId;

    @Schema(description = "文件地址", example = "https://www.joyintech.com")
    private String fileUrl;

    @Schema(description = "文件大小")
    private BigDecimal fileSize;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "附件类型", example = "22272")
    @ExcelProperty("附件类型")
    private String attachmentType;

    @Schema(description = "附件文件类型", example = "29914")
    private String fileTypeText;

    @Schema(description = "附件id", example = "29914")
    private String oriAttachmentId;
}