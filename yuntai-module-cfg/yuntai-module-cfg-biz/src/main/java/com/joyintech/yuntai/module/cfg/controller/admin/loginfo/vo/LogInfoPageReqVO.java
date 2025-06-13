package com.joyintech.yuntai.module.cfg.controller.admin.loginfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 日志记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LogInfoPageReqVO extends PageParam {

    @Schema(description = "表单ID", example = "29257")
    private Long formId;

    @Schema(description = "流程ID", example = "6992")
    private String workFlowId;

    @Schema(description = "实例ID", example = "21441")
    private String requestId;

    @Schema(description = "业务流程主键")
    private String serialNum;

    @Schema(description = "表单内容")
    private String content;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}