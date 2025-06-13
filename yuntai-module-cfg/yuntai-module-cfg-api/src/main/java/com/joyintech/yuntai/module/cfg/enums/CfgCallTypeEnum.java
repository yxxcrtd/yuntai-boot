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
public enum CfgCallTypeEnum {
    METHOD("1"),
    INTERFACE("2"),
    RUN_CODE("3");

    private String code;
}
