package com.joyintech.yuntai.module.cfg.controller.admin.dataconversion.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 数据转换分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DataConversionPageReqVO extends PageParam {

    @Schema(description = "列表配置页ID", example = "31073")
    private Long listConfigId;

    @Schema(description = "数据转换类型", example = "2")
    private String dataType;

    @Schema(description = "数据转换内容")
    private String dataContent;

    @Schema(description = "数据转换正则表达式")
    private String dataRegular;

    @Schema(description = "数据转换脚本")
    private String dataScript;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}