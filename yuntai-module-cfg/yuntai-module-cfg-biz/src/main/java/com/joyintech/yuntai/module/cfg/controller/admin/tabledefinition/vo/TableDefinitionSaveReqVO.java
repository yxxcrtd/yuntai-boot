package com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.joyintech.yuntai.module.cfg.enums.CfgConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 表定义新增/修改 Request VO")
@Data
public class TableDefinitionSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4134")
    private Long id;

    @Schema(description = "数据源id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4778")
    @NotNull(message = "数据源id不能为空")
    private Long datasourceId;

    @Schema(description = "表名", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    @NotEmpty(message = "表名不能为空")
    @Pattern(regexp = CfgConstants.TABLE_NAME_PATTERN, message = "表名必须以字母开头，并且只能包含字母、数字和下划线，长度不超过32")
    private String tableName;

    @Schema(description = "表注释", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "表注释不能为空")
    private String tableComment;

    @Schema(description = "是否系统表", example = "false")
    private Boolean isSys;

    @Schema(description = "描述", example = "随便")
    private String remark;

    @Schema(description = "表类型")
    private String tableType;

    @Schema(description = "表sql")
    private String tableSql;

}
