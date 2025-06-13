package com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.joyintech.yuntai.framework.excel.core.convert.AreaConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

@Schema(description = "导入Excel - 信评资产新增-证劵")
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class CreditRatingAssetSecurityReqVO {

    @Schema(description = "资产编号", example = "王五")
    private String ASSET_NO;

    @Schema(description = "资产类型", example = "王五")
    private String ASSET_TYPE;

    @Schema(description = "企业名称", example = "王五")
    @ExcelProperty("企业名称")
    private String ASSET_NAME;

    @Schema(description = "统一社会信用代码", example = "123456")
    @ExcelProperty("统一社会信用代码")
    private String CREDIT_CODE;

    @Schema(description = "证监会评级", example = "王五")
    @ExcelProperty(value = "证监会评级", converter = AreaConvert.class)
    private String CSRC_RATING;

    @Schema(description = "主体评级", example = "王五")
    @ExcelProperty("主体评级")
    private String MAIN_RATING;

}
