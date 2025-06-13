package com.joyintech.yuntai.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;

@Schema(description = "管理后台 - 登录 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginRespVO {

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userId;

    @Schema(description = "访问令牌", requiredMode = Schema.RequiredMode.REQUIRED, example = "happy")
    private String accessToken;

    @Schema(description = "刷新令牌", requiredMode = Schema.RequiredMode.REQUIRED, example = "nice")
    private String refreshToken;

    @Schema(description = "过期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime expiresTime;

    /**
     * 用户账号
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String realName;

    /**
     * 部门id
     */
    @TableField(exist = false)
    private String departId;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String departName;

    @TableField(exist = false)
    private String workNo;

}
