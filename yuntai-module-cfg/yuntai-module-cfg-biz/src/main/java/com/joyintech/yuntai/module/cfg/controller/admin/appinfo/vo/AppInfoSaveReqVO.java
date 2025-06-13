package com.joyintech.yuntai.module.cfg.controller.admin.appinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 多应用新增/修改 Request VO")
@Data
public class AppInfoSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24919")
    private Long id;

    @Schema(description = "应用名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "应用名称不能为空")
    private String appName;

    @Schema(description = "应用编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用编码不能为空")
    private String appCode;

    @Schema(description = "应用地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用地址不能为空")
    private String appAddress;

    @Schema(description = "应用容器")
    private String container;

    @Schema(description = "状态(0->开启1>停用)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态(0->开启1>停用)不能为空")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}