package com.joyintech.yuntai.module.cfg.controller.admin.validaterules.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 页面校验规则 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ValidateRulesRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28810")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "列表配置ID", example = "28605")
    private Long pageConfigId;

    @Schema(description = "校验名称", example = "李四")
    @ExcelProperty("校验名称")
    private String checkName;

    @Schema(description = "是否必填")
    private String isSelect;

    @Schema(description = "校验类型")
    private String validateType;

    @Schema(description = "提示信息")
    @ExcelProperty("提示信息")
    private String toolTips;

    @Schema(description = "规则定义")
    private Object condition;

    @Schema(description = "规则定义")
    private String rulesName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "区间配置类型")
    @ExcelProperty("区间配置类型")
    private String rangeType;


}