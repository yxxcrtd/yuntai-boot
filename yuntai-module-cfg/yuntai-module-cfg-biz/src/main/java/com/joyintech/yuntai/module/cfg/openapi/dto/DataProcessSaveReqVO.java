package com.joyintech.yuntai.module.cfg.openapi.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "表单发起入参")
@Data
public class DataProcessSaveReqVO {

    @Schema(description = "用户ID")
    @NotEmpty(message = "用户不能为空")
    private String userId;

    @Schema(description = "用户编码")
    private String workNo;

    @Schema(description = "部门ID")
    private String deptId;

    @Schema(description = "流程ID")
    @NotEmpty(message = "流程不能为空")
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

    @Schema(description = "业务编码")
    private String ywbm;

    @Schema(description = "合同id")
    private String contId;

    @Schema(description = "子表id")
    private List<String> childIdList;

    @Schema(description = "借据编号")
    private String reciptNumber;

    @Schema(description = "划款单ID")
    private String hkdId;

    @Schema(description = "产品id")
    private String prodId;

}
