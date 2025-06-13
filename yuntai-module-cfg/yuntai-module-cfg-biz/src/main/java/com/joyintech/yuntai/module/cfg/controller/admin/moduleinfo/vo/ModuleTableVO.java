package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 模型关联表 处理回显字段")
@Data
public class ModuleTableVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12069")
    private Long id;

    @Schema(description = "表名称", example = "17282")
    private String tableName;

    @Schema(description = "表别名", example = "a")
    private String tableAlias;

}
