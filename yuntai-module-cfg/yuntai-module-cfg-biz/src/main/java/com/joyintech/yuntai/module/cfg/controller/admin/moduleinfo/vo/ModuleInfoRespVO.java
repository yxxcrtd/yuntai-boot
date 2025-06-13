package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleapi.vo.ModuleApiRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 模型信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ModuleInfoRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21201")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    @ExcelProperty("模型名称")
    private String moduleName;

    @Schema(description = "模型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("模型编码")
    private String moduleCode;

    @Schema(description = "模型类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("模型类型")
    private String moduleType;

    @Schema(description = "模型sql")
    @ExcelProperty("模型sql")
    private String moduleSql;

    @Schema(description = "模型JavaBean")
    @ExcelProperty("模型JavaBean")
    private String moduleBean;

    @Schema(description = "模型方法")
    @ExcelProperty("模型方法")
    private String moduleMethod;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "菜单Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31477")
    private Long menuId;

    @Schema(description = "主表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31477")
    private Long mainTableId;

    @Schema(description = "主表名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "31477")
    @ExcelProperty("主表名称")
    private String mainTableName;

    @Schema(description = "映射模型id")
    private Long mappingModuleId;

    @Schema(description = "只读模型")
    private Boolean readonly;

    @Schema(description = "更新人")
    private String updater;

    @Schema(description = "接口地址")
    private String apiUrl;

    @Schema(description = "接口类型：get，post")
    private String apiType;

    @Schema(description = "表列表")
    private List<ModuleTableSaveReqVO> tableList;

    @Schema(description = "api列表")
    private List<ModuleApiRespVO> apiList;

    @Schema(description = "是否回推模型数据")
    private Boolean isPushBackModelData;

    @Schema(description = "指定回推地址")
    private String pushBackUrl;

    @Schema(description = "流程记录回推参数")
    private String flowLogParams;

}
