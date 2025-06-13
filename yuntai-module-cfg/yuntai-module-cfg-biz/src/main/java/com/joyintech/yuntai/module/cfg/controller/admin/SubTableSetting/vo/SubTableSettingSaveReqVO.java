package com.joyintech.yuntai.module.cfg.controller.admin.SubTableSetting.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.ConditionalTableSaveReqVO;

@Schema(description = "管理后台 - 子表设置新增/修改 Request VO")
@Data
public class SubTableSettingSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21028")
    private Long id;

    @Schema(description = "编辑模式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "编辑模式不能为空")
    private String editMode;

    @Schema(description = "是否必填", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "是否必填不能为空")
    private String isRequired;

    @Schema(description = "是否显示序号")
    private String isShowIndex;

    @Schema(description = "是否显示合计")
    private String isShowTotal;

    @Schema(description = "页面设置ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "页面设置ID不能为空")
    private String pageId;

    @Schema(description = "页面apiID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "页面apiID不能为空")
    private String pageApiId;

    @Schema(description = "表格id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "表格id不能为空")
    private String tableId;

    @Schema(description = "子表标题")
    private String tableTitle;

    @Schema(description = "默认数据")
    private String defaultData;

    @Schema(description = "显示行号")
    private String tableSort;

    @Schema(description = "每页行号默认起始")
    private String tableBeginIndex;

    @Schema(description = "斑马纹")
    private String tableStripes;

    @Schema(description = "是否显示复选框")
    private String isShowCheckBox;

    @Schema(description = "固定操作列")
    private String tableFixedAction;

    @Schema(description = "操作列位置")
    private String tableActionPostion;

    @Schema(description = "是否支持分页")
    private String isPageList;

    @Schema(description = "默认分页大小")
    private String tableDefPageSize;

    @Schema(description = "自定义表字段名")
    private String defTableFieldName;

    @Schema(description = "子表设置列表")
    private List<ConditionalTableSaveReqVO> showConfig;

    @Schema(description = "是否允许新增")
    private String isNotAllowAdd;

    @Schema(description = "是否隐藏操作列")
    private String isHiddenAction;


}