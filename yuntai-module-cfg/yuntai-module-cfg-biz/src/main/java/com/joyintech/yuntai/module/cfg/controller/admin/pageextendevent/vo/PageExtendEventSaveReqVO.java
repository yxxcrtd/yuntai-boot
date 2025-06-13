package com.joyintech.yuntai.module.cfg.controller.admin.pageextendevent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 页面事件扩展配置新增/修改 Request VO")
@Data
public class PageExtendEventSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16726")
    private Long id;

    @Schema(description = "页面id",  example = "5831")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "服务id", example = "5831")
    private Long serviceId;

    @Schema(description = "api编码")
    private String apiCode;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "接口地址", example = "https://www.joyintech.com")
    private String interfaceUrl;

    @Schema(description = "接口参数")
    private String interfaceParam;

    @Schema(description = "执行时间点")
    private String runTime;

    @Schema(description = "是否拦截主操作", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否拦截主操作不能为空")
    private Boolean isIntercept;

    @Schema(description = "事件类型：1-前端,2-后端", example = "1")
    private String eventType;

    @Schema(description = "调用类型1-调用方法,2-调用接口,3-执行代码", example = "2")
    private String callType;

    @Schema(description = "接口", example = "云台")
    private String interfaceName;

    @Schema(description = "方法名", example = "王五")
    private String methodName;

    @Schema(description = "代码内容")
    private String executableCode;

}
