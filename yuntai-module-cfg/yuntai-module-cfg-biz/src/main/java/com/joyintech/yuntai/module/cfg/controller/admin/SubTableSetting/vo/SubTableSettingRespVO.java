package com.joyintech.yuntai.module.cfg.controller.admin.SubTableSetting.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 子表设置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SubTableSettingRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21028")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "编辑模式", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("编辑模式")
    private String editMode;

    @Schema(description = "是否必填", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否必填")
    private String isRequired;

    @Schema(description = "是否显示序号")
    @ExcelProperty("是否显示序号")
    private String isShowIndex;

    @Schema(description = "是否显示合计")
    @ExcelProperty("是否显示合计")
    private String isShowTotal;

    @Schema(description = "页面设置ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("页面设置ID")
    private String pageId;

    @Schema(description = "页面apiID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("页面apiID")
    private String pageApiId;

    @Schema(description = "表格id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("表格id")
    private String tableId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "子表标题")
    @ExcelProperty("子表标题")
    private String tableTitle;

    @Schema(description = "默认数据")
    @ExcelProperty("默认数据")
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