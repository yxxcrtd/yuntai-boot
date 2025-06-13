package com.joyintech.yuntai.module.system.controller.admin.dept.vo.dept;

import com.joyintech.yuntai.module.system.api.user.dto.AdminUserRespDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeptUserTreeNodeVO {
    private String deptId;
    private String deptName;
    // 部门下的用户
    private List<AdminUserRespDTO> users;
    // 子布恩
    private List<DeptUserTreeNodeVO> children;

    public DeptUserTreeNodeVO(String deptId, String deptName) {
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

    public List<DeptUserTreeNodeVO> getChildren() {
        return children;
    }

    public void setChildren(List<DeptUserTreeNodeVO> children) {
        this.children = children;
    }

    public void addUser(AdminUserRespDTO user) {
        users.add(user);
    }

    public void addChild(DeptUserTreeNodeVO child) {
        children.add(child);
    }
}
