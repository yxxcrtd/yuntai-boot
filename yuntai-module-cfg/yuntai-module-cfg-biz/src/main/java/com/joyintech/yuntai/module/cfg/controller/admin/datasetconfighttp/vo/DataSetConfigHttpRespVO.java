package com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 数据集-http请求内容 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DataSetConfigHttpRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4754")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "列表页ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13047")
    @ExcelProperty("列表页ID")
    private Long dataId;

    @Schema(description = "类型", example = "2")
    @ExcelProperty("类型")
    private String type;

    @Schema(description = "请求头/参数名称")
    @ExcelProperty("请求头/参数名称")
    private String keyCode;

    @Schema(description = "内容/参数值")
    @ExcelProperty("内容/参数值")
    private String value;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}