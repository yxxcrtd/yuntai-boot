package com.joyintech.yuntai.module.cfg.controller.admin.paramterlist.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 页面路由参数 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ParamterListRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21801")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "页面ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32020")
    private Long pageId;

    @Schema(description = "关联字段ID", example = "7454")
    @ExcelProperty("关联字段ID")
    private Long fieldId;

    @Schema(description = "参数名称", example = "赵六")
    @ExcelProperty("参数名称")
    private String paramName;

    @Schema(description = "参数字段")
    @ExcelProperty("参数字段")
    private String paramField;

    @Schema(description = "是否必填")
    @ExcelProperty("是否必填")
    private Integer isRequire;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}