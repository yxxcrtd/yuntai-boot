package com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.joyintech.yuntai.framework.common.pojo.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 表定义分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TableDefinitionPageReqVO extends PageParam {

    @Schema(description = "数据源id", example = "4778")
    private Long datasourceId;

    @Schema(description = "表名", example = "云台")
    private String tableName;

    @Schema(description = "表注释")
    private String tableComment;

    @Schema(description = "描述", example = "随便")
    private String remark;

    @Schema(description = "是否系统表", example = "false")
    private Boolean isSys;

    @Schema(description = "状态：false-无效,true-生效", example = "false")
    private Boolean status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] updateTime;

    @Schema(description = "表类型")
    private String tableType;

    @Schema(description = "表sql")
    private String tableSql;
}
