package com.joyintech.yuntai.module.cfg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作按钮枚举
 */
@Getter
@AllArgsConstructor
public enum CfgPageButtonEnum {

    LEFT("LEFT", "左侧按钮"),
    RIGHT("RIGHT", "右侧按钮"),
    ROW("ROW", "行内按钮");

    private String code;
    private String desc;
}
