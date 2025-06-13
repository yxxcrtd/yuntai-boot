package com.joyintech.yuntai.module.cfg.dal.dataobject.processnode;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 流程设计节点 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_process_node")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessNodeDO extends BaseDO {
    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 流程设计ID
     */
    private Long processId;
    /**
     * 父级ID
     */
    private String pid;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 审批对象
     */
    private String assigneeType;
    /**
     * 表单内人员
     */
    private String formUser;
    /**
     * 多人审批方式
     */
    private String multi;
    /**
     * 节点类型
     */
    private String type;
    /**
     * 分支ID
     */
    private String branchId;
    /**
     * 表单内角色
     */
    private String formRole;
    /**
     * 审批人
     */
    private String users;
    /**
     * 审批人角色
     */
    private String roles;
    /**
     * 主管
     */
    private String leader;
    /**
     * 组织主管
     */
    private String orgLeader;
    /**
     * 发起人自选：true-单选，false-多选
     */
    private Boolean choice;
    /**
     * 是否发起人自己
     */
    private Boolean self;
    /**
     * 是否默认条件分支
     */
    private Boolean def;
    /**
     * 多人会签通过百分比
     */
    private BigDecimal multiPercent;
    /**
     * 审批人为空时处理方式
     */
    private String nobody;
    /**
     * 审批人为空时指定人员,多个逗号分隔
     */
    private String nobodyUsers;
    /**
     * 等待方式
     */
    private String waitType;
    /**
     * 单位
     */
    private String unit;
    /**
     * 等待时间
     */
    private Integer duration;
    /**
     * 指定时间
     */
    private String timeDate;
    /**
     * 通知类型类型,多个逗号分隔
     */
    private String types;
    /**
     * 通知主题
     */
    private String subject;
    /**
     * 通知内容
     */
    private String content;

    /**
     * 外部流程节点ID
     */
    private String outProcessNodeId;

    /**
     * 流程名称
     */
    @TableField(exist = false)
    private String processName;
    /**
     * 菜单ID
     */
    @TableField(exist = false)
    private Long menuId;
    /**
     * 关联表单
     */
    @TableField(exist = false)
    private Long pageId;
    /**
     * 流程类型(内外)
     */
    @TableField(exist = false)
    private String processType;
    /**
     * 备注
     */
    @TableField(exist = false)
    private String remark;
    /**
     * 流程ID
     */
    @TableField(exist = false)
    private String flowId;

    //是否回推
    private Boolean isPushBack;

    //是否回推模型数据
    private Boolean isPushBackModelData;

    //指定回推地址
    private String pushBackUrl;

}
