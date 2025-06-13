package com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 数据集-http请求内容分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DataSetConfigHttpPageReqVO extends PageParam {

    @Schema(description = "列表页ID", example = "13047")
    private Long dataId;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "请求头/参数名称")
    private String keyCode;

    @Schema(description = "内容/参数值")
    private String value;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}