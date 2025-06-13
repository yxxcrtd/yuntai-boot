package com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain.vo;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 数据库字段类型表(数据域) Response VO")
@Data
@ExcelIgnoreUnannotated
public class DbDataDomainRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1682")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "类型名", example = "1")
    @ExcelProperty("类型名")
    private String dataType;

    @Schema(description = "数据库类型（MySQL）;设计：其他数据库根据MySQL类型代码中做映射", example = "2")
    @ExcelProperty("数据库类型（MySQL）;设计：其他数据库根据MySQL类型代码中做映射")
    private String dbType;

    @Schema(description = "长度")
    @ExcelProperty("长度")
    private Integer dataLength;

    @Schema(description = "小数位数")
    @ExcelProperty("小数位数")
    private Integer dataScale;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}