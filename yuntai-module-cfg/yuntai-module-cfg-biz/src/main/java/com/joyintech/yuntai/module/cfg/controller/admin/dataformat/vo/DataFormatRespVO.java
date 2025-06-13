package com.joyintech.yuntai.module.cfg.controller.admin.dataformat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 页面数据格式化 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DataFormatRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1604")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "列表配置页ID", example = "11695")
    @ExcelProperty("列表配置页ID")
    private Long listConfigId;

    @Schema(description = "前缀")
    @ExcelProperty("前缀")
    private String prefix;

    @Schema(description = "后缀")
    @ExcelProperty("后缀")
    private String suffix;

    @Schema(description = "格式化类型", example = "1")
    @ExcelProperty("格式化类型")
    private String formatType;

    @Schema(description = "数字类型", example = "2")
    @ExcelProperty("数字类型")
    private String numType;

    @Schema(description = "保留小数位数")
    @ExcelProperty("保留小数位数")
    private String keepDecimalPlaces;

    @Schema(description = "转换倍率")
    @ExcelProperty("转换倍率")
    private String conversionRate;

    @Schema(description = "千分位符")
    @ExcelProperty("千分位符")
    private Boolean thousandth;

    @Schema(description = "日期格式")
    @ExcelProperty("日期格式")
    private String dateFormat;

    @Schema(description = "图片格式")
    @ExcelProperty("图片格式")
    private String pictureStyle;

    @Schema(description = "链接打开方式")
    @ExcelProperty("链接打开方式")
    private String linkOpenMethod;

    @Schema(description = "链接地址")
    @ExcelProperty("链接地址")
    private String linkAddress;

    @Schema(description = "正则表达式")
    @ExcelProperty("正则表达式")
    private String regularExpression;

    @Schema(description = "组件名称", example = "王五")
    @ExcelProperty("组件名称")
    private String componentName;

    @Schema(description = "脚本")
    private String script;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "回显字段")
    @ExcelProperty("回显字段")
    private String transferLabelField;

}
