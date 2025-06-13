package com.joyintech.yuntai.module.cfg.controller.admin.buttonaction.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 页面按钮动作分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ButtonActionPageReqVO extends PageParam {

    @Schema(description = "页面按钮ID", example = "7323")
    private Long pageButtonId;

    @Schema(description = "页面ID", example = "32530")
    private Long pageId;

    @Schema(description = "动作类型", example = "2")
    private String actionType;

    @Schema(description = "关联服务", example = "2151")
    private String modelServerId;

    @Schema(description = "打开方式")
    private String openWay;

    @Schema(description = "关联页面")
    private String relevancePage;

    @Schema(description = "页面参数")
    private String serverParams;

    @Schema(description = "脚本")
    private String dataScript;

    @Schema(description = "自定义方法")
    private String customMethod;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "关联页面类型", example = "2")
    private String pageType;

    @Schema(description = "跳转地址", example = "2")
    private String relevanceUrl;

    @Schema(description = "新增方式")
    private String newAddType;
}