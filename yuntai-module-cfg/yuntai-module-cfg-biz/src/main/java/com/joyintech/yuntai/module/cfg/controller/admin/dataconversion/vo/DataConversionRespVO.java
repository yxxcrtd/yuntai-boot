package com.joyintech.yuntai.module.cfg.controller.admin.dataconversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 数据转换 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DataConversionRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1836")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "列表配置页ID", example = "31073")
    @ExcelProperty("列表配置页ID")
    private Long listConfigId;

    @Schema(description = "数据转换类型", example = "2")
    @ExcelProperty("数据转换类型")
    private String dataType;

    @Schema(description = "数据转换内容")
    @ExcelProperty("数据转换内容")
    private String dataContent;

    @Schema(description = "数据转换正则表达式")
    private String dataRegular;

    @Schema(description = "数据转换脚本")
    @ExcelProperty("数据转换脚本")
    private String dataScript;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}