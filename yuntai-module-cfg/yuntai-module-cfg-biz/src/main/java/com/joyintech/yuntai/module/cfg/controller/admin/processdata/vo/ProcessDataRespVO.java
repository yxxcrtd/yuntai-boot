package com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 流程Log日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProcessDataRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23694")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "页面id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6959")
    @ExcelProperty("页面id")
    private Long pageId;

    @Schema(description = "流程id", example = "15551")
    @ExcelProperty("流程id")
    private String flowId;

    @Schema(description = "流程实例", example = "16051")
    @ExcelProperty("流程实例")
    private String flowRequestId;

    @Schema(description = "流程节点id", example = "19545")
    @ExcelProperty("流程节点id")
    private String nodeId;

    @Schema(description = "数据id", example = "24504")
    @ExcelProperty("数据id")
    private String formId;

    @Schema(description = "数据明细")
    @ExcelProperty("数据明细")
    private String formData;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}