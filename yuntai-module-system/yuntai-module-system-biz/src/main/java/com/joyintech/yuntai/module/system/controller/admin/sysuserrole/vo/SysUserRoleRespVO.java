package com.joyintech.yuntai.module.system.controller.admin.sysuserrole.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户角色 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SysUserRoleRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30840")
    @ExcelProperty("主键ID")
    private String id;

    @Schema(description = "", example = "6492")
    @ExcelProperty("")
    private String userId;

    @Schema(description = "", example = "32457")
    @ExcelProperty("")
    private String roleId;

    @Schema(description = "")
    @ExcelProperty("")
    private String tenantCode;

}