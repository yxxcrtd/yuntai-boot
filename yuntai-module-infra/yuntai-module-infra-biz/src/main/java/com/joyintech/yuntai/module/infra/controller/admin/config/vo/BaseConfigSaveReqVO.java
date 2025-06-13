package com.joyintech.yuntai.module.infra.controller.admin.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/6
 */
@Data
@Schema(description = "管理后台 - 基础配置信息 Response VO")
public class BaseConfigSaveReqVO {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "域名")
    private String host;

    @Schema(description = "系统图标")
    private String logo;

    @Schema(description = "是否开启登录验证码")
    private Boolean verifyCode;

    @Schema(description = "是否开启水印")
    private Boolean watermark;

    @Schema(description = "登陆方式配置:1-账号密码,2-手机验证码,多个逗号分割")
    private String loginMethod;

}
