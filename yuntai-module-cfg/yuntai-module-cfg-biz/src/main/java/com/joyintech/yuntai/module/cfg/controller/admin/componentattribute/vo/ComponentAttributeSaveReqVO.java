package com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 组件属性新增/修改 Request VO")
@Data
public class ComponentAttributeSaveReqVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4825")
    private Long id;

    @Schema(description = "字段属性Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    private Long columnId;

    @Schema(description = "页面id",example = "云台")
    private Long pageId;

    @Schema(description = "优先级",example = "数字")
    private Long priority;

    @Schema(description = "属性",  example = "云台")
    @NotEmpty(message = "属性不能为空")
    private String attributeCode;

    @Schema(description = "属性名称",  example = "云台")
    @NotEmpty(message = "属性名称不能为空")
    private String attributeName;

    @Schema(description = "属性类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "属性类型不能为空")
    private String attributeType;

    @Schema(description = "属性默认值")
    private String attributeValue;

    @Schema(description = "页面联动配置id")
    private Long linkageId;

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1257")
    private Integer sort;
}
