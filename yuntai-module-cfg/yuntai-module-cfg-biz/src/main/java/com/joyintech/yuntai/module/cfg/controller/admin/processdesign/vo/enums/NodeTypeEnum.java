package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/1
 */
@Getter
@AllArgsConstructor
public enum NodeTypeEnum {
    START("start"),
    CC("cc"),
    APPROVAL("approval"),
    CONDITION("condition"),
    EXCLUSIVE("exclusive"),
    TIMER("timer"),
    NOTIFY("notify"),
    END("end");

    private String code;

}
