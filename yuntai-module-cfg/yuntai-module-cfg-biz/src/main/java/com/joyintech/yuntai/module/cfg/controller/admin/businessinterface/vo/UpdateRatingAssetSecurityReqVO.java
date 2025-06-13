package com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.joyintech.yuntai.framework.excel.core.convert.AreaConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

@Schema(description = "导入Excel - 信评资产变更-证劵")
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class UpdateRatingAssetSecurityReqVO {

    @Schema(description = "资产编号", example = "王五")
    @ExcelProperty("资产编号")
    private String assetNo;

    @Schema(description = "资产类型", example = "王五")
    private String assetType;

    @Schema(description = "企业名称", example = "王五")
    @ExcelProperty("企业名称")
    private String assetName;

    @Schema(description = "统一社会信用代码", example = "123456")
    @ExcelProperty("统一社会信用代码")
    private String creditCode;

    @Schema(description = "证监会评级", example = "王五")
    @ExcelProperty(value = "证监会评级", converter = AreaConvert.class)
    private String csrcRating;

    @Schema(description = "主体评级", example = "王五")
    @ExcelProperty("主体评级")
    private String mainRating;

    @Schema(description = "是否删除", example = "王五")
    @ExcelProperty("是否删除")
    private String isDel;
}
