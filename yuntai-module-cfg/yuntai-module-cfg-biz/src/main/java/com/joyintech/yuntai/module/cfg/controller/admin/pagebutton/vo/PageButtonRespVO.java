package com.joyintech.yuntai.module.cfg.controller.admin.pagebutton.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 页面操作按钮 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageButtonRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28049")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "按钮类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("按钮类型")
    private String buttonType;

    @Schema(description = "操作类型", example = "1")
    @ExcelProperty("操作类型")
    private String operationType;

    @Schema(description = "按钮名称", example = "张三")
    @ExcelProperty("按钮名称")
    private String buttonName;

    @Schema(description = "列表页ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23130")
    @ExcelProperty("列表页ID")
    private Long pageId;

    @Schema(description = "关联服务ID")
    private Long modelServerId;

    @Schema(description = "api编码")
    private String apiCode;

    @Schema(description = "服务参数")
    private List<String> serverParams;

    @Schema(description = "服务参数编码")
    private String serverParamsCode;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "按钮样式")
    @ExcelProperty("按钮样式")
    private String buttonStyle;

    @Schema(description = "按钮图标")
    @ExcelProperty("按钮图标")
    private String buttonIcon;

    @Schema(description = "按钮类型")
    private String buttonShape;

    @Schema(description = "打开方式")
    @ExcelProperty("打开方式")
    private String openWay;

    @Schema(description = "关联页面")
    @ExcelProperty("关联页面")
    private String relevancePage;

    @Schema(description = "自定义方法")
    private String customMethod;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "按钮默认参数")
    private String actionDefaultParams;

    @Schema(description = "子表id")
    private Long moduleTableId;
}
