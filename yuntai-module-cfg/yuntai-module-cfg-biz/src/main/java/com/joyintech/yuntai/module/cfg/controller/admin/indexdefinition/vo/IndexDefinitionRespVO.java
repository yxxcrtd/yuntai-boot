package com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition.vo;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 索引定义 Response VO")
@Data
@ExcelIgnoreUnannotated
public class IndexDefinitionRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15749")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "定义表主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10572")
    @ExcelProperty("定义表主键")
    private Long tableId;

    @Schema(description = "索引名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("索引名称")
    private String indexName;

    @Schema(description = "索引列", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("索引列")
    private String indexColumns;

    @Schema(description = "是否唯一索引")
    @ExcelProperty("是否唯一索引")
    private Boolean isUniqueKey;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}