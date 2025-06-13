package com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import java.time.LocalDateTime;
import java.util.List;

import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.ComponentAttributeSaveReqVO;
import org.springframework.format.annotation.DateTimeFormat;

import com.joyintech.yuntai.framework.common.pojo.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 字段定义分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ColumnDefinitionPageReqVO extends PageParam {

    @Schema(description = "定义表主键", example = "23055")
    private Long tableId;

    @Schema(description = "列名", example = "赵六")
    private String columnName;

    @Schema(description = "列注释")
    private String columnComment;

    @Schema(description = "数据域主键", example = "18797")
    private Long dataDomainId;

    @Schema(description = "jdbcType", example = "VARCHAR")
    private String jdbcType;

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

    @Schema(description = "是否系统字段")
    private Boolean isSys;

    @Schema(description = "是否生效false-未生效,true-已生效")
    private Boolean status;


}
