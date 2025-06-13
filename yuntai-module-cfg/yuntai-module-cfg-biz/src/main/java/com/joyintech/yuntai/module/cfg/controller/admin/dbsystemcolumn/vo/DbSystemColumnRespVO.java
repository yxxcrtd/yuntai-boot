package com.joyintech.yuntai.module.cfg.controller.admin.dbsystemcolumn.vo;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 数据库系统字段 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DbSystemColumnRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28759")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "字段名", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    @ExcelProperty("字段名")
    private String columnName;

    @Schema(description = "字段注释", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("字段注释")
    private String columnComment;

    @Schema(description = "位置")
    @ExcelProperty("位置")
    private Integer columnPosition;

    @Schema(description = "数据域主键", example = "9902")
    @ExcelProperty("数据域主键")
    private Long dataDomainId;

    @Schema(description = "数据库类型（MySQL）", requiredMode = Schema.RequiredMode.REQUIRED, example = "mysql")
    @ExcelProperty("数据库类型（MySQL）")
    private String typeSource;

    @Schema(description = "数据类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "VARCHAR")
    @ExcelProperty("数据类型")
    private String columnType;

    @Schema(description = "长度")
    @ExcelProperty("长度")
    private Integer columnLength;

    @Schema(description = "小数位数")
    @ExcelProperty("小数位数")
    private Integer columnScale;

    @Schema(description = "默认值")
    @ExcelProperty("默认值")
    private String defaultValue;

    @Schema(description = "是否主键")
    @ExcelProperty("是否主键")
    private Boolean isPrimaryKey;

    @Schema(description = "非空")
    @ExcelProperty("非空")
    private Boolean isNotNull;

    @Schema(description = "自增")
    @ExcelProperty("自增")
    private Boolean isAutoIncrement;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "字段分类:0-新增,1-删除2-更新，3-主键", example = "你猜")
    private String category;

    @Schema(description = "是否是逻辑删除字段", example = "你猜")
    private String deletedField;

    @Schema(description = "删除的值", example = "你猜")
    private String deletedValue;

    @Schema(description = "未删除的值", example = "你猜")
    private String notDeletedValue;

    private String columnTypeWithId;
}
