package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimerWaitType {
    DURATION("duration", "持续时间"),
    DATE("date", "日期");

    @JsonValue
    private final String type;
    private final String description;

    public static TimerWaitType getByType(String type) {
        if (type == null) {return null;}
        for (TimerWaitType assigneeTypeEnum : TimerWaitType.values()) {
            if (assigneeTypeEnum.getType().equals(type)) {
                return assigneeTypeEnum;
            }
        }
        return null;
    }
}
