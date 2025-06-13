package com.joyintech.yuntai.module.cfg.controller.admin.pageextendevent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 页面事件扩展配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageExtendEventRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16726")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "页面id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5831")
    @ExcelProperty("页面id")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "服务id", example = "5831")
    private Long serviceId;

    @Schema(description = "api编码")
    private String apiCode;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "接口地址", example = "https://www.joyintech.com")
    @ExcelProperty("接口地址")
    private String interfaceUrl;

    @Schema(description = "接口参数")
    @ExcelProperty("接口参数")
    private String interfaceParam;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "执行时间点")
    @ExcelProperty("执行时间点")
    private String runTime;

    @Schema(description = "是否拦截主操作", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否拦截主操作")
    private Boolean isIntercept;

    @Schema(description = "事件类型：1-前端,2-后端", example = "1")
    @ExcelProperty("事件类型：1-前端,2-后端")
    private String eventType;

    @Schema(description = "调用类型1-调用方法,2-调用接口,3-执行代码", example = "2")
    @ExcelProperty("调用类型1-调用方法,2-调用接口,3-执行代码")
    private String callType;

    @Schema(description = "接口", example = "云台")
    @ExcelProperty("接口")
    private String interfaceName;

    @Schema(description = "方法名", example = "王五")
    @ExcelProperty("方法名")
    private String methodName;

    @Schema(description = "代码内容")
    @ExcelProperty("代码内容")
    private String executableCode;

}
