package com.joyintech.yuntai.module.cfg.controller.admin.sqlcodegen.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionSaveReqVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 基于数据库的表结构，创建代码生成器的表和字段定义 Request VO")
@Data
public class SqlCodegenCreateListReqVO {
    @Schema(description = "表数组", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "表数组不能为空")
    private List<TableDefinitionSaveReqVO> tables;

}
