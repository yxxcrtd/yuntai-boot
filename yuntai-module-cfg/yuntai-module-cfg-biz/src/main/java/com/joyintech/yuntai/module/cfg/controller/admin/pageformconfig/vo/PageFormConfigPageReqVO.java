package com.joyintech.yuntai.module.cfg.controller.admin.pageformconfig.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 表单页配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageFormConfigPageReqVO extends PageParam {

    @Schema(description = "页面ID", example = "1246")
    private Long pageId;

    @Schema(description = "字段名", example = "云台")
    private String columnName;

    @Schema(description = "标题")
    private String columnTitle;

    @Schema(description = "所属模型")
    private String columnAlignment;

    @Schema(description = "显示组件")
    private String columnComponent;

    @Schema(description = "是否必填")
    private String isRequired;

    @Schema(description = "占位符")
    private String columnPlaceholder;

    @Schema(description = "tips")
    private String columnTips;

    @Schema(description = "宽度")
    private String columnWidth;

    @Schema(description = "插槽")
    private String columnSlot;

    @Schema(description = "组件配置;暂定，若实际编码时需要可设计为独立表")
    private String componentConfig;

    @Schema(description = "校验规则;暂定，若实际编码时需要可设计为独立表")
    private String checkRuleConfig;

    @Schema(description = "联动配置;暂定，若实际编码时需要可设计为独立表")
    private String linkedConfig;

    @Schema(description = "事件配置;暂定，若实际编码时需要可设计为独立表")
    private String eventConfig;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}