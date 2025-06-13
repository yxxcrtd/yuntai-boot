package com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition.vo;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.joyintech.yuntai.framework.common.pojo.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 索引定义分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IndexDefinitionPageReqVO extends PageParam {

    @Schema(description = "定义表主键", example = "10572")
    private Long tableId;

    @Schema(description = "索引名称", example = "王五")
    private String indexName;

    @Schema(description = "索引列")
    private String indexColumns;

    @Schema(description = "是否唯一索引")
    private Boolean isUniqueKey;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}