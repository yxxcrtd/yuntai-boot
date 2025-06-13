package com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 条件分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ConditionalTablePageReqVO extends PageParam {

    @Schema(description = "页面/节点关联ID", example = "20470")
    private Long relevanceId;

    @Schema(description = "条件类型(1->初始化；2->运行时)")
    private String type;

    @Schema(description = "设置类型")
    private String setType;

    @Schema(description = "脚本")
    private String script;

    @Schema(description = "条件内容")
    private String content;

    @Schema(description = "前端传值内容")
    private Object condition;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "显示类型")
    private List<String> showPageType;

    @Schema(description = "显示类型")
    private String showPageTypeCode;

    @Schema(description = "赋值为")
    private String fillValue;

    @Schema(description = "切换为")
    private String columnDisplayComponent;

    private String columnDisplayComponentName;
}