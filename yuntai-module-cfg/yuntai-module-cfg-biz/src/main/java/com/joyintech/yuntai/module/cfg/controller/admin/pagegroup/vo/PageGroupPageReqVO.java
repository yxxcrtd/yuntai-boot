package com.joyintech.yuntai.module.cfg.controller.admin.pagegroup.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 页面分组分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageGroupPageReqVO extends PageParam {

    @Schema(description = "列表页ID", example = "14381")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "分组名称", example = "李四")
    private String groupName;

    @Schema(description = "分组编码")
    private String groupCode;

    @Schema(description = "分组排序")
    private String groupSort;

    @Schema(description = "插槽")
    private String groupSlot;

    @Schema(description = "是否隐藏")
    private Boolean isHidden;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "尾部插槽")
    private String groupFailSlot;

}
