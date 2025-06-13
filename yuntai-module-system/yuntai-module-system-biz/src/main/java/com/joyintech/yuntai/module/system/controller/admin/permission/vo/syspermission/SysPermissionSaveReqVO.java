package com.joyintech.yuntai.module.system.controller.admin.permission.vo.syspermission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 按钮菜单新增/修改 Request VO")
@Data
public class SysPermissionSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28991")
    private String id;

    @Schema(description = "", example = "6958")
    private String parentId;

    @Schema(description = "")
    private String code;

    @Schema(description = "", example = "张三")
    private String name;

    @Schema(description = "")
    private Integer canFavorite;

    @Schema(description = "")
    private String menuAlias;

    @Schema(description = "")
    private String busParams;

    @Schema(description = "")
    private String busGroup;

    @Schema(description = "", example = "https://www.joyintech.com")
    private String url;

    @Schema(description = "")
    private String component;

    @Schema(description = "", example = "张三")
    private String componentName;

    @Schema(description = "")
    private String redirect;

    @Schema(description = "", example = "2")
    private Integer menuType;

    @Schema(description = "")
    private String perms;

    @Schema(description = "", example = "2")
    private String permsType;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "不能为空")
    private BigDecimal sortNo;

    @Schema(description = "")
    private Integer alwaysShow;

    @Schema(description = "")
    private String icon;

    @Schema(description = "")
    private Integer isRoute;

    @Schema(description = "")
    private Integer isLeaf;

    @Schema(description = "")
    private Integer keepAlive;

    @Schema(description = "")
    private Integer hidden;

    @Schema(description = "", example = "随便")
    private String description;

    @Schema(description = "")
    private String createBy;

    @Schema(description = "")
    private String updateBy;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "不能为空")
    private Integer delFlag;

    @Schema(description = "")
    private Integer ruleFlag;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "不能为空")
    private String status;

    @Schema(description = "")
    private Integer internalOrExternal;

    @Schema(description = "", example = "1")
    private String systemType;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "不能为空")
    private Boolean hiddenType;

}