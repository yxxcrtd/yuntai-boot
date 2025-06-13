package com.joyintech.yuntai.module.system.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeptUserTreeNode {
    private String deptId;
    private String deptName;
    // 部门下的用户
    private List<AdminUserRespDTO> users;
    // 子布恩
    private List<DeptUserTreeNode> children;

    public DeptUserTreeNode(String deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.users = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<AdminUserRespDTO> getUsers() {
        return users;
    }

    public void setUsers(List<AdminUserRespDTO> users) {
        this.users = users;
    }

    public List<DeptUserTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<DeptUserTreeNode> children) {
        this.children = children;
    }

    public void addUser(AdminUserRespDTO user) {
        users.add(user);
    }

    public void addChild(DeptUserTreeNode child) {
        children.add(child);
    }
}
