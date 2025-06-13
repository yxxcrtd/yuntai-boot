package com.joyintech.yuntai.module.cfg.controller.admin.functioninfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 开发平台功能管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class FunctionInfoRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14287")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "父级ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25460")
    @ExcelProperty("父级ID")
    private Long parentId;

    @Schema(description = "功能菜单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("功能菜单名称")
    private String functionName;

    @Schema(description = "功能菜单编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("功能菜单编码")
    private String functionCode;

    @Schema(description = "功能菜单图标", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("功能菜单图标")
    private String functionIcon;

    @Schema(description = "功能菜单类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String functionType;

    @Schema(description = "功能菜单排序")
    @ExcelProperty("功能菜单排序")
    private String sort;

    @Schema(description = "功能菜单状态", example = "1")
    @ExcelProperty("功能菜单状态")
    private String status;

    @Schema(description = "功能菜单描述", example = "你猜")
    @ExcelProperty("功能菜单描述")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}