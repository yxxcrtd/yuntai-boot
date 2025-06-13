package com.joyintech.yuntai.module.cfg.controller.admin.componenttable.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.ComponentAttributeRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;
import javax.validation.constraints.*;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 组件新增/修改 Request VO")
@Data
public class ComponentTableSaveReqVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1257")
    private Long id;

    @Schema(description = "组件分组表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "92")
    @NotNull(message = "组件分组表ID不能为空")
    private Long groupId;

    @Schema(description = "组件分组表名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "92")
    private String groupName;

    @Schema(description = "组件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "组件名称不能为空")
    private String componentName;

    @Schema(description = "组件编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "组件编码不能为空")
    private String componentCode;

    @Schema(description = "状态（0->开启； 1->停用）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态（0->开启； 1->停用）不能为空")
    private Integer status;

    private List<ComponentAttributeRespVO> addComponentAttribute;
    private List<ComponentAttributeRespVO> updateComponentAttribute;
    private List<ComponentAttributeRespVO> delComponentAttribute;

}