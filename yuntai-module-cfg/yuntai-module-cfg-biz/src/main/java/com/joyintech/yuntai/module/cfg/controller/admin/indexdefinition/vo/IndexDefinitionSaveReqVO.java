package com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.joyintech.yuntai.module.cfg.enums.CfgConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 索引定义新增/修改 Request VO")
@Data
public class IndexDefinitionSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15749")
    private Long id;

    @Schema(description = "定义表主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10572")
    @NotNull(message = "定义表主键不能为空")
    private Long tableId;

    @Schema(description = "索引名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "idx_new")
    @NotEmpty(message = "索引名称不能为空")
    @Pattern(regexp = CfgConstants.TABLE_NAME_PATTERN, message = "索引必须以字母开头，并且只能包含字母、数字和下划线，长度不超过32")
    private String indexName;

    @Schema(description = "索引列", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "索引列不能为空")
    private String indexColumns;

    @Schema(description = "是否唯一索引")
    private Boolean isUniqueKey;

}