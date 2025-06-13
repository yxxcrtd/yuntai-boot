package com.joyintech.yuntai.module.cfg.sqlgen.enums;

import lombok.Getter;

@Getter
public enum ColType {
    I("I", "数值型"),
    S("S", "字符型"),
    E("E", "枚举型"),
    EQ("EQ", "SQL枚举型"),
    D("D", "日期型"),
    O("O", "对象型"),
    M("M", "其他型"),
    R("R", "关联型");

    private String value;

    private String desc;

    ColType(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ColType getEnumFromString(String string) {
        for (ColType color : values()) {
            if (color.getValue().equals(string)) {
                return color;
            }
        }
        return null;
    }
}
