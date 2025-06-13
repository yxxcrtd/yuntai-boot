package com.joyintech.yuntai.module.cfg.controller.admin.moduleapi.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 模型API分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ModuleApiPageReqVO extends PageParam {

    @Schema(description = "服务名称", example = "张三")
    private String serviceName;

    @Schema(description = "服务编码")
    private String serviceCode;

    @Schema(description = "模型或表id", example = "1341")
    private Long moduleId;

    @Schema(description = "模块id", example = "1341")
    private Long menuId;

    @Schema(description = "服务方式", example = "2")
    private String serviceType;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "模型集合",hidden = true)
    private Set<Long> moduleIdSet;

}
