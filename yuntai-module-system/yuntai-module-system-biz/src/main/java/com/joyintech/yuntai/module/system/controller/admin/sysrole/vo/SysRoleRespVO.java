package com.joyintech.yuntai.module.system.controller.admin.sysrole.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 角色 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SysRoleRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18439")
    @ExcelProperty("主键ID")
    private String id;

    @Schema(description = "", example = "张三")
    @ExcelProperty("")
    private String roleName;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("")
    private String roleCode;

    @Schema(description = "")
    @ExcelProperty("")
    private String tenantCode;

    @Schema(description = "", example = "1")
    @ExcelProperty("")
    private Boolean roleType;

}