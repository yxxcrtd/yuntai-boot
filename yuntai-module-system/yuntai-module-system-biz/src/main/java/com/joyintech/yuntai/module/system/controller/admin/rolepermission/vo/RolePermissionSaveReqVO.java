package com.joyintech.yuntai.module.system.controller.admin.rolepermission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 角色按钮菜单权限新增/修改 Request VO")
@Data
public class RolePermissionSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13263")
    private String id;

    @Schema(description = "", example = "22824")
    private String roleId;

    @Schema(description = "", example = "25944")
    private String permissionId;

    @Schema(description = "")
    private String dataRuleIds;

    @Schema(description = "")
    private LocalDateTime operateDate;

    @Schema(description = "")
    private String operateIp;

    @Schema(description = "")
    private String tenantCode;

}