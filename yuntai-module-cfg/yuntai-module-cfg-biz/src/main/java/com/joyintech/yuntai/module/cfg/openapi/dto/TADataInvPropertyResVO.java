package com.joyintech.yuntai.module.cfg.openapi.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 初始委托人及其财产信息
 *
 * @author haozhenzhen
 * @since 2025-05-13
 */

@Schema(description = "初始委托人及其财产信息")
@Data
public class TADataInvPropertyResVO {

    @Schema(description = "内部信托合同编号")
    private String contNo;

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

    @Schema(description = "委托资产类型")
    private String invProperty;

    @Schema(description = "委托资产类型补充说明")
    private String invPropertyExt;

    @Schema(description = "实收信托规模（委托资产）")
    private String contAmt;

    @Schema(description = "合同生效日期")
    private String effDate;

    @Schema(description = "项目代码")
    private String fundCode;
}
