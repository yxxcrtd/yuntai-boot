package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: StartNode
 * @Author：蔡晓峰
 * @Date：2023/11/26 14:16
 * @github：https://github.com/tsai996/lowflow-design
 * @gitee：https://gitee.com/cai_xiao_feng/lowflow-design
 * @description：启动节点
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StartNode extends Node {
    // 表单属性
    private List<FormProperty> formProperties = new ArrayList<>();

}
