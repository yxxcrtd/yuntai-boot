package com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

@Schema(description = "导入Excel - 公募资管产品新增模板")
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class PublicFundCreateReqVO {

    @Schema(description = "资产类型", example = "王五")
    private String assetType;
}
