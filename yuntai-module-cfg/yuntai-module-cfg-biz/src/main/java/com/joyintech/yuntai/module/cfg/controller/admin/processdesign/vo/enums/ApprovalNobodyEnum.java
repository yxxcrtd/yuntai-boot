package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ApprovalNobodyEnum {
    REFUSE("reject", "自动拒绝"),
    PASS("pass", "自动通过"),
    ADMIN("admin", "转交流程管理员"),
    ASSIGN("assign", "指定人员");

    @JsonValue
    private final String nobody;
    private final String description;

    ApprovalNobodyEnum(String nobody, String description) {
        this.nobody = nobody;
        this.description = description;
    }

    public static ApprovalNobodyEnum getByNobody(String nobody) {
        if (nobody == null) {return null;}
        for (ApprovalNobodyEnum assigneeTypeEnum : ApprovalNobodyEnum.values()) {
            if (assigneeTypeEnum.getNobody().equals(nobody)) {
                return assigneeTypeEnum;
            }
        }
        return null;
    }
}
