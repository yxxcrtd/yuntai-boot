package com.joyintech.yuntai.module.system.controller.admin.dept.vo.dept;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Schema(description = "管理后台 - 部门信息 Response VO")
@Data
public class DeptUserRespVO {

    @Schema(description = "部门编号", example = "1024")
    private Long id;

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "云台")
    private String name;

    @Schema(description = "父部门 ID", example = "1024")
    private Long parentId;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer sort;

    @Schema(description = "dept/user", requiredMode = Schema.RequiredMode.REQUIRED, example = "dept/user")
    private String type;

    @Schema(description = "是否叶子节点", requiredMode = Schema.RequiredMode.REQUIRED, example = "dept/user")
    private Boolean isLeaf;

    @Schema(description = "子节点", example = "1024",hidden = true)
    @JsonIgnore
    private List<DeptUserRespVO> children ;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptUserRespVO that = (DeptUserRespVO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
