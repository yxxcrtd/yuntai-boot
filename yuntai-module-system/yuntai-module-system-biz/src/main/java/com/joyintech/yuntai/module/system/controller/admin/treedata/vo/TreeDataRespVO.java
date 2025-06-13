package com.joyintech.yuntai.module.system.controller.admin.treedata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 字典树子 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TreeDataRespVO {

    @Schema(description = "", example = "22875")
    @ExcelProperty("")
    private String id;

    @Schema(description = "", example = "2")
    @ExcelProperty("")
    private String treeType;

    @Schema(description = "")
    @ExcelProperty("")
    private String nodeCode;

    @Schema(description = "")
    @ExcelProperty("")
    private String nodeText;

    @Schema(description = "")
    @ExcelProperty("")
    private String shortText;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer nodeLevel;

    @Schema(description = "")
    @ExcelProperty("")
    private String parentCode;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer sortIndex;

    @Schema(description = "")
    @ExcelProperty("")
    private String createBy;

    @Schema(description = "")
    @ExcelProperty("")
    private String updateBy;

    @Schema(description = "")
    @ExcelProperty("")
    private LocalDateTime createTime;

    @Schema(description = "", example = "2")
    @ExcelProperty("")
    private Integer status;

    @Schema(description = "")
    @ExcelProperty("")
    private String treeGroup;

    @Schema(description = "", example = "王五")
    @ExcelProperty("")
    private String treeName;

}