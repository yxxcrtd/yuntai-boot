package com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.ConditionalTableSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 页面联动配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageLinkageRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27961")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "列表配置ID", example = "28605")
    private Long pageConfigId;

    @Schema(description = "配置名称", example = "赵六")
    @ExcelProperty("配置名称")
    private String linkageName;

    @Schema(description = "配置类型1->初始化;2->运行时;", example = "2")
    @ExcelProperty("配置类型1->初始化;2->运行时;")
    private String linkageType;

    @Schema(description = "配置图标")
    private String icon;

    @Schema(description = "配置方式1->配置;2->脚本;")
    @ExcelProperty("配置方式1->配置;2->脚本;")
    private String linkageWay;

    @Schema(description = "配置内容")
    @ExcelProperty("配置内容")
    private String linkageContent;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "页面id")
    private Long pageId;

    @Schema(description = "条件配置")
    private List<ConditionalTableSaveReqVO> sceneList;
}