package com.joyintech.yuntai.module.system.controller.admin.rolepermission.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 角色按钮菜单权限分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RolePermissionPageReqVO extends PageParam {

    @Schema(description = "", example = "22824")
    private String roleId;

    @Schema(description = "", example = "25944")
    private String permissionId;

    @Schema(description = "")
    private String dataRuleIds;

    @Schema(description = "")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operateDate;

    @Schema(description = "")
    private String operateIp;

    @Schema(description = "")
    private String tenantCode;

}