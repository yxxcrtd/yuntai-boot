package com.joyintech.yuntai.module.cfg.controller.admin.commonvar.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 公共变量 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CommonVarRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21028")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("字段名称")
    private String fieldName;

    @Schema(description = "字段描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("字段描述")
    private String fieldDescribe;

    @Schema(description = "缓存类型")
    @ExcelProperty("缓存类型")
    private String cacheType;

    @Schema(description = "前端/后端类型")
    @ExcelProperty("前端/后端类型")
    private String type;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}