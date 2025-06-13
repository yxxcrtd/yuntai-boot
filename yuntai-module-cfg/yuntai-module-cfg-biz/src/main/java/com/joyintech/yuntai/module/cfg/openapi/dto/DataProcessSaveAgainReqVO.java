package com.joyintech.yuntai.module.cfg.openapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "表单再次发起入参")
@Data
public class DataProcessSaveAgainReqVO {

    @Schema(description = "用户ID")
    @NotEmpty(message = "用户不能为空")
    private String userId;

    @Schema(description = "部门ID")
    private String deptId;

    @Schema(description = "流程ID")
    private String workFlowId;

    @Schema(description = "前置流程ID")
    private String approvalProcess;

    @Schema(description = "表单ID")
    @NotEmpty(message = "表单不能为空")
    private String pageInfoId;

    @Schema(description = "是否跳过首节点")
    private String isnextflow;

    @Schema(description = "业务流程唯一主键")
    private String serialNum;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "流程标题")
    private String requestName;

    @Schema(description = "业务参数")
    private String businessParameter;

    @Schema(description = "紧急程度")
    private String requestLevel;

    @Schema(description = "待提交的流程请求ID")
    @NotNull(message = "流程实例ID不能为空")
    private Integer requestId;

}
