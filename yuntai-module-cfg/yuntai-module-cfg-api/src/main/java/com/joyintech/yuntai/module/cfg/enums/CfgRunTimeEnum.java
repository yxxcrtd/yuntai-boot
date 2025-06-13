package com.joyintech.yuntai.module.cfg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/20
 */
@Getter
@AllArgsConstructor
public enum CfgRunTimeEnum {
    LOAD_BEFORE("api-load-before"),
    LOAD_AFTER("api-load-after");

    private String code;
}
