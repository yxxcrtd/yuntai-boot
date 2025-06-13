package com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;


@Schema(description = "管理后台 -通用保存 Request VO")
@Data
public class LowCodeReqVO {
    @Schema(description = "模型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10572")
    @NotNull(message = "模型id不能为空")
    private Long moduleId;

    @Schema(description = "API CODE", requiredMode = Schema.RequiredMode.REQUIRED, example = "10572")
    @NotNull(message = "API code不能为空")
    private String serviceCode;

    @Schema(description = "管理后台 -通用参数")
    private Map<String, Object> params;

}




