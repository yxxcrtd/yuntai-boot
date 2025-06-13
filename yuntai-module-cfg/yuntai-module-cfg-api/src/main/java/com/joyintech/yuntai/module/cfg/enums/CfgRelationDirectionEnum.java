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
public enum CfgRelationDirectionEnum {
    IN_CHILD(1),//关联字段在子表中
    IN_MAIN(2);//关联字段在主表中

    private Integer code;
}
