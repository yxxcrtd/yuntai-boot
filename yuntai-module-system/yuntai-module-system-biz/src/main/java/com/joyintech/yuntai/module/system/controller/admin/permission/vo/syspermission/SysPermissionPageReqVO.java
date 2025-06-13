package com.joyintech.yuntai.module.system.controller.admin.permission.vo.syspermission;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 按钮菜单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysPermissionPageReqVO extends PageParam {

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

    @Schema(description = "")
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
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "")
    private String updateBy;

    @Schema(description = "")
    private Integer delFlag;

    @Schema(description = "")
    private Integer ruleFlag;

    @Schema(description = "", example = "2")
    private String status;

    @Schema(description = "")
    private Integer internalOrExternal;

    @Schema(description = "", example = "1")
    private String systemType;

    @Schema(description = "", example = "2")
    private Boolean hiddenType;

}