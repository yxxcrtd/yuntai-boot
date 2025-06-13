package com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.joyintech.yuntai.framework.common.pojo.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 列表页配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageListConfigPageReqVO extends PageParam {

    @Schema(description = "页面ID", example = "32020")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "子表ID")
    private Long childTableId;

    @Schema(description = "api编码")
    private String apiCode;

    @Schema(description = "分组编码")
    private String groupCode;

    @Schema(description = "数据库字典")
    private String columnDictType;

    @Schema(description = "模型关联表ID", example = "28605")
    private Long moduleTableId;

    @Schema(description = "字段ID", example = "28605")
    private Long fieldId;

    @Schema(description = "数据库表ID", example = "28605")
    private Long tableId;

    @Schema(description = "列字段", example = "赵六")
    private String columnName;

    @Schema(description = "列名称")
    private String columnComment;

    @Schema(description = "字段名称别名")
    private String columnNameAlias;

    @Schema(description = "字段别名")
    private String columnFieldAlias;

    @Schema(description = "默认值")
    private String defValue;

    @Schema(description = "占据列数")
    private Long columnSpan;

    @Schema(description = "所属分组", example = "15995")
    private Long columnGroupId;

    @Schema(description = "对齐方式")
    private String columnAlignment;

    @Schema(description = "固定宽度")
    private String columnFixedWidth;

    @Schema(description = "最小宽度")
    private String columnMinWidth;

    @Schema(description = "字段排序")
    private Integer sort;

    @Schema(description = "是否显示")
    private Integer isVisible;

    @Schema(description = "是否固定列")
    private String isColumnFixed;

    @Schema(description = "插槽")
    private String columnSlot;

    @Schema(description = "是否合计列")
    private Integer isTotalColumn;

    @Schema(description = "是否支持排序")
    private Integer isColumnSort;

    @Schema(description = "是否必填")
    private Integer isRequire;

    @Schema(description = "是否置灰")
    private Integer isDisabled;

    @Schema(description = "默认隐藏")
    private Integer isHidden;

    @Schema(description = "表头tips")
    private String columnHeadTips;

    @Schema(description = "表头插槽")
    private String columnHeadSlot;

    @Schema(description = "显示组件")
    private String columnDisplayComponent;

    @Schema(description = "字段标签")
    private List<String > columnTag;

    @Schema(description = "字段标签存储")
    private String columnTagCode;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "标签长度")
    private Integer columnHeadWidth;

    @Schema(description = "字段标签配置")
    private String columnTagConfig;
}
