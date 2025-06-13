package com.joyintech.yuntai.module.cfg.controller.admin.paramterlist.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 页面路由参数新增/修改 Request VO")
@Data
public class ParamterListSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21801")
    private Long id;

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

}