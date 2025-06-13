package com.joyintech.yuntai.module.cfg.controller.admin.pageextendevent.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 页面事件扩展配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageExtendEventPageReqVO extends PageParam {

    @Schema(description = "页面id", example = "5831")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "接口地址", example = "https://www.joyintech.com")
    private String interfaceUrl;

    @Schema(description = "接口参数")
    private String interfaceParam;

    @Schema(description = "api编码")
    private String apiCode;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "执行时间点")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] runTime;

    @Schema(description = "是否拦截主操作")
    private Boolean isIntercept;

    @Schema(description = "事件类型：1-前端,2-后端", example = "1")
    private String eventType;

    @Schema(description = "调用类型1-调用方法,2-调用接口,3-执行代码", example = "2")
    private String callType;

    @Schema(description = "接口", example = "云台")
    private String interfaceName;

    @Schema(description = "方法名", example = "王五")
    private String methodName;

    @Schema(description = "代码内容")
    private String executableCode;

}
