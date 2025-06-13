package com.joyintech.yuntai.module.cfg.controller.admin.commonvar.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 公共变量新增/修改 Request VO")
@Data
public class CommonVarSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21028")
    private Long id;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "字段名称不能为空")
    private String fieldName;

    @Schema(description = "字段描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "字段描述不能为空")
    private String fieldDescribe;

    @Schema(description = "缓存类型")
    private String cacheType;

    @Schema(description = "前端/后端类型")
    private String type;

}