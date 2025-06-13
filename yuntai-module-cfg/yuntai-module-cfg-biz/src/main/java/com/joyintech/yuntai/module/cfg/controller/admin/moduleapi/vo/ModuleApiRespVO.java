package com.joyintech.yuntai.module.cfg.controller.admin.moduleapi.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleApiParamVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 模型API Response VO")
@Data
@ExcelIgnoreUnannotated
public class ModuleApiRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11933")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "服务名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("服务名称")
    private String serviceName;

    @Schema(description = "服务编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("服务编码")
    private String serviceCode;

    @Schema(description = "模型或表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1341")
    @ExcelProperty("模型id")
    private Long moduleId;

    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "模型1")
    @ExcelProperty("模型名称")
    private String moduleName;

    @Schema(description = "服务方式", example = "2")
    @ExcelProperty("服务方式")
    private String serviceType;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "参数列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ModuleApiParamVO> paramVOList;

}
