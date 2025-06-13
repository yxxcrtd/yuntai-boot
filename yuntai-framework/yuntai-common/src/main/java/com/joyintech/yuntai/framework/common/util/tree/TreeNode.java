package com.joyintech.yuntai.framework.common.util.tree;

import lombok.Data;

import java.util.List;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/8
 */
@Data
public class TreeNode {
    /**
     * id
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 子节点
     */
    private List<TreeNode> children;
}
