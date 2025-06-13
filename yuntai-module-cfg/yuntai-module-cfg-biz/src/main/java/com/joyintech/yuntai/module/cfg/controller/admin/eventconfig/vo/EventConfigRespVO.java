package com.joyintech.yuntai.module.cfg.controller.admin.eventconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 页面事件配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EventConfigRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14882")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "列表配置ID", example = "28605")
    private Long pageConfigId;

    @Schema(description = "事件名称", example = "王五")
    @ExcelProperty("事件名称")
    private String eventName;

    @Schema(description = "事件类型")
    private String eventType;

    @Schema(description = "配置图标")
    private String icon;

    @Schema(description = "事件内容")
    @ExcelProperty("事件内容")
    private String script;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}