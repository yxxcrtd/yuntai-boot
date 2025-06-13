package com.joyintech.yuntai.module.system.controller.admin.sysuserrole.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 用户角色分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysUserRolePageReqVO extends PageParam {

    @Schema(description = "", example = "6492")
    private String userId;

    @Schema(description = "", example = "32457")
    private String roleId;

    @Schema(description = "")
    private String tenantCode;

}