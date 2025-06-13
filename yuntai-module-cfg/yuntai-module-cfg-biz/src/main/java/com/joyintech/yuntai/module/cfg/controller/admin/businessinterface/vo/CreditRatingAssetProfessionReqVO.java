package com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.joyintech.yuntai.framework.excel.core.convert.AreaConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

@Schema(description = "导入Excel - 信评资产新增-同业")
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class CreditRatingAssetProfessionReqVO {

    @Schema(description = "资产编号", example = "王五")
    private String ASSET_NO;

    @Schema(description = "资产类型", example = "王五")
    private String ASSET_TYPE;

    @Schema(description = "银行名称", example = "王五")
    @ExcelProperty("银行名称")
    private String ASSET_NAME;

    @Schema(description = "统一社会信用代码", example = "123456")
    @ExcelProperty("统一社会信用代码")
    private String CREDIT_CODE;

    @Schema(description = "银行类型", example = "王五")
    @ExcelProperty(value = "银行类型", converter = AreaConvert.class)
    private String BANK_TYPE;

    @Schema(description = "注册省份", example = "王五")
    @ExcelProperty(value = "注册省份", converter = AreaConvert.class)
    private String SING_IN_PROVINCE;

    @Schema(description = "主体信用评级", example = "王五")
    @ExcelProperty(value = "主体信用评级", converter = AreaConvert.class)
    private String MAIN_RATING;
}
