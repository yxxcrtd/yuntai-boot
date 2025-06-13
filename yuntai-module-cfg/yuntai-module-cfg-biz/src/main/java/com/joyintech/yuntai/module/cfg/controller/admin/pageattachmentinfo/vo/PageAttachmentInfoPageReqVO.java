package com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.joyintech.yuntai.framework.common.pojo.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 表单页配置-附件管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageAttachmentInfoPageReqVO extends PageParam {

    @Schema(description = "附件模式", example = "1")
    private String attachmentType;

    @Schema(description = "是否必传")
    private String isRequire;

    @Schema(description = "是否允许下载")
    private String isAllowDownload;

    @Schema(description = "是否允许预览")
    private String isAllowPreview;

    @Schema(description = "允许上传的文件类型")
    private String allowFileSuffix;

    @Schema(description = "允许上传的文件类型")
    private String allowFileSuffixCode;

    @Schema(description = "上传文件大小范围")
    private String allowSize;

    @Schema(description = "上传文件大小范围")
    private String allowSizeCode;

    @Schema(description = "文件类型（字典）")
    private String fileSourceDict;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "列表页ID", example = "17931")
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