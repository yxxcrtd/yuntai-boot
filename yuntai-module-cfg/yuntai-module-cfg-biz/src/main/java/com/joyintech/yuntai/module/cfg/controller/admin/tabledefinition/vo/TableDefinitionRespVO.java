package com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 表定义 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TableDefinitionRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4134")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "数据源id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4778")
    @ExcelProperty("数据源id")
    private Long datasourceId;

    @Schema(description = "数据源名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "4778")
    private String datasourceName;

    @Schema(description = "表名", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    @ExcelProperty("表名")
    private String tableName;

    @Schema(description = "表注释", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("表注释")
    private String tableComment;

    @Schema(description = "描述", example = "随便")
    @ExcelProperty("描述")
    private String remark;

    @Schema(description = "是否系统表", example = "false")
    private Boolean isSys;

    @Schema(description = "状态：false-无效,true-生效", example = "false")
    private Boolean status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "表类型")
    private String tableType;

    @Schema(description = "表sql")
    private String tableSql;
}
