package com.joyintech.yuntai.module.cfg.controller.admin.templateinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 模版分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TemplateInfoPageReqVO extends PageParam {

    @Schema(description = "模版分组ID", example = "8214")
    private Long groupId;

    @Schema(description = "模版code")
    private String templateCode;

    @Schema(description = "模版名称", example = "李四")
    private String templateName;

    @Schema(description = "模版地址")
    private String templateAddress;

    @Schema(description = "模版图片")
    private String templateImage;

    @Schema(description = "模版类型")
    private String templateType;

    @Schema(description = "分组id")
    private List<Long> groupIds;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
