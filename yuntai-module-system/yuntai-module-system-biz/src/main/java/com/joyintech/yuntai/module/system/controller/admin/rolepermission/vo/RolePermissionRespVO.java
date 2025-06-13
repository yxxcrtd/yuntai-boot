package com.joyintech.yuntai.module.system.controller.admin.rolepermission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 角色按钮菜单权限 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RolePermissionRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13263")
    @ExcelProperty("主键ID")
    private String id;

    @Schema(description = "", example = "22824")
    @ExcelProperty("")
    private String roleId;

    @Schema(description = "", example = "25944")
    @ExcelProperty("")
    private String permissionId;

    @Schema(description = "")
    @ExcelProperty("")
    private String dataRuleIds;

    @Schema(description = "")
    @ExcelProperty("")
    private LocalDateTime operateDate;

    @Schema(description = "")
    @ExcelProperty("")
    private String operateIp;

    @Schema(description = "")
    @ExcelProperty("")
    private String tenantCode;

}