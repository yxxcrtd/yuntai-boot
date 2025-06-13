package com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 列表页配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageListConfigRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18854")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "页面ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32020")
    @ExcelProperty("页面ID")
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

    @Schema(description = "模型关联表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28605")
    @ExcelProperty("模型关联表ID")
    private Long moduleTableId;

    @Schema(description = "字段ID", example = "28605")
    private Long fieldId;

    @Schema(description = "数据库表ID", example = "28605")
    private Long tableId;

    @Schema(description = "列字段", example = "赵六")
    @ExcelProperty("列字段")
    private String columnName;

    @Schema(description = "列名称")
    @ExcelProperty("列名称")
    private String columnComment;

    @Schema(description = "字段名称别名")
    @ExcelProperty("字段名称别名")
    private String columnNameAlias;

    @Schema(description = "字段别名")
    @ExcelProperty("字段别名")
    private String columnFieldAlias;

    @Schema(description = "默认值")
    private String defValue;

    @Schema(description = "占据列数")
    private Long columnSpan;

    @Schema(description = "所属分组", requiredMode = Schema.RequiredMode.REQUIRED, example = "15995")
    @ExcelProperty("所属分组")
    private Long columnGroupId;

    @Schema(description = "对齐方式")
    @ExcelProperty("对齐方式")
    private String columnAlignment;

    @Schema(description = "固定宽度")
    @ExcelProperty("固定宽度")
    private String columnFixedWidth;

    @Schema(description = "最小宽度")
    @ExcelProperty("最小宽度")
    private String columnMinWidth;

    @Schema(description = "字段排序")
    private Integer sort;

    @Schema(description = "是否显示")
    @ExcelProperty("是否显示")
    private Integer isVisible;

    @Schema(description = "是否固定列")
    @ExcelProperty("是否固定列")
    private String isColumnFixed;

    @Schema(description = "插槽")
    @ExcelProperty("插槽")
    private String columnSlot;

    @Schema(description = "是否合计列")
    @ExcelProperty("是否合计列")
    private Integer isTotalColumn;

    @Schema(description = "是否支持排序")
    @ExcelProperty("是否支持排序")
    private Integer isColumnSort;

    @Schema(description = "是否必填")
    private Integer isRequire;

    @Schema(description = "是否置灰")
    private Integer isDisabled;

    @Schema(description = "默认隐藏")
    private Integer isHidden;

    @Schema(description = "表头tips")
    @ExcelProperty("表头tips")
    private String columnHeadTips;

    @Schema(description = "表头插槽")
    @ExcelProperty("表头插槽")
    private String columnHeadSlot;

    @Schema(description = "显示组件")
    private String columnDisplayComponent;

    @Schema(description = "字段标签")
    private List<String > columnTag;

    @Schema(description = "字段标签存储")
    private String columnTagCode;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "标签长度")
    private Integer columnHeadWidth;

    @Schema(description = "字段标签配置")
    private String columnTagConfig;
}
