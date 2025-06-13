package com.joyintech.yuntai.module.cfg.controller.admin.pageparameter.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 页面参数新增/修改 Request VO")
@Data
public class PageParameterSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32179")
    private Long id;

    @Schema(description = "页面ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32020")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "模版ID")
    private Long templateId;

    @Schema(description = "参数名称", example = "王五")
    private String parameterName;

    @Schema(description = "参数值")
    private String parameterValue;

    @Schema(description = "参数编码")
    private String parameterCode;

    @Schema(description = "参数类型")
    private String parameterType;

}