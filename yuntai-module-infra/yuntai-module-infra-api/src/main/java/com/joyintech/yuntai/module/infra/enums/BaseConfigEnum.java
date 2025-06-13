package com.joyintech.yuntai.module.infra.enums;

import lombok.AllArgsConstructor;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/6
 */
@AllArgsConstructor
public enum BaseConfigEnum {
    TITLE("title", "标题"),
    LOGO("logo", "系统图标"),
    KEYWORD("keyword", "关键字"),
    REMARK("description", "描述"),
    URL("host", "域名")
    ;
    private String code;
    private String name;

    /**
     * 根据code获取name
     * @param code
     * @return
     */
    public static String getByCode(String code) {
        for (BaseConfigEnum e : BaseConfigEnum.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
