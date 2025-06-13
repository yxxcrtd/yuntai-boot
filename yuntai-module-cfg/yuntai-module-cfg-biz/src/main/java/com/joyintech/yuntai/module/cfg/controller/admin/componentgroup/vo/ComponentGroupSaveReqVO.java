package com.joyintech.yuntai.module.cfg.controller.admin.componentgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 组件分组新增/修改 Request VO")
@Data
public class ComponentGroupSaveReqVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22816")
    private Long id;

    @Schema(description = "组件分组名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "组件分组名称不能为空")
    private String groupName;

    @Schema(description = "编号排序")
    private Integer numSort;

    @Schema(description = "父级ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22816")
    private Long parentId;

}