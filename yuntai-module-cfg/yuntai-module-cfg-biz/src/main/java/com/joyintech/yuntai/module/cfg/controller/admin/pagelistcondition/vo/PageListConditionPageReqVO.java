package com.joyintech.yuntai.module.cfg.controller.admin.pagelistcondition.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 表单页查询条件（待定）分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageListConditionPageReqVO extends PageParam {

    @Schema(description = "页面ID", example = "25180")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "列字段", example = "张三")
    private String columnName;

    @Schema(description = "列名称")
    private String columnComment;

    @Schema(description = "列字段别名")
    private String columnNameAlias;

    @Schema(description = "查询操作符")
    private String columnQueryOperator;

    @Schema(description = "显示组件")
    private String columnDisplayComponent;

    @Schema(description = "显示组件")
    private String columnDisplayComponentName;

    @Schema(description = "数据库字典")
    private String columnDictType;

    @Schema(description = "字段排序")
    private String sort;

    @Schema(description = "默认值")
    private String columnDefault;

    @Schema(description = "是否查询列")
    private String isQueryColumn;

    @Schema(description = "是否必填")
    private String isColumnRequire;

    @Schema(description = "插槽")
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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}