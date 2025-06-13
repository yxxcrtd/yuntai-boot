package com.joyintech.yuntai.module.cfg.controller.admin.dataconversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 数据转换新增/修改 Request VO")
@Data
public class DataConversionSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1836")
    private Long id;

    @Schema(description = "列表配置页ID", example = "31073")
    private Long listConfigId;

    @Schema(description = "数据转换类型", example = "2")
    private String dataType;

    @Schema(description = "数据转换内容")
    private String dataContent;

    @Schema(description = "数据转换正则表达式")
    private String dataRegular;

    @Schema(description = "数据转换脚本")
    private String dataScript;

}