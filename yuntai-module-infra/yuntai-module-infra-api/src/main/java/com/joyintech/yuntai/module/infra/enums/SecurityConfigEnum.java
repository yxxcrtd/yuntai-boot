package com.joyintech.yuntai.module.infra.enums;

import lombok.AllArgsConstructor;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/6
 */
@AllArgsConstructor
public enum SecurityConfigEnum {
    LOGIN_METHOD("loginMethod", "登陆方式配置"),
    VERIFY_CODE("verifyCode", "显示登陆验证码"),
    WATERMARK("watermark", "显示水印")
    ;
    private String code;
    private String name;

    /**
     * 根据code获取name
     * @param code
     * @return
     */
    public static String getByCode(String code) {
        for (SecurityConfigEnum e : SecurityConfigEnum.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
