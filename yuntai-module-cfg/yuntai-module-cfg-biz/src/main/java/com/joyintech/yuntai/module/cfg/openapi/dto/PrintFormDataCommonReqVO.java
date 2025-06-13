package com.joyintech.yuntai.module.cfg.openapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 类描述：
 *
 * @author liuyanlong
 * @version 1.0
 * @since 2025/5/24
 */
@Schema(description = "个性化打印")
@Data
public class PrintFormDataCommonReqVO {

    @Schema(description = "经办人")
    private String handlingPerson;

    @Schema(description = "经办部门")
    private String handlingDepartment;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "项目编号")
    private String projectNumber;

    @Schema(description = "项目类型")
    private String projectType;

    @Schema(description = "付款方式")
    private String paymentMethod;

    @Schema(description = "划款金额（元）")
    private String paymentAmount;

    @Schema(description = "金额大写（元）")
    private String amountInWords;

    @Schema(description = "划款日期")
    private String fundTransferDate;

    @Schema(description = "账户管理人")
    private String accountManager;

    @Schema(description = "指令复核人名章")
    private String instructionReviewer;

    @Schema(description = "款项用途")
    private String purposeOfPayment;

    @Schema(description = "付款账户信息-名称")
    private String paymentAccountName;

    @Schema(description = "付款账户信息-账号")
    private String paymentAccountNumber;

    @Schema(description = "付款账户信息-开户行")
    private String paymentAccountBank;

    @Schema(description = "付款账户信息-分支机构")
    private String paymentAccountBranch;

    @Schema(description = "付款账户信息-归属地")
    private String paymentAccountBankLocation;

    @Schema(description = "付款账户信息-大额行号")
    private String paymentLargeAmountBankNumber;

    @Schema(description = "收款账户信息-名称")
    private String receivingAccountName;

    @Schema(description = "收款账户信息-账号")
    private String receivingAccountNumber;

    @Schema(description = "收款账户信息-开户行")
    private String receivingAccountBank;

    @Schema(description = "收款账户信息-分支机构")
    private String receivingAccountBranch;

    @Schema(description = "收款账户信息-归属地")
    private String receivingAccountBankLocation;

    @Schema(description = "收款账户信息-大额行号")
    private String receivingLargeAmountBankNumber;
}
