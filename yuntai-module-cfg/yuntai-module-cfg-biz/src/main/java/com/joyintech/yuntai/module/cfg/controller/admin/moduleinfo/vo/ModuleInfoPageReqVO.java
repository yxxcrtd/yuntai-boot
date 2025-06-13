package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 模型信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ModuleInfoPageReqVO extends PageParam {

    @Schema(description = "模型名称", example = "云台")
    private String moduleName;

    @Schema(description = "模型编码")
    private String moduleCode;

    @Schema(description = "模型类型", example = "2")
    private String moduleType;

    @Schema(description = "模型sql")
    private String moduleSql;

    @Schema(description = "模型JavaBean")
    private String moduleBean;

    @Schema(description = "模型方法")
    private String moduleMethod;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "菜单Id", example = "31477")
    private Long menuId;

}
