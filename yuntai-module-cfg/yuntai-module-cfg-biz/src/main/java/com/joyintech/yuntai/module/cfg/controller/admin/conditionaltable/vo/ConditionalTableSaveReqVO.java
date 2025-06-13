package com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute.ColumnComponentAttributeDO;

@Schema(description = "管理后台 - 条件新增/修改 Request VO")
@Data
public class ConditionalTableSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21870")
    private Long id;

    @Schema(description = "页面/节点关联ID", example = "20470")
    private Long relevanceId;

    @Schema(description = "条件类型(1->初始化；2->运行时)")
    private String type;

    @Schema(description = "设置类型")
    private String setType;

    @Schema(description = "脚本")
    private String script;

    @Schema(description = "前端传值内容")
    private Object condition;

    @Schema(description = "条件内容")
    private String content;

    @Schema(description = "显示类型")
    private List<String> showPageType;

    @Schema(description = "显示类型")
    private String showPageTypeCode;

    @Schema(description = "赋值为")
    private String fillValue;

    @Schema(description = "切换为")
    private String columnDisplayComponent;

    private String columnDisplayComponentName;

    @Schema(description = "赋值为—组件属性")
    private List<ColumnComponentAttributeDO> columnComponentAttributeDO;
}