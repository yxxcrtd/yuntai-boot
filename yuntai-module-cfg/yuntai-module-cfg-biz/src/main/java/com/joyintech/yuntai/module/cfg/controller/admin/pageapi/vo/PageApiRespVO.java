package com.joyintech.yuntai.module.cfg.controller.admin.pageapi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 页面api Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageApiRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10363")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "页面ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9079")
    @ExcelProperty("页面ID")
    private Long pageId;

    @Schema(description = "apiID")
    private Long apiId;

    @Schema(description = "数据模型", requiredMode = Schema.RequiredMode.REQUIRED, example = "11753")
    @ExcelProperty("数据模型")
    private Long moduleId;

    @Schema(description = "服务ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1318")
    @ExcelProperty("服务ID")
    private Long serverId;

    @Schema(description = "api编码")
    @ExcelProperty("api编码")
    private String apiCode;

    @Schema(description = "api名称", example = "李四")
    @ExcelProperty("api名称")
    private String apiName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}