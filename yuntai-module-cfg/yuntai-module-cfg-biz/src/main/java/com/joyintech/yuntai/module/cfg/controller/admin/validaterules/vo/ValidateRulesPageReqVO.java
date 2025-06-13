package com.joyintech.yuntai.module.cfg.controller.admin.validaterules.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 页面校验规则分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ValidateRulesPageReqVO extends PageParam {

    @Schema(description = "列表配置ID", example = "28605")
    private Long pageConfigId;

    @Schema(description = "校验名称", example = "李四")
    private String checkName;

    @Schema(description = "是否必填")
    private String isSelect;

    @Schema(description = "校验类型")
    private String validateType;

    @Schema(description = "提示信息")
    private String toolTips;

    @Schema(description = "规则定义")
    private Object condition;

    @Schema(description = "规则定义")
    private String rulesName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}