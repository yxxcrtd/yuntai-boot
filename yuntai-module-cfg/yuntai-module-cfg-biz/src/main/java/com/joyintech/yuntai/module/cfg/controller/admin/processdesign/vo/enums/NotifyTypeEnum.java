package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum NotifyTypeEnum {
    SITE("site", "站内"),
    EMAIL("email", "邮件"),
    SMS("sms", "短信"),
    WECHAT("wechat", "微信"),
    DINGTALK("dingtalk", "钉钉"),
    FEISHU("feishu", "飞书");

    @JsonValue
    private final String type;
    private final String description;

    public static NotifyTypeEnum getByType(String type) {
        if (type == null) {return null;}
        for (NotifyTypeEnum assigneeTypeEnum : NotifyTypeEnum.values()) {
            if (assigneeTypeEnum.getType().equals(type)) {
                return assigneeTypeEnum;
            }
        }
        return null;
    }

    public static List<String> getByType(List<NotifyTypeEnum> typeList) {
        List<String> list = new ArrayList<>();
        if (typeList == null || typeList.isEmpty()) {return list;}
        for (NotifyTypeEnum assigneeTypeEnum : typeList) {
            list.add(assigneeTypeEnum.getType());
        }
        return list;
    }
}
