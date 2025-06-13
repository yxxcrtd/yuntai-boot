package com.joyintech.yuntai.module.cfg.openapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 初始受益权信息
 *
 * @author haozhenzhen
 * @since 2025-05-13
 */

@Schema(description = "初始受益权信息")
@Data
public class TADataBeftInfoResVO {

    @Schema(description = "内部信托合同编号")
    private String contNo;

    @Schema(description = "受益凭据编号")
    private String benefitNo;

    @Schema(description = "委托人全称")
    private String investorName;

    @Schema(description = "委托人类型")
    private String custType;

    @Schema(description = "委托人类型详情")
    private String custTypeSec;

    @Schema(description = "委托人证件类型")
    private String certificateType;

    @Schema(description = "委托人证件号码")
    private String certificateNo;

    @Schema(description = "管理人名称")
    private String productMgName;

    @Schema(description = "管理人证件类型")
    private String productMgType;

    @Schema(description = "管理人证件号码")
    private String productMgCertNo;

    @Schema(description = "是否本公司发行产品")
    private String isInnerCust;

    @Schema(description = "委托人是否上市")
    private String isListed;

    @Schema(description = "受益人顺位")
    private String befSequence;

    @Schema(description = "实际持有信托份额")
    private String fundVol;

    @Schema(description = "单位净值")
    private String nav;

    @Schema(description = "实际持有信托金额")
    private String contAmt;

    @Schema(description = "受益权起始日期")
    private String issueDate;

    @Schema(description = "受益权计划到期日期")
    private String endDate;

    @Schema(description = "合同是否记载业绩比较基准")
    private String contRateFlag;

    @Schema(description = "业绩比较基准（下限）")
    private String rate;

    @Schema(description = "业绩比较基准（上限）")
    private String rateMax;

    @Schema(description = "预期业绩比较基准说明")
    private String rateDes;

    @Schema(description = "受益权代码")
    private String productCode;

    @Schema(description = "受益权类型")
    private String benefitLevel;

    @Schema(description = "受益分配方式")
    private String divProperty;

    @Schema(description = "项目代码")
    private String fundCode;
}
