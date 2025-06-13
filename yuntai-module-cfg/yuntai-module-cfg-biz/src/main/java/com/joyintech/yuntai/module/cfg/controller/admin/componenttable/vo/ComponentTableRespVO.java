package com.joyintech.yuntai.module.cfg.controller.admin.componenttable.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.ComponentAttributeRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 组件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ComponentTableRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1257")
    @ExcelProperty("主键编号")
    private Long id;

    @Schema(description = "组件分组表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "92")
    @ExcelProperty("组件分组表ID")
    private Long groupId;

    @Schema(description = "组件分组表名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "92")
    private String groupName;

    @Schema(description = "组件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("组件名称")
    private String componentName;

    @Schema(description = "组件编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("组件编码")
    private String componentCode;

    @Schema(description = "状态（0->开启； 1->停用）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态（0->开启； 1->停用）")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    private List<ComponentAttributeRespVO> componentAttributeList;
}