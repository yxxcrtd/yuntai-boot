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
public enum CfgRelationTypeEnum {
    LEFT("LEFT JOIN"),
    RIGHT("RIGHT JOIN"),
    INNER("INNER JOIN"),
    CHILD("CHILD");
    private String code;
}
