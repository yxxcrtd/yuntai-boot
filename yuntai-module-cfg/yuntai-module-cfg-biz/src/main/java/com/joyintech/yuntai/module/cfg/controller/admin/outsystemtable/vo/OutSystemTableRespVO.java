package com.joyintech.yuntai.module.cfg.controller.admin.outsystemtable.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 外部系统关联 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OutSystemTableRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15557")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "页面ID", example = "29438")
    @ExcelProperty("页面ID")
    private Long pageId;

    @Schema(description = "流程ID", example = "14841")
    @ExcelProperty("流程ID")
    private String flowId;

    @Schema(description = "实例ID", example = "1745")
    @ExcelProperty("实例ID")
    private String flowInstanceId;

    @Schema(description = "类型", example = "2")
    @ExcelProperty("类型")
    private String flowType;

    @Schema(description = "业务数据ID", example = "21923")
    @ExcelProperty("业务数据ID")
    private String businessId;

    @Schema(description = "流程唯一Key")
    @ExcelProperty("流程唯一Key")
    private String pageDataId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}