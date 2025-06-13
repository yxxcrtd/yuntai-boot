package com.joyintech.yuntai.module.cfg.controller.admin.functioninfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 开发平台功能管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FunctionInfoPageReqVO extends PageParam {

    @Schema(description = "父级ID", example = "25460")
    private Long parentId;

    @Schema(description = "功能菜单名称", example = "李四")
    private String functionName;

    @Schema(description = "功能菜单编码")
    private String functionCode;

    @Schema(description = "功能菜单图标")
    private String functionIcon;

    @Schema(description = "功能菜单类型")
    private String functionType;

    @Schema(description = "功能菜单排序")
    private String sort;

    @Schema(description = "功能菜单状态", example = "1")
    private String status;

    @Schema(description = "功能菜单描述", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}