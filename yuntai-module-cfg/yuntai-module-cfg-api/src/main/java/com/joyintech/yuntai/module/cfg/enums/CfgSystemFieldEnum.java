package com.joyintech.yuntai.module.cfg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/31
 */
@Getter
@AllArgsConstructor
public enum CfgSystemFieldEnum {
    USER_ID("userId", "用户ID"),
    DATE("date", "日期"),
    STRING("string", "字符串"),
    NUMBER("number", "数字"),
    CREATE_FLAG("0", "新增标记"),
    DELETE_FLAG("1", "删除标记"),
    UPDATE_FLAG("2", "更新标记"),
    PRIMARY_KEY("3", "主键");

    private String code;

    private String desc;


}
