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
public enum CfgServiceTypeEnum {
    HTTP("http", "HTTP服务"),
    JAVA("java", "Java服务"),
    SQL("sql", "SQL服务"),
    JS("js", "JS服务"),
    LAYOUT("layout", "服务编排");

    private String code;

    private String desc;
}
