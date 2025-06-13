package com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 数据集-http请求内容新增/修改 Request VO")
@Data
public class DataSetConfigHttpSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4754")
    private Long id;

    @Schema(description = "列表页ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13047")
    @NotNull(message = "列表页ID不能为空")
    private Long dataId;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "请求头/参数名称")
    private String keyCode;

    @Schema(description = "内容/参数值")
    private String value;

}