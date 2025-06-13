package com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 流程Log日志新增/修改 Request VO")
@Data
public class ProcessDataSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23694")
    private Long id;

    @Schema(description = "页面id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6959")
    @NotNull(message = "页面id不能为空")
    private Long pageId;

    @Schema(description = "流程id", example = "15551")
    private String flowId;

    @Schema(description = "流程实例", example = "16051")
    private String flowRequestId;

    @Schema(description = "流程节点id", example = "19545")
    private String nodeId;

    @Schema(description = "数据id", example = "24504")
    private String formId;

    @Schema(description = "数据明细")
    private String formData;

}