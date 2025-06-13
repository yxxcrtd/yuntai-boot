package com.joyintech.yuntai.module.cfg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/7
 */
@Getter
@AllArgsConstructor
public enum CfgFunctionTypeEnum {
    DIR("1", "目录"),
    MODULE("2", "模块");
    private String code;
    private String desc;
}
