package com.joyintech.yuntai.module.cfg.controller.admin.pagegroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 页面分组 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageGroupRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "830")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "列表页ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14381")
    @ExcelProperty("列表页ID")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "分组名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("分组名称")
    private String groupName;

    @Schema(description = "分组编码")
    private String groupCode;

    @Schema(description = "分组排序")
    private String groupSort;

    @Schema(description = "插槽")
    private String groupSlot;


    @Schema(description = "是否隐藏")
    private Boolean isHidden;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "尾部插槽")
    private String groupFailSlot;

}
