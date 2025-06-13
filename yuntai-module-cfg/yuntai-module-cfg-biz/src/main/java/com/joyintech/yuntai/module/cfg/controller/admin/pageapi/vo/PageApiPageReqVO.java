package com.joyintech.yuntai.module.cfg.controller.admin.pageapi.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 页面api分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageApiPageReqVO extends PageParam {

    @Schema(description = "页面ID", example = "9079")
    private Long pageId;

    @Schema(description = "apiID")
    private Long apiId;

    @Schema(description = "数据模型", example = "11753")
    private Long moduleId;

    @Schema(description = "服务ID", example = "1318")
    private Long serverId;

    @Schema(description = "api编码")
    private String apiCode;

    @Schema(description = "api名称", example = "李四")
    private String apiName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}