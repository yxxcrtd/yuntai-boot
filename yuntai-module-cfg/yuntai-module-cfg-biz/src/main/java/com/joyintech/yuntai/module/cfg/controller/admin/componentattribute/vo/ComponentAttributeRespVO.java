package com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import javax.validation.constraints.NotEmpty;

@Schema(description = "管理后台 - 组件属性 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ComponentAttributeRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4825")
    @ExcelProperty("主键编号")
    private Long id;

    @Schema(description = "组件表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11344")
    @ExcelProperty("组件表ID")
    private Long componentId;

    @Schema(description = "属性", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    private String attributeCode;

    @Schema(description = "属性名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    @ExcelProperty("属性名称")
    private String attributeName;

    @Schema(description = "属性类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("属性类型")
    private String attributeType;

    @Schema(description = "属性默认值")
    @ExcelProperty("属性默认值")
    private String attributeValue;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "页面联动配置id")
    private Long linkageId;

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1257")
    private Integer sort;
}
