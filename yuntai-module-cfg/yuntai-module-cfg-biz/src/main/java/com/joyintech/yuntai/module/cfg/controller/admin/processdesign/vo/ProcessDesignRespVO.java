package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 流程设计 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProcessDesignRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24268")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22504")
    @ExcelProperty("菜单ID")
    private Long menuId;

    @Schema(description = "流程名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    @ExcelProperty("流程名称")
    private String processName;

    @Schema(description = "页面id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("页面id")
    private Long pageId;

    @Schema(description = "页面名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("页面名称")
    private String pageName;

    @Schema(description = "流程类型(内外)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("流程类型(内外)")
    private String processType;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "流程id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("流程id")
    private String flowId;

}
