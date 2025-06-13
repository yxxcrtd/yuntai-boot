package com.joyintech.yuntai.module.system.controller.admin.openapi.vo;

import com.joyintech.yuntai.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Schema(description = "获取token实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAuthLoginReqVO {

    @Schema(description = "账号")
    @NotEmpty(message = "登录账号不能为空")
    private String userName;

    @Schema(description = "外部应用编码(线下申请)")
    @NotEmpty(message = "应用编码不能为空")
    private String appId;

    @Schema(description = "外部应用来源")
    @NotEmpty(message = "应用来源不能为空")
    private String appSource;


}
