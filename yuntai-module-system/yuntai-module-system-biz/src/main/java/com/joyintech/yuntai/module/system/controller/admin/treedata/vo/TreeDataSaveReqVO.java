package com.joyintech.yuntai.module.system.controller.admin.treedata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 字典树子新增/修改 Request VO")
@Data
public class TreeDataSaveReqVO {

    @Schema(description = "", example = "22875")
    private String id;

    @Schema(description = "", example = "2")
    private String treeType;

    @Schema(description = "")
    private String nodeCode;

    @Schema(description = "")
    private String nodeText;

    @Schema(description = "")
    private String shortText;

    @Schema(description = "")
    private Integer nodeLevel;

    @Schema(description = "")
    private String parentCode;

    @Schema(description = "")
    private Integer sortIndex;

    @Schema(description = "")
    private String createBy;

    @Schema(description = "")
    private String updateBy;

    @Schema(description = "", example = "2")
    private Integer status;

    @Schema(description = "")
    private String treeGroup;

    @Schema(description = "", example = "王五")
    private String treeName;

}