package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/23
 */
@Data
public class ModuleApiParamVO {
    private Long moduleId;

    @Schema(description = "参数类型：query/request/response", example = "query")
    private String paramType;

    @Schema(description = "apiId", example = "apiId")
    private Long apiId;

    @Schema(description = "cfg_module_table 表的id", example = "111")
    private Long moduleTableId;

    @Schema(description = "字段对应的表名", example = "111")
    private String tableName;

    @Schema(description = "字段id", example = "111")
    private Long fieldId;

    @Schema(description = "字段名称", example = "111")
    private String fieldName;

    @Schema(description = "字段名称", example = "111")
    private String fieldComment;

    @Schema(description = "字段类型", example = "111")
    private String dataType;

    @Schema(description = "是否必填", example = "111")
    private Boolean required;

    @Schema(description = "默认值", example = "111")
    private String defaultValue;

    @Schema(description = "校验规则", example = "111")
    private String validRule;

    @Schema(description = "父级的id,与moduleTableId 构成父子结构,只有子表才有父子结构", example = "111")
    private Long parentModuleTableId;

    @Schema(description = "父级的表名", example = "111")
    private String parentModuleTableName;

}
