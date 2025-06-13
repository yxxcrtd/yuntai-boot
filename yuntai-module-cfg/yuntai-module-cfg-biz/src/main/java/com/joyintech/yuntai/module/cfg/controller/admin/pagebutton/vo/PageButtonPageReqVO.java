package com.joyintech.yuntai.module.cfg.controller.admin.pagebutton.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 页面操作按钮分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageButtonPageReqVO extends PageParam {

    @Schema(description = "按钮类型", example = "1")
    private String buttonType;

    @Schema(description = "操作类型", example = "1")
    private String operationType;

    @Schema(description = "按钮名称", example = "张三")
    private String buttonName;

    @Schema(description = "列表页ID", example = "23130")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "关联服务ID")
    private Long modelServerId;

    @Schema(description = "api编码")
    private String apiCode;

    @Schema(description = "服务参数")
    private List<String> serverParams;

    @Schema(description = "服务参数编码")
    private String serverParamsCode;

    @Schema(description = "按钮样式")
    private String buttonStyle;

    @Schema(description = "按钮图标")
    private String buttonIcon;

    @Schema(description = "按钮类型")
    private String buttonShape;

    @Schema(description = "打开方式")
    private String openWay;

    @Schema(description = "关联页面")
    private String relevancePage;

    @Schema(description = "自定义方法")
    private String customMethod;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "按钮默认参数")
    private String actionDefaultParams;

    @Schema(description = "子表id")
    private Long moduleTableId;
}
