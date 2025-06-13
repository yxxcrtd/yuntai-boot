package com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 流程Log日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcessDataPageReqVO extends PageParam {

    @Schema(description = "页面id", example = "6959")
    private Long pageId;

    @Schema(description = "流程id", example = "15551")
    private String flowId;

    @Schema(description = "流程实例", example = "16051")
    private String flowRequestId;

    @Schema(description = "流程节点id", example = "19545")
    private String nodeId;

    @Schema(description = "数据id", example = "24504")
    private String formId;

    @Schema(description = "数据明细")
    private String formData;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}