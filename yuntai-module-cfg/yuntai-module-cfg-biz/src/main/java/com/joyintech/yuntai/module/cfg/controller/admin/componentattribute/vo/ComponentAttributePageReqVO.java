package com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 组件属性分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ComponentAttributePageReqVO extends PageParam {

    @Schema(description = "组件表ID", example = "11344")
    private Long componentId;

    @Schema(description = "属性", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    private String attributeCode;

    @Schema(description = "属性名称", example = "云台")
    private String attributeName;

    @Schema(description = "属性类型", example = "1")
    private String attributeType;

    @Schema(description = "属性默认值")
    private String attributeValue;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "页面联动配置id")
    private Long linkageId;

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1257")
    private Integer sort;
}
