package com.joyintech.yuntai.module.cfg.controller.admin.appinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 多应用分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppInfoPageReqVO extends PageParam {

    @Schema(description = "应用名称", example = "王五")
    private String appName;

    @Schema(description = "应用编码")
    private String appCode;

    @Schema(description = "应用地址")
    private String appAddress;

    @Schema(description = "应用容器")
    private String container;

    @Schema(description = "状态(0->开启1>停用)", example = "2")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}