package com.joyintech.yuntai.module.infra.controller.admin.db.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 数据源配置 Response VO")
@Data
public class DataSourceConfigRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "数据源名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    private String name;

    @Schema(description = "数据源连接", requiredMode = Schema.RequiredMode.REQUIRED, example = "jdbc:mysql://127.0.0.1:3306/ruoyi-vue-pro")
    private String url;

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "root")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "root")
    private String password;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "数据源类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    private String typeSource;

    @Schema(description = "数据源类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    private String typeName;

    @Schema(description = "动态数据源", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer source;

    @Schema(description = "数据源编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "A_B_C_D")
    private String code;

    @Schema(description = "ip", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    private String ip;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "********")
    private String remark;

}
