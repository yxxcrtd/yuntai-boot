package com.joyintech.yuntai.module.cfg.controller.admin.componentgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 组件分组 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ComponentGroupRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22816")
    @ExcelProperty("主键编号")
    private Long id;

    @Schema(description = "组件分组名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("组件分组名称")
    private String groupName;

    @Schema(description = "编号排序")
    @ExcelProperty("编号排序")
    private Integer numSort;

    @Schema(description = "父级ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22816")
    @ExcelProperty("父级ID")
    private Long parentId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "子集", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ComponentGroupRespVO> children;
}