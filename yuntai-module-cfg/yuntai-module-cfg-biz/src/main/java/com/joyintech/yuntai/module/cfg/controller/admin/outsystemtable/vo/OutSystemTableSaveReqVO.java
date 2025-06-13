package com.joyintech.yuntai.module.cfg.controller.admin.outsystemtable.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 外部系统关联新增/修改 Request VO")
@Data
public class OutSystemTableSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15557")
    private Long id;

    @Schema(description = "页面ID", example = "29438")
    private Long pageId;

    @Schema(description = "流程ID", example = "14841")
    private String flowId;

    @Schema(description = "实例ID", example = "1745")
    private String flowInstanceId;

    @Schema(description = "类型", example = "2")
    private String flowType;

    @Schema(description = "业务数据ID", example = "21923")
    private String businessId;

    @Schema(description = "流程唯一Key")
    private String pageDataId;

}