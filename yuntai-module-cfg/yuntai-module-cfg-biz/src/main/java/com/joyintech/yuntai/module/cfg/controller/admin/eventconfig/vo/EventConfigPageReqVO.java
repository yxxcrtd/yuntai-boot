package com.joyintech.yuntai.module.cfg.controller.admin.eventconfig.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 页面事件配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EventConfigPageReqVO extends PageParam {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}