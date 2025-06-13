package com.joyintech.yuntai.module.cfg.controller.admin.templatecontent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 模版内容 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TemplateContentRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17198")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "模版ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14468")
    @ExcelProperty("模版ID")
    private Long templateId;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("类型")
    private Integer type;

    @Schema(description = "api编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("api编码")
    private String apiCode;

    @Schema(description = "api名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("api名称")
    private String apiName;

    @Schema(description = "api类型", example = "2")
    @ExcelProperty("api类型")
    private String apiType;

    @Schema(description = "参数名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("参数名称")
    private String parameterName;

    @Schema(description = "参数编码")
    @ExcelProperty("参数编码")
    private String parameterCode;

    @Schema(description = "参数类型", example = "1")
    @ExcelProperty("参数类型")
    private String parameterType;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}