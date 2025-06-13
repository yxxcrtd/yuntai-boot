package com.joyintech.yuntai.module.system.controller.admin.dictitem.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 字典子表分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DictItemPageReqVO extends PageParam {

    @Schema(description = "", example = "2639")
    private String dictId;

    @Schema(description = "")
    private String dictCode;

    @Schema(description = "")
    private String itemText;

    @Schema(description = "")
    private String itemValue;

    @Schema(description = "", example = "2")
    private String filterType;

    @Schema(description = "", example = "随便")
    private String description;

    @Schema(description = "")
    private Integer sortOrder;

    @Schema(description = "", example = "2")
    private Integer status;

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
    private Integer tenantCode;

    @Schema(description = "")
    private String extText1;

}