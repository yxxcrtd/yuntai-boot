package com.joyintech.yuntai.module.system.controller.admin.sysrole.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 角色新增/修改 Request VO")
@Data
public class SysRoleSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18439")
    private String id;

    @Schema(description = "", example = "张三")
    private String roleName;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "不能为空")
    private String roleCode;

    @Schema(description = "")
    private String tenantCode;

    @Schema(description = "", example = "1")
    private Boolean roleType;

}