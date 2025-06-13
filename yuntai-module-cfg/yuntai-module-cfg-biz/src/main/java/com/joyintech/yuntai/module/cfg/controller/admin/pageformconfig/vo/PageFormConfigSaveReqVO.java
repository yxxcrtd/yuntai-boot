package com.joyintech.yuntai.module.cfg.controller.admin.pageformconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 表单页配置新增/修改 Request VO")
@Data
public class PageFormConfigSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "174")
    private Long id;

    @Schema(description = "页面ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1246")
    @NotNull(message = "页面ID不能为空")
    private Long pageId;

    @Schema(description = "字段名", example = "云台")
    private String columnName;

    @Schema(description = "标题")
    private String columnTitle;

    @Schema(description = "所属模型")
    private String columnAlignment;

    @Schema(description = "显示组件")
    private String columnComponent;

    @Schema(description = "是否必填")
    private String isRequired;

    @Schema(description = "占位符")
    private String columnPlaceholder;

    @Schema(description = "tips")
    private String columnTips;

    @Schema(description = "宽度")
    private String columnWidth;

    @Schema(description = "插槽")
    private String columnSlot;

    @Schema(description = "组件配置;暂定，若实际编码时需要可设计为独立表")
    private String componentConfig;

    @Schema(description = "校验规则;暂定，若实际编码时需要可设计为独立表")
    private String checkRuleConfig;

    @Schema(description = "联动配置;暂定，若实际编码时需要可设计为独立表")
    private String linkedConfig;

    @Schema(description = "事件配置;暂定，若实际编码时需要可设计为独立表")
    private String eventConfig;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}