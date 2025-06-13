package com.joyintech.yuntai.module.cfg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/23
 */
@Getter
@AllArgsConstructor
public enum CfgParamTypeEnum {
    REQUEST("请求参数"),
    RESPONSE("响应参数");

    private String desc;

    public static String getByName(String name) {
        for(CfgParamTypeEnum e : values()) {
            if(e.name().equals(name)) {return e.desc;}
        }
        return null;
    }
}
