package com.joyintech.yuntai.module.cfg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/18
 */
@Getter
@AllArgsConstructor
public enum CfgModuleTypeEnum {
    COMMON("1", "普通模型"),
    SQL("2", "SQL"),
    JAVA_BEAN("3", "JavaBean"),
    HTTP("4", "http");


    private String code;
    private String desc;
}
