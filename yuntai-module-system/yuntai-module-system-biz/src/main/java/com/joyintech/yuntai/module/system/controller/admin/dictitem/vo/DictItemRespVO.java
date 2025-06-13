package com.joyintech.yuntai.module.system.controller.admin.dictitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 字典子表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DictItemRespVO {

    @Schema(description = "", example = "19284")
    @ExcelProperty("")
    private String id;

    @Schema(description = "", example = "2639")
    @ExcelProperty("")
    private String dictId;

    @Schema(description = "")
    @ExcelProperty("")
    private String dictCode;

    @Schema(description = "")
    @ExcelProperty("")
    private String itemText;

    @Schema(description = "")
    @ExcelProperty("")
    private String itemValue;

    @Schema(description = "", example = "2")
    @ExcelProperty("")
    private String filterType;

    @Schema(description = "", example = "随便")
    @ExcelProperty("")
    private String description;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer sortOrder;

    @Schema(description = "", example = "2")
    @ExcelProperty("")
    private Integer status;

    @Schema(description = "")
    @ExcelProperty("")
    private String createBy;

    @Schema(description = "")
    @ExcelProperty("")
    private LocalDateTime createTime;

    @Schema(description = "")
    @ExcelProperty("")
    private String updateBy;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer delFlag;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer tenantCode;

    @Schema(description = "")
    @ExcelProperty("")
    private String extText1;

}