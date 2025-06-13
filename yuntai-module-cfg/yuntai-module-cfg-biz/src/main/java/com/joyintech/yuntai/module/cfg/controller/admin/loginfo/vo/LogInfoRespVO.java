package com.joyintech.yuntai.module.cfg.controller.admin.loginfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 日志记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class LogInfoRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30409")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "表单ID", example = "29257")
    @ExcelProperty("表单ID")
    private Long formId;

    @Schema(description = "流程ID", example = "6992")
    @ExcelProperty("流程ID")
    private String workFlowId;

    @Schema(description = "实例ID", example = "21441")
    @ExcelProperty("实例ID")
    private String requestId;

    @Schema(description = "业务流程主键")
    @ExcelProperty("业务流程主键")
    private String serialNum;

    @Schema(description = "表单内容")
    @ExcelProperty("表单内容")
    private String content;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}