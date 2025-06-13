package com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 页面联动配置新增/修改 Request VO")
@Data
public class PageLinkageSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27961")
    private Long id;

    @Schema(description = "列表配置ID", example = "28605")
    private Long pageConfigId;

    @Schema(description = "配置名称", example = "赵六")
    private String linkageName;

    @Schema(description = "配置类型1->初始化;2->运行时;", example = "2")
    private String linkageType;

    @Schema(description = "配置图标")
    private String icon;

    @Schema(description = "配置方式1->配置;2->脚本;")
    private String linkageWay;

    @Schema(description = "配置内容")
    private String linkageContent;
}