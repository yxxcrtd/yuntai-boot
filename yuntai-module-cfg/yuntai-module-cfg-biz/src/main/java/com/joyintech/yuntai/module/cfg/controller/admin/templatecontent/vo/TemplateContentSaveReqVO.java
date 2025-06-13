package com.joyintech.yuntai.module.cfg.controller.admin.templatecontent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 模版内容新增/修改 Request VO")
@Data
public class TemplateContentSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17198")
    private Long id;

    @Schema(description = "模版ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14468")
    @NotNull(message = "模版ID不能为空")
    private Long templateId;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "类型不能为空")
    private Integer type;

    @Schema(description = "api编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "api编码不能为空")
    private String apiCode;

    @Schema(description = "api名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "api名称不能为空")
    private String apiName;

    @Schema(description = "api类型", example = "2")
    private String apiType;

    @Schema(description = "参数名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "参数名称不能为空")
    private String parameterName;

    @Schema(description = "参数编码")
    private String parameterCode;

    @Schema(description = "参数类型", example = "1")
    private String parameterType;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}