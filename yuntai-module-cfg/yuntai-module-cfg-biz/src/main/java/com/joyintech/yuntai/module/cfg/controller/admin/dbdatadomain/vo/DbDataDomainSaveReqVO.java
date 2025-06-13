package com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 数据库字段类型表(数据域)新增/修改 Request VO")
@Data
public class DbDataDomainSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1682")
    private Long id;

    @Schema(description = "类型名", example = "1")
    private String dataType;

    @Schema(description = "数据库类型（MySQL）;设计：其他数据库根据MySQL类型代码中做映射", example = "2")
    private String dbType;

    @Schema(description = "长度")
    private Integer dataLength;

    @Schema(description = "小数位数")
    private Integer dataScale;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}