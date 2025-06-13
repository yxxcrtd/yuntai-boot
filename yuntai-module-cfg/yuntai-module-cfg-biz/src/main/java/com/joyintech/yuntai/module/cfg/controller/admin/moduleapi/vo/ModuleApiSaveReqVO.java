package com.joyintech.yuntai.module.cfg.controller.admin.moduleapi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 模型API新增/修改 Request VO")
@Data
public class ModuleApiSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11933")
    private Long id;

    @Schema(description = "服务名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "服务名称不能为空")
    private String serviceName;

    @Schema(description = "服务编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "服务编码不能为空")
    private String serviceCode;

    @Schema(description = "模型或表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1341")
    @NotNull(message = "模型id不能为空")
    private Long moduleId;

    @Schema(description = "服务方式", example = "2")
    private String serviceType;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}
