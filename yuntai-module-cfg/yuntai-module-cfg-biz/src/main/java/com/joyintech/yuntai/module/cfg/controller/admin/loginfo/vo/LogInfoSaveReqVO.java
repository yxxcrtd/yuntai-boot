package com.joyintech.yuntai.module.cfg.controller.admin.loginfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 日志记录新增/修改 Request VO")
@Data
public class LogInfoSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30409")
    private Long id;

    @Schema(description = "表单ID", example = "29257")
    private Long formId;

    @Schema(description = "流程ID", example = "6992")
    private String workFlowId;

    @Schema(description = "实例ID", example = "21441")
    private String requestId;

    @Schema(description = "业务流程主键")
    private String serialNum;

    @Schema(description = "表单内容")
    private String content;

}