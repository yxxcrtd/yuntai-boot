package com.joyintech.yuntai.module.system.controller.admin.dictitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 字典子表新增/修改 Request VO")
@Data
public class DictItemSaveReqVO {

    @Schema(description = "", example = "19284")
    private String id;

    @Schema(description = "", example = "2639")
    private String dictId;

    @Schema(description = "")
    private String dictCode;

    @Schema(description = "")
    private String itemText;

    @Schema(description = "")
    private String itemValue;

    @Schema(description = "", example = "2")
    private String filterType;

    @Schema(description = "", example = "随便")
    private String description;

    @Schema(description = "")
    private Integer sortOrder;

    @Schema(description = "", example = "2")
    private Integer status;

    @Schema(description = "")
    private String createBy;

    @Schema(description = "")
    private String updateBy;

    @Schema(description = "")
    private Integer delFlag;

    @Schema(description = "")
    private Integer tenantCode;

    @Schema(description = "")
    private String extText1;

}