package com.joyintech.yuntai.module.cfg.controller.admin.componenttable.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 组件分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ComponentTablePageReqVO extends PageParam {

    @Schema(description = "组件分组表ID", example = "92")
    private Long groupId;

    @Schema(description = "组件分组表名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "92")
    private String groupName;

    @Schema(description = "组件名称", example = "赵六")
    private String componentName;

    @Schema(description = "组件编码")
    private String componentCode;

    @Schema(description = "状态（0->开启； 1->停用）", example = "2")
    private Integer status;

    @Schema(description = "组件分组表ID列表")
    private List<Long> groupIds;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
