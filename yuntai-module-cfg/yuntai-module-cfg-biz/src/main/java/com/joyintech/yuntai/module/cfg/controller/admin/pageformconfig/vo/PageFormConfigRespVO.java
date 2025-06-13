package com.joyintech.yuntai.module.cfg.controller.admin.pageformconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 表单页配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageFormConfigRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "174")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "页面ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1246")
    @ExcelProperty("页面ID")
    private Long pageId;

    @Schema(description = "字段名", example = "云台")
    @ExcelProperty("字段名")
    private String columnName;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String columnTitle;

    @Schema(description = "所属模型")
    @ExcelProperty("所属模型")
    private String columnAlignment;

    @Schema(description = "显示组件")
    @ExcelProperty("显示组件")
    private String columnComponent;

    @Schema(description = "是否必填")
    @ExcelProperty("是否必填")
    private String isRequired;

    @Schema(description = "占位符")
    @ExcelProperty("占位符")
    private String columnPlaceholder;

    @Schema(description = "tips")
    @ExcelProperty("tips")
    private String columnTips;

    @Schema(description = "宽度")
    @ExcelProperty("宽度")
    private String columnWidth;

    @Schema(description = "插槽")
    @ExcelProperty("插槽")
    private String columnSlot;

    @Schema(description = "组件配置;暂定，若实际编码时需要可设计为独立表")
    @ExcelProperty("组件配置;暂定，若实际编码时需要可设计为独立表")
    private String componentConfig;

    @Schema(description = "校验规则;暂定，若实际编码时需要可设计为独立表")
    @ExcelProperty("校验规则;暂定，若实际编码时需要可设计为独立表")
    private String checkRuleConfig;

    @Schema(description = "联动配置;暂定，若实际编码时需要可设计为独立表")
    @ExcelProperty("联动配置;暂定，若实际编码时需要可设计为独立表")
    private String linkedConfig;

    @Schema(description = "事件配置;暂定，若实际编码时需要可设计为独立表")
    @ExcelProperty("事件配置;暂定，若实际编码时需要可设计为独立表")
    private String eventConfig;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

}