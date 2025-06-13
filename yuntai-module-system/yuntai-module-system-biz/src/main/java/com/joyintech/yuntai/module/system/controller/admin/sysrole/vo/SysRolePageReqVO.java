package com.joyintech.yuntai.module.system.controller.admin.sysrole.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 角色分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysRolePageReqVO extends PageParam {

    @Schema(description = "", example = "张三")
    private String roleName;

    @Schema(description = "")
    private String roleCode;

    @Schema(description = "")
    private String tenantCode;

    @Schema(description = "", example = "1")
    private Boolean roleType;

}