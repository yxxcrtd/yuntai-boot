package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: ParallelNode
 * @Author：蔡晓峰
 * @Date：2023/11/26 14:16
 * @github：https://github.com/tsai996/lowflow-design
 * @gitee：https://gitee.com/cai_xiao_feng/lowflow-design
 * @description：并行节点
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParallelNode extends BranchNode {
    private String name;

}
