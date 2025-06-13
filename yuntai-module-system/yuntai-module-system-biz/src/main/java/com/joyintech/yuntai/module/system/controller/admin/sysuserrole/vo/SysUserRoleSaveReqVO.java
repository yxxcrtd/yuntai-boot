package com.joyintech.yuntai.module.system.controller.admin.sysuserrole.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 用户角色新增/修改 Request VO")
@Data
public class SysUserRoleSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30840")
    private String id;

    @Schema(description = "", example = "6492")
    private String userId;

    @Schema(description = "", example = "32457")
    private String roleId;

    @Schema(description = "")
    private String tenantCode;

}