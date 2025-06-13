package com.joyintech.yuntai.module.cfg.controller.admin.commonvar.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公共变量分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommonVarPageReqVO extends PageParam {

    @Schema(description = "字段名称")
    private String fieldName;

    @Schema(description = "字段描述")
    private String fieldDescribe;

    @Schema(description = "缓存类型")
    private String cacheType;

    @Schema(description = "前端/后端类型")
    private String type;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}