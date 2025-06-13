package com.joyintech.yuntai.module.cfg.controller.admin.functioninfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 开发平台功能管理新增/修改 Request VO")
@Data
public class FunctionInfoSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14287")
    private Long id;

    @Schema(description = "父级ID", example = "25460")
    private Long parentId;

    @Schema(description = "功能菜单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "功能菜单名称不能为空")
    private String functionName;

    @Schema(description = "功能菜单编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String functionCode;

    @Schema(description = "功能菜单图标", requiredMode = Schema.RequiredMode.REQUIRED)
    private String functionIcon;

    @Schema(description = "功能菜单类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "功能菜单类型不能为空")
    private String functionType;

    @Schema(description = "功能菜单排序")
    private String sort;

    @Schema(description = "功能菜单状态", example = "1")
    private String status;

    @Schema(description = "功能菜单描述", example = "你猜")
    private String remark;

}
