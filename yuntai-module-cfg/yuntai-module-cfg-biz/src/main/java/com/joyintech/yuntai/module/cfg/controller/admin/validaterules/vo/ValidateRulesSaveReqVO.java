package com.joyintech.yuntai.module.cfg.controller.admin.validaterules.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 页面校验规则新增/修改 Request VO")
@Data
public class ValidateRulesSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28810")
    private Long id;

    @Schema(description = "列表配置ID", example = "28605")
    private Long pageConfigId;

    @Schema(description = "校验名称", example = "李四")
    private String checkName;

    @Schema(description = "是否必填")
    private String isSelect;

    @Schema(description = "校验类型")
    private String validateType;

    @Schema(description = "提示信息")
    private String toolTips;

    @Schema(description = "规则定义")
    private Object condition;

    @Schema(description = "规则定义")
    private String rulesName;
}