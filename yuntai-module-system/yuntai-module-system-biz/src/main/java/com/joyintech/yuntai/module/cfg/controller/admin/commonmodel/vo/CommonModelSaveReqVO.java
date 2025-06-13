package com.joyintech.yuntai.module.cfg.controller.admin.commonmodel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 公共模型新增/修改 Request VO")
@Data
public class CommonModelSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21028")
    private Long id;

    @Schema(description = "模型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "模型编码不能为空")
    private String modelCode;

    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "模型名称不能为空")
    private String modelName;

    @Schema(description = "备注")
    private String remark;

}