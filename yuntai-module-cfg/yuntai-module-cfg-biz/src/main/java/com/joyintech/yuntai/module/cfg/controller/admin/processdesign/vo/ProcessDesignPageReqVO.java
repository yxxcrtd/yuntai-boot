package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 流程设计分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcessDesignPageReqVO extends PageParam {

    @Schema(description = "菜单ID", example = "22504")
    private Long menuId;

    @Schema(description = "流程名称", example = "云台")
    private String processName;

    @Schema(description = "关联表单id")
    private Long pageId;

    @Schema(description = "流程id")
    private String flowId;

    @Schema(description = "流程类型(内外)", example = "1")
    private String processType;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
