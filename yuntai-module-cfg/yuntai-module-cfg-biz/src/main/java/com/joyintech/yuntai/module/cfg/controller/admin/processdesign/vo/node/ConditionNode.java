package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.condition.FilterRules;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: ConditionNode
 * @Author：蔡晓峰
 * @Date：2023/11/26 14:16
 * @github：https://github.com/tsai996/lowflow-design
 * @gitee：https://gitee.com/cai_xiao_feng/lowflow-design
 * @description：条件(分支)
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConditionNode extends Node {
    private Boolean def;//是否默认条件分支
    private FilterRules conditions;
    @JsonIgnore
    private Map<String, String> operatorMap = new HashMap<>();

    {
        // 等于
        operatorMap.put("eq", "var:eq(%s, %s)");
        // 不等于
        operatorMap.put("ne", "var:notEquals(%s, %s)");
        // 包含
        operatorMap.put("in", "var:containsAny(%s, %s)");
        // 不包含
        operatorMap.put("ni", "var:notContainsAny(%s, %s)");
        // 为空
        operatorMap.put("ul", "var:isNull(%s)");
        // 不为空
        operatorMap.put("nu", "var:isNotNull(%s)");
        // 字符包含
        operatorMap.put("lk", "var:contains(%s, %s)");
        // 大于
        operatorMap.put("gt", "var:gt(%s, %s)");
        // 小于
        operatorMap.put("lt", "var:lt(%s, %s)");
        // 小于或等于
        operatorMap.put("le", "var:lte(%s, %s)");
        // 大于或等于
        operatorMap.put("ge", "var:gte(%s, %s)");
    }






}
