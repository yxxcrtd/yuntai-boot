package com.joyintech.yuntai.module.cfg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作按钮枚举
 */
@Getter
@AllArgsConstructor
public enum CfgPageLinkEnum {

    INIT("INIT", "初始化"),
    RUN("RUN", "运行中");

    private String code;
    private String desc;
}
