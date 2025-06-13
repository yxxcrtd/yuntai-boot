package com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.ComponentAttributeSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 字段定义 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ColumnDefinitionRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3712")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "定义表主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23055")
    @ExcelProperty("定义表主键")
    private Long tableId;

    @Schema(description = "列名", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("列名")
    private String columnName;

    @Schema(description = "列注释", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("列注释")
    private String columnComment;

    @Schema(description = "数据域主键", example = "15680")
    @ExcelProperty("数据域主键")
    private Long dataDomainId;

    @Schema(description = "jdbcType", example = "VARCHAR")
    private String jdbcType;

    @Schema(description = "长度")
    @ExcelProperty("长度")
    private Integer columnLength;

    @Schema(description = "小数位数")
    @ExcelProperty("小数位数")
    private Integer columnScale;

    @Schema(description = "默认值")
    @ExcelProperty("默认值")
    private String defaultValue;

    @Schema(description = "是否主键")
    @ExcelProperty("是否主键")
    private Boolean isPrimaryKey;

    @Schema(description = "非空")
    @ExcelProperty("非空")
    private Boolean isNotNull;

    @Schema(description = "自增")
    @ExcelProperty("自增")
    private Boolean isAutoIncrement;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "是否系统字段")
    @ExcelProperty("是否系统字段")
    private Boolean isSys;

    @Schema(description = "数据类型")
    @ExcelProperty("数据类型")
    private String columnType;

    @Schema(description = "Java类型")
    @ExcelProperty("Java类型")
    private String javaType;

    @Schema(description = "是否生效,true:生效,false:不生效")
    @ExcelProperty("是否生效,true:生效,false:不生效")
    private Boolean status;

    @Schema(description = "组件code")
    @ExcelProperty("组件code")
    private String componentCode;

    @Schema(description = "组件名称")
    private String componentName;

    @Schema(description = "类型id")
    private String columnTypeWithId;

    @Schema(description = "组件属性集合")
    private List<ComponentAttributeSaveReqVO> propsList;
}
