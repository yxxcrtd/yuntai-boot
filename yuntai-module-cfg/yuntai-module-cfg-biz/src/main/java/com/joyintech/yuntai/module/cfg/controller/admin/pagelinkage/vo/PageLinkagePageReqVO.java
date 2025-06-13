package com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 页面联动配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageLinkagePageReqVO extends PageParam {

    @Schema(description = "列表配置ID", example = "28605")
    private Long pageConfigId;

    @Schema(description = "配置名称", example = "赵六")
    private String linkageName;

    @Schema(description = "配置类型1->初始化;2->运行时;", example = "2")
    private String linkageType;

    @Schema(description = "配置图标")
    private String icon;

    @Schema(description = "配置方式1->配置;2->脚本;")
    private String linkageWay;

    @Schema(description = "配置内容")
    private String linkageContent;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}