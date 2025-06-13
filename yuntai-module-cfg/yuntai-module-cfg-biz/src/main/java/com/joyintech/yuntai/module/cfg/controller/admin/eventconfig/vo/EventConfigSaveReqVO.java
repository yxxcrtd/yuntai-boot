package com.joyintech.yuntai.module.cfg.controller.admin.eventconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 页面事件配置新增/修改 Request VO")
@Data
public class EventConfigSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14882")
    private Long id;

    @Schema(description = "列表配置ID", example = "28605")
    private Long pageConfigId;

    @Schema(description = "事件名称", example = "王五")
    private String eventName;

    @Schema(description = "事件类型")
    private String eventType;

    @Schema(description = "配置图标")
    private String icon;

    @Schema(description = "事件内容")
    private String script;

}