package com.joyintech.yuntai.module.cfg.controller.admin.templategroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 模版分组 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TemplateGroupRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26855")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "分组名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    @ExcelProperty("分组名称")
    private String groupName;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer numSort;

    @Schema(description = "父级ID", example = "2082")
    @ExcelProperty("父级ID")
    private Long parentId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "子集", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<TemplateGroupRespVO> children;

}
