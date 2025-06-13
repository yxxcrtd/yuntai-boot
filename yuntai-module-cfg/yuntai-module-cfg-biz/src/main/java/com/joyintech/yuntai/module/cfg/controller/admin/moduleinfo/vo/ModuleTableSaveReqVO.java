package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 模型关联表新增/修改 Request VO")
@Data
public class ModuleTableSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12069")
    private Long id;

    @Schema(description = "模型id", example = "17282")
    private Long moduleId;

    @Schema(description = "表Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7401")
    @NotNull(message = "表Id不能为空")
    private Long tableId;

    @Schema(description = "表名称", example = "17282")
    private String tableName;

    @Schema(description = "表别名", example = "a")
    private String tableAlias;

    @Schema(description = "表注释", example = "17282")
    private String tableComment;

    @Schema(description = "是否主表")
    private Boolean isMain;

    @Schema(description = "是否只读")
    private Boolean readonly;

    @Schema(description = "是否子表")
    private Boolean isChild;

    @Schema(description = "主表id")
    private Long mainTableId;

    @Schema(description = "关联类型")
    private String relationType;

    /* 配置 查询条件 start */
    @Schema(description = "参数名称")
    private String parameterName;

    @Schema(description = "参数类型")
    private String parameterType;

    @Schema(description = "其他查询条件")
    private String searchSql;
    /* 配置 查询条件 start */

    @Schema(description = "每选择一张表生成一个唯一标识")
    private String tableKey;

    @Schema(description = "记录mainTableId对应表的tableKey")
    private String mainTableKey;

    @Schema(description = "表关系列表")
    private List<ModuleRelationFieldSaveReqVO> relationFields;

    @Schema(description = "表字段列表")
    private List<ModuleFieldSaveReqVO> fieldList;

}
