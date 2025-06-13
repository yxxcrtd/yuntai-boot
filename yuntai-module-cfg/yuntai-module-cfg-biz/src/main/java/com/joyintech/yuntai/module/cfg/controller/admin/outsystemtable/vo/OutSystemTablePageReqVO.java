package com.joyintech.yuntai.module.cfg.controller.admin.outsystemtable.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 外部系统关联分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OutSystemTablePageReqVO extends PageParam {

    @Schema(description = "页面ID", example = "29438")
    private Long pageId;

    @Schema(description = "流程ID", example = "14841")
    private String flowId;

    @Schema(description = "实例ID", example = "1745")
    private String flowInstanceId;

    @Schema(description = "类型", example = "2")
    private String flowType;

    @Schema(description = "业务数据ID", example = "21923")
    private String businessId;

    @Schema(description = "流程唯一Key")
    private String pageDataId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}