package com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.joyintech.yuntai.framework.excel.core.convert.AreaConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

@Schema(description = "导入Excel - 信评资产新增-主体")
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class CreditRatingAssetReqVO {

    @Schema(description = "资产编号", example = "王五")
    private String ASSET_NO;

    @Schema(description = "资产类型", example = "王五")
    private String ASSET_TYPE;

    @Schema(description = "主体名称", example = "王五")
    @ExcelProperty("主体名称")
    private String ASSET_NAME;

    @Schema(description = "统一社会信用代码", example = "123456")
    @ExcelProperty("统一社会信用代码")
    private String CREDIT_CODE;

    @Schema(description = "省份", example = "王五")
    @ExcelProperty(value = "省份", converter = AreaConvert.class)
    private String PROVINCE;

    @Schema(description = "地级市", example = "王五")
    @ExcelProperty(value = "地级市", converter = AreaConvert.class)
    private String CITY;

    @Schema(description = "所属县/区级", example = "王五")
    @ExcelProperty(value = "所属县/区级", converter = AreaConvert.class)
    private String AREA;

    @Schema(description = "总得分", example = "王五")
    @ExcelProperty("总得分")
    private String TOTAL_SCORE;

    @Schema(description = "配置策略(万元)", example = "王五")
    @ExcelProperty("配置策略(万元)")
    private String SETTING_STRATEGY;

    @Schema(description = "短期期限（含权债，距离最近行权日的时间）", example = "王五")
    @ExcelProperty("短期期限（含权债，距离最近行权日的时间）")
    private String SHORT_TERM;

    @Schema(description = "交易策略限额(万元)", example = "王五")
    @ExcelProperty("交易策略限额(万元)")
    private String SHORT_CREDIT;

    @Schema(description = "评级(公司认可)", example = "王五")
    @ExcelProperty("评级(公司认可)")
    private String MAIN_RATING;

    @Schema(description = "主体内部风险评级", example = "王五")
    @ExcelProperty("主体内部风险评级")
    private String INTERNAL_RATING;
}
