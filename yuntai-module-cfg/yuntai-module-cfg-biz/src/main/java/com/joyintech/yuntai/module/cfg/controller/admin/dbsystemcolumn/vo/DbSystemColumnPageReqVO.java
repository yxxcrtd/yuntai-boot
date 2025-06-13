package com.joyintech.yuntai.module.cfg.controller.admin.dbsystemcolumn.vo;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.joyintech.yuntai.framework.common.pojo.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 数据库系统字段分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DbSystemColumnPageReqVO extends PageParam {

    @Schema(description = "字段名", example = "云台")
    private String columnName;

    @Schema(description = "字段注释")
    private String columnComment;

    @Schema(description = "位置")
    private Integer columnPosition;

    @Schema(description = "数据域主键", example = "8791")
    private Long dataDomainId;

    @Schema(description = "数据库类型（MySQL）", example = "VARCHAR")
    private String typeSource;

    @Schema(description = "长度")
    private Integer columnLength;

    @Schema(description = "小数位数")
    private Integer columnScale;

    @Schema(description = "默认值")
    private String defaultValue;

    @Schema(description = "是否主键")
    private Boolean isPrimaryKey;

    @Schema(description = "非空")
    private Boolean isNotNull;

    @Schema(description = "自增")
    private Boolean isAutoIncrement;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "数据源id", example = "110")
    private Long dataSourceId;

}
