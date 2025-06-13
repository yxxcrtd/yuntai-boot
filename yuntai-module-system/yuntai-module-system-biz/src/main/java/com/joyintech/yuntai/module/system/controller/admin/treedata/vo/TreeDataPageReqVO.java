package com.joyintech.yuntai.module.system.controller.admin.treedata.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 字典树子分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TreeDataPageReqVO extends PageParam {

    @Schema(description = "", example = "2")
    private String treeType;

    @Schema(description = "")
    private String nodeCode;

    @Schema(description = "")
    private String nodeText;

    @Schema(description = "")
    private String shortText;

    @Schema(description = "")
    private Integer nodeLevel;

    @Schema(description = "")
    private String parentCode;

    @Schema(description = "")
    private Integer sortIndex;

    @Schema(description = "")
    private String createBy;

    @Schema(description = "")
    private String updateBy;

    @Schema(description = "")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "", example = "2")
    private Integer status;

    @Schema(description = "")
    private String treeGroup;

    @Schema(description = "", example = "王五")
    private String treeName;

}