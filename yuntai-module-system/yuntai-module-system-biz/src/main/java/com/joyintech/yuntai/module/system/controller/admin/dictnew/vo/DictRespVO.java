package com.joyintech.yuntai.module.system.controller.admin.dictnew.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 字典主表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DictRespVO {

    @Schema(description = "", example = "3416")
    @ExcelProperty("")
    private String id;

    @Schema(description = "", example = "1")
    @ExcelProperty("")
    private Integer systemType;

    @Schema(description = "")
    @ExcelProperty("")
    private String dictGroup;

    @Schema(description = "", example = "赵六")
    @ExcelProperty("")
    private String dictName;

    @Schema(description = "")
    @ExcelProperty("")
    private String dictCode;

    @Schema(description = "")
    @ExcelProperty("")
    private String createBy;

    @Schema(description = "")
    @ExcelProperty("")
    private String createTime;

    @Schema(description = "")
    @ExcelProperty("")
    private String updateBy;

    @Schema(description = "", example = "2")
    @ExcelProperty("")
    private Integer type;

    @Schema(description = "", example = "2")
    @ExcelProperty("")
    private String bankType;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer sortIndex;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer editState;

    @Schema(description = "", example = "你说的对")
    @ExcelProperty("")
    private String description;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer delFlag;

    @Schema(description = "", example = "1")
    @ExcelProperty("")
    private String dataType;

    @Schema(description = "")
    @ExcelProperty("")
    private String dictClassify;

    @Schema(description = "", example = "1")
    @ExcelProperty("")
    private Integer dictType;

}