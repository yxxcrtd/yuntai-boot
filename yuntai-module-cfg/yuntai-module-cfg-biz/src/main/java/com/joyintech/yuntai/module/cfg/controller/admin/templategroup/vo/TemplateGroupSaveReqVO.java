package com.joyintech.yuntai.module.cfg.controller.admin.templategroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 模版分组新增/修改 Request VO")
@Data
public class TemplateGroupSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26855")
    private Long id;

    @Schema(description = "分组名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    @NotEmpty(message = "分组名称不能为空")
    private String groupName;

    @Schema(description = "排序")
    private Integer numSort;

    @Schema(description = "父级ID", example = "2082")
    private Long parentId;

}