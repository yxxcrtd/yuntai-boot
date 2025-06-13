package com.joyintech.yuntai.module.cfg.controller.admin.SubTableSetting.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 子表设置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubTableSettingPageReqVO extends PageParam {

    @Schema(description = "编辑模式")
    private String editMode;

    @Schema(description = "是否必填")
    private String isRequired;

    @Schema(description = "是否显示序号")
    private String isShowIndex;

    @Schema(description = "是否显示合计")
    private String isShowTotal;

    @Schema(description = "页面设置ID")
    private String pageId;

    @Schema(description = "页面apiID")
    private String pageApiId;

    @Schema(description = "表格id")
    private String tableId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "子表标题")
    private String tableTitle;

    @Schema(description = "默认数据")
    private String defaultData;

    @Schema(description = "显示行号")
    private String tableSort;

    @Schema(description = "每页行号默认起始")
    private String tableBeginIndex;

    @Schema(description = "斑马纹")
    private String tableStripes;

    @Schema(description = "是否显示复选框")
    private String isShowCheckBox;

    @Schema(description = "固定操作列")
    private String tableFixedAction;

    @Schema(description = "操作列位置")
    private String tableActionPostion;

    @Schema(description = "是否支持分页")
    private String isPageList;

    @Schema(description = "默认分页大小")
    private String tableDefPageSize;
}