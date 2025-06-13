package com.joyintech.yuntai.module.infra.controller.admin.db.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 数据源配置创建/修改 Request VO")
@Data
public class DataSourceConfigSaveReqVO {

    @Schema(description = "主键编号", example = "1024")
    private Long id;

    @Schema(description = "数据源类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    @NotNull(message = "数据源类型不能为空")
    private String typeSource;

    @Schema(description = "数据源类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
//    @NotNull(message = "数据源类型不能为空")
    private String typeName;

    @Schema(description = "动态数据源", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "数据源不能为空")
    private Integer source;

    @Schema(description = "数据源编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "A_B_C_D")
    @NotNull(message = "数据源编码不能为空")
    private String code;

    @Schema(description = "ip", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    @NotNull(message = "ip不能为空")
    private String ip;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "********")
    private String remark;

    @Schema(description = "数据源名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    @NotNull(message = "数据源名称不能为空")
    private String name;

    @Schema(description = "数据源连接", requiredMode = Schema.RequiredMode.REQUIRED, example = "jdbc:mysql://127.0.0.1:3306/ruoyi-vue-pro")
    @NotNull(message = "数据源连接不能为空")
    private String url;

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "root")
    @NotNull(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotNull(message = "密码不能为空")
    private String password;

}
