package com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.joyintech.yuntai.framework.common.pojo.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 表单页配置-附件管理-指定上传文件分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageAttachmentUploadfilePageReqVO extends PageParam {

    @Schema(description = "附件管理ID", example = "16753")
    private Long attachmentId;

    @Schema(description = "文件名称", example = "李四")
    private String fileTypeId;

    @Schema(description = "文件名称", example = "李四")
    private String fileTypeText;

    @Schema(description = "是否必传")
    private Integer isRequire;

    @Schema(description = "是否校验文件名", example = "云台")
    private Integer isValidFileName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "流程节点Id")
    private String flowNodeId;

    @Schema(description = "文件名校验规则（正则）")
    private String validRule;
}