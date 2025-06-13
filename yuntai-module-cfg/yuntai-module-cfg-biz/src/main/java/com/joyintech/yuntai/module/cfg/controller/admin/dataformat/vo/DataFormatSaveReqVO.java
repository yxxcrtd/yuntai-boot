package com.joyintech.yuntai.module.cfg.controller.admin.dataformat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 页面数据格式化新增/修改 Request VO")
@Data
public class DataFormatSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1604")
    private Long id;

    @Schema(description = "列表配置页ID", example = "11695")
    private Long listConfigId;

    @Schema(description = "前缀")
    private String prefix;

    @Schema(description = "后缀")
    private String suffix;

    @Schema(description = "格式化类型", example = "1")
    private String formatType;

    @Schema(description = "数字类型", example = "2")
    private String numType;

    @Schema(description = "保留小数位数")
    private String keepDecimalPlaces;

    @Schema(description = "转换倍率")
    private String conversionRate;

    @Schema(description = "千分位符")
    private Boolean thousandth;

    @Schema(description = "日期格式")
    private String dateFormat;

    @Schema(description = "图片格式")
    private String pictureStyle;

    @Schema(description = "链接打开方式")
    private String linkOpenMethod;

    @Schema(description = "链接地址")
    private String linkAddress;

    @Schema(description = "正则表达式")
    private String regularExpression;

    @Schema(description = "组件名称", example = "王五")
    private String componentName;

    @Schema(description = "脚本")
    private String script;

}
