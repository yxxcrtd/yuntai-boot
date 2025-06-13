package com.joyintech.yuntai.module.cfg.controller.admin.pagelistcondition.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 表单页查询条件（待定） Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageListConditionRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24070")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "页面ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25180")
    @ExcelProperty("页面ID")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "列字段", example = "张三")
    @ExcelProperty("列字段")
    private String columnName;

    @Schema(description = "列名称")
    @ExcelProperty("列名称")
    private String columnComment;

    @Schema(description = "列字段别名")
    @ExcelProperty("列字段别名")
    private String columnNameAlias;

    @Schema(description = "查询操作符")
    @ExcelProperty("查询操作符")
    private String columnQueryOperator;

    @Schema(description = "显示组件")
    @ExcelProperty("显示组件")
    private String columnDisplayComponent;

    @Schema(description = "显示组件")
    private String columnDisplayComponentName;

    @Schema(description = "数据库字典")
    private String columnDictType;

    @Schema(description = "字段排序")
    private String sort;

    @Schema(description = "默认值")
    @ExcelProperty("默认值")
    private String columnDefault;

    @Schema(description = "是否查询列")
    @ExcelProperty("是否查询列")
    private String isQueryColumn;

    @Schema(description = "是否必填")
    @ExcelProperty("是否必填")
    private String isColumnRequire;

    @Schema(description = "插槽")
    @ExcelProperty("插槽")
    private String columnSlot;

    @Schema(description = "apiID")
    private String apiCode;

    @Schema(description = "分组编码")
    private String groupCode;

    @Schema(description = "子表ID")
    private Long childTableId;

    @Schema(description = "字段ID", example = "28605")
    private Long fieldId;

    @Schema(description = "数据库表ID", example = "28605")
    private Long tableId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}