package com.joyintech.yuntai.module.system.controller.admin.permission.vo.syspermission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 按钮菜单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SysPermissionRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28991")
    @ExcelProperty("主键ID")
    private String id;

    @Schema(description = "", example = "6958")
    @ExcelProperty("")
    private String parentId;

    @Schema(description = "")
    @ExcelProperty("")
    private String code;

    @Schema(description = "", example = "张三")
    @ExcelProperty("")
    private String name;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer canFavorite;

    @Schema(description = "")
    @ExcelProperty("")
    private String menuAlias;

    @Schema(description = "")
    @ExcelProperty("")
    private String busParams;

    @Schema(description = "")
    @ExcelProperty("")
    private String busGroup;

    @Schema(description = "", example = "https://www.joyintech.com")
    @ExcelProperty("")
    private String url;

    @Schema(description = "")
    @ExcelProperty("")
    private String component;

    @Schema(description = "", example = "张三")
    @ExcelProperty("")
    private String componentName;

    @Schema(description = "")
    @ExcelProperty("")
    private String redirect;

    @Schema(description = "", example = "2")
    @ExcelProperty("")
    private Integer menuType;

    @Schema(description = "")
    @ExcelProperty("")
    private String perms;

    @Schema(description = "", example = "2")
    @ExcelProperty("")
    private String permsType;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("")
    private BigDecimal sortNo;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer alwaysShow;

    @Schema(description = "")
    @ExcelProperty("")
    private String icon;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer isRoute;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer isLeaf;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer keepAlive;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer hidden;

    @Schema(description = "", example = "随便")
    @ExcelProperty("")
    private String description;

    @Schema(description = "")
    @ExcelProperty("")
    private String createBy;

    @Schema(description = "")
    @ExcelProperty("")
    private LocalDateTime createTime;

    @Schema(description = "")
    @ExcelProperty("")
    private String updateBy;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("")
    private Integer delFlag;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer ruleFlag;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("")
    private String status;

    @Schema(description = "")
    @ExcelProperty("")
    private Integer internalOrExternal;

    @Schema(description = "", example = "1")
    @ExcelProperty("")
    private String systemType;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("")
    private Boolean hiddenType;

}