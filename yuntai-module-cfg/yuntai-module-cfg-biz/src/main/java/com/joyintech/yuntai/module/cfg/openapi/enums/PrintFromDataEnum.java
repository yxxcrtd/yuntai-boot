package com.joyintech.yuntai.module.cfg.openapi.enums;

public enum PrintFromDataEnum {
    HANDLING_PERSON("HANDLING_PERSON","经办人"),
    HANDLING_DEPARTMENT("HANDLING_DEPARTMENT","经办部门"),
    PROJECT_NAME("PROJECT_NAME","项目名称"),
    PROJECT_NUMBER("PROJECT_NUMBER","项目名称"),
    PROJECT_TYPE("PROJECT_TYPE","项目类型"),
    PAYMENT_METHOD("PAYMENT_METHOD","付款方式"),
    PAYMENT_AMOUNT("PAYMENT_AMOUNT","划款金额（元）"),
    AMOUNT_IN_WORDS("AMOUNT_IN_WORDS","金额大写（元）"),
    FUND_TRANSFER_DATE("FUND_TRANSFER_DATE","划款日期"),
    ACCOUNT_MANAGER("ACCOUNT_MANAGER","账户管理人"),
    INSTRUCTION_REVIEWER("INSTRUCTION_REVIEWER","指令复核人名章"),
    PURPOSE_OF_PAYMENT("PURPOSE_OF_PAYMENT","款项用途"),
    PAYMENT_ACCOUNT_NAME("PAYMENT_ACCOUNT_NAME","付款账户信息-名称"),
    PAYMENT_ACCOUNT_NUMBER("PAYMENT_ACCOUNT_NUMBER","付款账户信息-账号"),
    PAYMENT_ACCOUNT_BANK("PAYMENT_ACCOUNT_BANK","付款账户信息-开户行"),
    PAYMENT_ACCOUNT_BRANCH("PAYMENT_ACCOUNT_BRANCH","付款账户信息-分支机构"),
    PAYMENT_ACCOUNT_BANK_LOCATION("PAYMENT_ACCOUNT_BANK_LOCATION","付款账户信息-归属地"),
    PAYMENT_LARGE_AMOUNT_BANK_NUMBER("PAYMENT_LARGE_AMOUNT_BANK_NUMBER","付款账户信息-大额行号"),
    RECEIVING_ACCOUNT_NAME("RECEIVING_ACCOUNT_NAME","收款账户信息-名称"),
    RECEIVING_ACCOUNT_NUMBER("RECEIVING_ACCOUNT_NUMBER","收款账户信息-账号"),
    RECEIVING_ACCOUNT_BANK("RECEIVING_ACCOUNT_BANK","收款账户信息-开户行"),
    RECEIVING_ACCOUNT_BRANCH("RECEIVING_ACCOUNT_BRANCH","收款账户信息-分支机构"),
    RECEIVING_ACCOUNT_BANK_LOCATION("RECEIVING_ACCOUNT_BANK_LOCATION","收款账户信息-归属地"),
    RECEIVING_LARGE_AMOUNT_BANK_NUMBER("RECEIVING_LARGE_AMOUNT_BANK_NUMBER","收款账户信息-大额行号");
    /**
     * 类型
     */
    private final String code ;
    /**
     * 名称
     */
    private final String name;

    PrintFromDataEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
