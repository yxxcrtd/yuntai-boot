package com.joyintech.yuntai.module.cfg.controller.admin.buttonaction.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 页面按钮动作新增/修改 Request VO")
@Data
public class ButtonActionSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9667")
    private Long id;

    @Schema(description = "页面按钮ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7323")
    @NotNull(message = "页面按钮ID不能为空")
    private Long pageButtonId;

    @Schema(description = "页面ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32530")
    @NotNull(message = "页面ID不能为空")
    private Long pageId;

    @Schema(description = "动作类型", example = "2")
    private String actionType;

    @Schema(description = "关联服务", example = "2151")
    private String modelServerId;

    @Schema(description = "打开方式")
    private String openWay;

    @Schema(description = "关联页面")
    private String relevancePage;

    @Schema(description = "页面参数")
    private List<String> serverParams;

    @Schema(description = "脚本")
    private String dataScript;

    @Schema(description = "自定义方法")
    private String customMethod;

    @Schema(description = "关联页面类型", example = "2")
    private String pageType;

    @Schema(description = "跳转地址", example = "2")
    private String relevanceUrl;

    @Schema(description = "新增方式")
    private String newAddType;

}