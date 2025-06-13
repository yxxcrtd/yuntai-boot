package com.joyintech.yuntai.module.cfg.controller.admin.templatecontent.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 模版内容分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TemplateContentPageReqVO extends PageParam {

    @Schema(description = "模版ID", example = "14468")
    private Long templateId;

    @Schema(description = "类型", example = "2")
    private Integer type;

    @Schema(description = "api编码")
    private String apiCode;

    @Schema(description = "api名称", example = "李四")
    private String apiName;

    @Schema(description = "api类型", example = "2")
    private String apiType;

    @Schema(description = "参数名称", example = "李四")
    private String parameterName;

    @Schema(description = "参数编码")
    private String parameterCode;

    @Schema(description = "参数类型", example = "1")
    private String parameterType;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}