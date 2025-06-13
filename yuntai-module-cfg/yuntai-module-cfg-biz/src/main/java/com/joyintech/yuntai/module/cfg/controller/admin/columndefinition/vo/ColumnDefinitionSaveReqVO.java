package com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.ComponentAttributeSaveReqVO;
import com.joyintech.yuntai.module.cfg.enums.CfgConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Schema(description = "管理后台 - 字段定义新增/修改 Request VO")
@Data
public class ColumnDefinitionSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3712")
    private Long id;

    @Schema(description = "定义表主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23055")
    @NotNull(message = "定义表主键不能为空")
    private Long tableId;

    @Schema(description = "字段名", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "字段名不能为空")
    @Pattern(regexp = CfgConstants.TABLE_NAME_PATTERN, message = "字段名必须以字母开头，并且只能包含字母、数字和下划线，长度不超过32")
    private String columnName;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "名称不能为空")
    private String columnComment;

    @Schema(description = "数据域主键", example = "15680")
    private Long dataDomainId;

    @Schema(description = "数据库类型（MySQL）", requiredMode = Schema.RequiredMode.REQUIRED, example = "mysql")
    private String typeSource;

    @Schema(description = "数据类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "varchar")
    @NotEmpty(message = "数据类型不能为空")
    private String columnType;

    @Schema(description = "长度")
    private Integer columnLength;

    @Schema(description = "小数位")
    private Integer columnScale;

    @Schema(description = "默认值")
    private String defaultValue;

    @Schema(description = "是否主键")
    private Boolean isPrimaryKey;

    @Schema(description = "非空")
    private Boolean isNotNull;

    @Schema(description = "自增")
    private Boolean isAutoIncrement;

    @Schema(description = "是否系统字段")
    private Boolean isSys;

    @Schema(description = "Java类型")
    private String javaType;

    @Schema(description = "jdbcType", example = "VARCHAR")
    private String jdbcType;

    @Schema(description = "是否生效false-未生效,true-已生效")
    private Boolean status;

    @Schema(description = "排序字段")
    private Integer sort;

    @Schema(description = "组件")
    private String componentCode;

    @Schema(description = "组件属性集合")
    private List<ComponentAttributeSaveReqVO> propsList;

}
