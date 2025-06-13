package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: CcNode
 * @Author：蔡晓峰
 * @Date：2023/11/26 14:16
 * @github：https://github.com/tsai996/lowflow-design
 * @gitee：https://gitee.com/cai_xiao_feng/lowflow-design
 * @description：抄送节点
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CcNode extends AssigneeNode {
    // 表单属性
    private List<FormProperty> formProperties = new ArrayList<>();
    // 操作权限
    private Map<String, Boolean> operations = new LinkedHashMap<>();



}
