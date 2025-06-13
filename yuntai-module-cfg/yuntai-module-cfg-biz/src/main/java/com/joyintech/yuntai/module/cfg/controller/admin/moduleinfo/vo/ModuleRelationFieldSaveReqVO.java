package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 模型关联新增/修改 Request VO")
@Data
public class ModuleRelationFieldSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14077")
    private Long id;

    @Schema(description = "模型id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7744")
    private Long moduleId;

    @Schema(description = "关联cfg_module_table表的id", example = "20638")
    private Long moduleTableId;

    @Schema(description = "关联cfg_module_table表的id", example = "16369")
    private Long relationModuleTableId;

    @Schema(description = "记录是子表字段关联主表还是主表字段关联子表,0-子表字段关联主表,1-主表字段关联子表")
    private Integer relationDirection;

    @Schema(description = "表关联字段Id", example = "王五")
    private String fieldId;

    @Schema(description = "关联表字段Id", example = "赵六")
    private String relationFieldId;

    @Schema(description = "关联表tableKey", example = "1")
    private String relationTableKey;
}
