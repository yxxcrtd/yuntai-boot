package com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 表单页配置-附件管理-指定上传文件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageAttachmentUploadfileRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7681")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "附件管理ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16753")
    @ExcelProperty("附件管理ID")
    private Long attachmentId;

    @Schema(description = "文件名称", example = "李四")
    @ExcelProperty("文件名称")
    private String fileTypeId;

    @Schema(description = "文件名称", example = "李四")
    @ExcelProperty("文件名称")
    private String fileTypeText;

    @Schema(description = "是否必传")
    @ExcelProperty("是否必传")
    private Integer isRequire;

    @Schema(description = "是否校验文件名", example = "云台")
    @ExcelProperty("是否校验文件名")
    private Integer isValidFileName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "流程节点Id")
    private String flowNodeId;

    @Schema(description = "文件名校验规则（正则）")
    private String validRule;
}