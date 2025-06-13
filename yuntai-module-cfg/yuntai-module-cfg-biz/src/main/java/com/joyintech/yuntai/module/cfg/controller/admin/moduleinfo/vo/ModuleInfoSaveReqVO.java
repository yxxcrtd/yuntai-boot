package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 模型信息新增/修改 Request VO")
@Data
public class ModuleInfoSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21201")
    private Long id;

    @Schema(description = "模型映射id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21201")
    private Long mappingModuleId;

    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    @NotEmpty(message = "模型名称不能为空")
    private String moduleName;

    @Schema(description = "模型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "模型编码不能为空")
    private String moduleCode;

    @Schema(description = "模型类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "模型类型不能为空")
    private String moduleType;

    @Schema(description = "模型sql")
    private String moduleSql;

    @Schema(description = "模型JavaBean")
    private String moduleBean;

    @Schema(description = "模型方法")
    private String moduleMethod;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "菜单Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31477")
    private Long menuId;

    @Schema(description = "主表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31477")
    private Long mainTableId;

    @Schema(description = "是否只读模型",  example = "false")
    private Boolean readonly;

    @Schema(description = "接口地址", example = "false")
    private String apiUrl;

    @Schema(description = "接口类型：get，post", example = "false")
    private String apiType;

    @Schema(description = "表列表")
    private List<ModuleTableSaveReqVO> tableList;

    @Schema(description = "是否回推模型数据")
    private Boolean isPushBackModelData;

    @Schema(description = "指定回推地址")
    private String pushBackUrl;

    @Schema(description = "更新时的更新时间")
    private LocalDateTime updateTimeStamp;

    @Schema(description = "流程记录回推参数")
    private String flowLogParams;
}
