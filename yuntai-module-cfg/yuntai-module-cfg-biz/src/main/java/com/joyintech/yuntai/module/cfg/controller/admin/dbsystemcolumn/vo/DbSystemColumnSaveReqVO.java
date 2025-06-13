package com.joyintech.yuntai.module.cfg.controller.admin.dbsystemcolumn.vo;

import javax.validation.constraints.NotEmpty;

import com.joyintech.yuntai.framework.common.enums.MySqlDbDataTypeEnum;
import com.joyintech.yuntai.framework.common.validation.InEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 数据库系统字段新增/修改 Request VO")
@Data
public class DbSystemColumnSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28759")
    private Long id;

    @Schema(description = "字段名", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    @NotEmpty(message = "字段名不能为空")
    private String columnName;

    @Schema(description = "字段注释", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "字段注释不能为空")
    private String columnComment;

    @Schema(description = "位置")
    private Integer columnPosition;

    @Schema(description = "数据域主键", example = "9902")
    private Long dataDomainId;

    @Schema(description = "数据库类型（MySQL）", requiredMode = Schema.RequiredMode.REQUIRED, example = "VARCHAR")
    @NotEmpty(message = "数据库类型（MySQL）不能为空")
    private String columnType;

    @Schema(description = "长度")
    private Integer columnLength;

    @Schema(description = "小数位数")
    private Integer columnScale;

    @Schema(description = "默认值")
    private String defaultValue;

    @Schema(description = "是否主键")
    private Boolean isPrimaryKey;

    @Schema(description = "非空")
    private Boolean isNotNull;

    @Schema(description = "自增")
    private Boolean isAutoIncrement;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "数据库类型", example = "oracle")
    private String typeSource;
}
