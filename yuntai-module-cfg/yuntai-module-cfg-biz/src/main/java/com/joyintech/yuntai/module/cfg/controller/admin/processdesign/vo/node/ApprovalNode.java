package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node;


import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.enums.ApprovalMultiEnum;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.enums.ApprovalNobodyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ApprovalNode
 * @Author：蔡晓峰
 * @Date：2023/11/26 14:16
 * @github：https://github.com/tsai996/lowflow-design
 * @gitee：https://gitee.com/cai_xiao_feng/lowflow-design
 * @description：审批节点
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApprovalNode extends AssigneeNode {
    // 表单属性
    private List<FormProperty> formProperties = new ArrayList<>();
    // 操作权限
    private Map<String, Boolean> operations = new LinkedHashMap<>();
    // 多人审批方式
    private ApprovalMultiEnum multi;
    // 多人会签通过百分比
    private BigDecimal multiPercent;
    // 审批人为空时处理方式
    private ApprovalNobodyEnum nobody;
    // 审批人为空时指定人员
    private List<String> nobodyUsers;
    // 任务监听器
    private List<NodeListener> taskListeners;

    //是否回推
    private Boolean isPushBack;
    //是否回推模型数据
    private Boolean isPushBackModelData;
    //指定回推地址
    private String pushBackUrl;
}
