package com.joyintech.yuntai.module.cfg.controller.admin.paramterlist.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 页面路由参数分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ParamterListPageReqVO extends PageParam {

    @Schema(description = "页面ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32020")
    private Long pageId;

    @Schema(description = "关联字段ID", example = "7454")
    private Long fieldId;

    @Schema(description = "参数名称", example = "赵六")
    private String paramName;

    @Schema(description = "参数字段")
    private String paramField;

    @Schema(description = "是否必填")
    private Integer isRequire;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}