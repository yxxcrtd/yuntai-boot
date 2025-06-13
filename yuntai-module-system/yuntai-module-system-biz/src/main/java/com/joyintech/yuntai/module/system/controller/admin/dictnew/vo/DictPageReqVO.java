package com.joyintech.yuntai.module.system.controller.admin.dictnew.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 字典主表分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DictPageReqVO extends PageParam {

    @Schema(description = "", example = "1")
    private Integer systemType;

    @Schema(description = "")
    private String dictGroup;

    @Schema(description = "", example = "赵六")
    private String dictName;

    @Schema(description = "")
    private String dictCode;

    @Schema(description = "")
    private String createBy;

    @Schema(description = "")
    private String createTime;

    @Schema(description = "")
    private String updateBy;

    @Schema(description = "", example = "2")
    private Integer type;

    @Schema(description = "", example = "2")
    private String bankType;

    @Schema(description = "")
    private Integer sortIndex;

    @Schema(description = "")
    private Integer editState;

    @Schema(description = "", example = "你说的对")
    private String description;

    @Schema(description = "")
    private Integer delFlag;

    @Schema(description = "", example = "1")
    private String dataType;

    @Schema(description = "")
    private String dictClassify;

    @Schema(description = "", example = "1")
    private Integer dictType;

}