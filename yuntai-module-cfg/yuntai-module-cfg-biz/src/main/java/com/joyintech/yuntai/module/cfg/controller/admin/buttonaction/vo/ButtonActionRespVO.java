package com.joyintech.yuntai.module.cfg.controller.admin.buttonaction.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 页面按钮动作 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ButtonActionRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9667")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "页面按钮ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7323")
    @ExcelProperty("页面按钮ID")
    private Long pageButtonId;

    @Schema(description = "页面ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32530")
    @ExcelProperty("页面ID")
    private Long pageId;

    @Schema(description = "动作类型", example = "2")
    @ExcelProperty("动作类型")
    private String actionType;

    @Schema(description = "关联服务", example = "2151")
    @ExcelProperty("关联服务")
    private String modelServerId;

    @Schema(description = "打开方式")
    @ExcelProperty("打开方式")
    private String openWay;

    @Schema(description = "关联页面")
    @ExcelProperty("关联页面")
    private String relevancePage;

    @Schema(description = "页面参数")
    @ExcelProperty("页面参数")
    private String serverParams;

    @Schema(description = "脚本")
    @ExcelProperty("脚本")
    private String dataScript;

    @Schema(description = "自定义方法")
    @ExcelProperty("自定义方法")
    private String customMethod;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "关联页面类型", example = "2")
    private String pageType;

    @Schema(description = "跳转地址", example = "2")
    private String relevanceUrl;
}