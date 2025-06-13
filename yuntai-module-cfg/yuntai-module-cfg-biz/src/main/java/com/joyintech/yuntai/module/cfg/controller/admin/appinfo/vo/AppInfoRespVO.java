package com.joyintech.yuntai.module.cfg.controller.admin.appinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 多应用 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppInfoRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24919")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "应用名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("应用名称")
    private String appName;

    @Schema(description = "应用编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用编码")
    private String appCode;

    @Schema(description = "应用地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用地址")
    private String appAddress;

    @Schema(description = "应用容器")
    @ExcelProperty("应用容器")
    private String container;

    @Schema(description = "状态(0->开启1>停用)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态(0->开启1>停用)")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}