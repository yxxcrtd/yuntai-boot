package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: Node
 * @Author：蔡晓峰
 * @Date：2023/11/26 14:16
 * @github：https://github.com/tsai996/lowflow-design
 * @gitee：https://gitee.com/cai_xiao_feng/lowflow-design
 * @description：节点
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = Node.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StartNode.class, name = "start"),
        @JsonSubTypes.Type(value = CcNode.class, name = "cc"),
        @JsonSubTypes.Type(value = ApprovalNode.class, name = "approval"),
        @JsonSubTypes.Type(value = ConditionNode.class, name = "condition"),
        @JsonSubTypes.Type(value = ExclusiveNode.class, name = "exclusive"),
        @JsonSubTypes.Type(value = TimerNode.class, name = "timer"),
        @JsonSubTypes.Type(value = NotifyNode.class, name = "notify"),
        @JsonSubTypes.Type(value = EndNode.class, name = "end")
})
public abstract class Node implements Serializable {
    private static final long serialVersionUID = 132324315232123L;
    // 节点id
    private String id;
    // 父节点id
    private String pid;
    // 节点名称
    private String name;
    // 节点类型
    @JsonTypeId
    private String type;
    // 执行监听器
    private List<NodeListener> executionListeners;
    // 子节点
    private Node child;
    // 分支id
    @JsonIgnore
    private String branchId;
    //外部流程
    private String outProcessNodeId;
    // 流程名称
    private String processName;
    // 菜单ID
    private Long menuId;
    // 关联表单
    private Long pageId;
    // 流程类型(内外)
    private String processType;
    // 备注
    private String remark;
    // 流程ID
    private String flowId;
    //是否回推
    private Boolean isPushBack;
    //是否回推模型数据
    private Boolean isPushBackModelData;
    //指定回推地址
    private String pushBackUrl;
}
