package com.joyintech.yuntai.module.cfg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 查询操作符
 *
 * @Author Administrator
 * @Date 2024/10/25
 */
@Getter
@AllArgsConstructor
public enum CfgOperateEnum {
    EQUALS("eq", "等于"),
    EQUALS_NO_CASE("eq-ig-case", "等于忽略大小写"),
    GREATER("gt", "大于"),
    GREATER_EQUALS("gte", "大于等于"),
    LESS("lt", "小于"),
    LESS_EQUALS("lte", "小于等于"),
    NOT_EQUALS("neq", "不等于"),
    LIKE("like", "相似"),
    LEFT_LIKE("left-like", "左相似"),
    RIGHT_LIKE("right-like", "右相似"),
    IN("in", "在...中"),
    NOT_IN("not-in", "不在...中"),
    BETWEEN("between", "在...之间");

    private String code;
    private String label;


}
