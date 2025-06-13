package com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.joyintech.yuntai.framework.excel.core.convert.AreaConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

@Schema(description = "导入Excel - 信评资产变更-同业")
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class UpdateRatingAssetProfessionReqVO {

    @Schema(description = "资产编号", example = "王五")
    @ExcelProperty("资产编号")
    private String assetNo;

    @Schema(description = "资产类型", example = "王五")
    private String assetType;

    @Schema(description = "银行名称", example = "王五")
    @ExcelProperty("银行名称")
    private String assetName;

    @Schema(description = "统一社会信用代码", example = "123456")
    @ExcelProperty("统一社会信用代码")
    private String creditCode;

    @Schema(description = "银行类型", example = "王五")
    @ExcelProperty(value = "银行类型", converter = AreaConvert.class)
    private String bankType;

    @Schema(description = "注册省份", example = "王五")
    @ExcelProperty(value = "注册省份", converter = AreaConvert.class)
    private String singInProvince;

    @Schema(description = "主体信用评级", example = "王五")
    @ExcelProperty(value = "主体信用评级", converter = AreaConvert.class)
    private String mainRating;

    @Schema(description = "是否删除", example = "王五")
    @ExcelProperty("是否删除")
    private String isDel;
}
