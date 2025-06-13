package com.joyintech.yuntai.framework.common.util.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/8
 */
public class TreeUtil {

    // 递归查询指定id及其下级节点
    public static TreeNode findByIdWithChildren(List<TreeNode> allGroups, Long id, List<Long> groupIds) {
        Map<Long, TreeNode> groupMap = new HashMap<>();
        // 构建节点映射
        for (TreeNode group : allGroups) {
            groupMap.put(group.getId(), group);
        }
        // 递归构建树形结构
        return buildTree(groupMap, id,groupIds);
    }

    private static TreeNode buildTree(Map<Long, TreeNode> groupMap, Long parentId,List<Long> groupIds) {
        TreeNode parentGroup = groupMap.get(parentId);
        if (parentGroup == null) {
            return null;
        }
        groupIds.add(parentId);
        List<TreeNode> children = new ArrayList<>();
        for (TreeNode group : groupMap.values()) {
            if (parentId.equals(group.getParentId())) {
                TreeNode childGroup = buildTree(groupMap, group.getId(),groupIds);
                children.add(childGroup);
            }
        }
        parentGroup.setChildren(children);
        return parentGroup;
    }
}
