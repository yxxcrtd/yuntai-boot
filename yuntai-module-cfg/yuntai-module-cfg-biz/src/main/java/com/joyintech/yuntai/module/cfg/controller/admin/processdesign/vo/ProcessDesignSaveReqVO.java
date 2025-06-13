package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node.Node;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 流程设计新增/修改 Request VO")
@Data
public class ProcessDesignSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24268")
    private Long id;

    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22504")
    @NotNull(message = "菜单ID不能为空")
    private Long menuId;

    @Schema(description = "流程名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    @NotEmpty(message = "流程名称不能为空")
    private String processName;

    @Schema(description = "关联页面id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long pageId;

    @Schema(description = "流程id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String flowId;

    @Schema(description = "流程类型(内外)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "流程类型(内外)不能为空")
    private String processType;

    @Schema(description = "流程节点数据",  example = "1")
    private Node process;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新时的更新时间")
    private LocalDateTime updateTimeStamp;

}
