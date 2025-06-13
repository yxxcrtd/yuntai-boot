package com.joyintech.yuntai.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 菜单列表 Request VO")
@Data
public class MenuListReqVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "页面设置ID")
    private Long pageId;

    @Schema(description = "菜单名称，模糊匹配", example = "云台")
    private String name;

    @Schema(description = "展示状态，参见 CommonStatusEnum 枚举类", example = "1")
    private Integer status;

    @Schema(description = "菜单所属模块，参见 MoudleTypeEnum 枚举类", example = "0")
    private Integer moduleType;

    @Schema(description = "低码功能模块id", example = "1")
    private Long functionId;

    @Schema(description = "路由", example = "1")
    private String path;

    @Schema(description = "菜单类型", example = "1")
    private Integer type;

}
