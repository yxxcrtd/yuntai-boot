package com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain.vo;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.joyintech.yuntai.framework.common.pojo.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 数据库字段类型表(数据域)分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DbDataDomainPageReqVO extends PageParam {

    @Schema(description = "类型名", example = "1")
    private String dataType;

    @Schema(description = "数据库类型（MySQL）;设计：其他数据库根据MySQL类型代码中做映射", example = "2")
    private String dbType;

    @Schema(description = "长度")
    private Integer dataLength;

    @Schema(description = "小数位数")
    private Integer dataScale;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}