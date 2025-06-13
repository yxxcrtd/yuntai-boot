package com.joyintech.yuntai.module.cfg.controller.admin.commonmodel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 公共模型 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CommonModelRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21028")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "模型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("模型编码")
    private String modelCode;

    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("模型名称")
    private String modelName;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}