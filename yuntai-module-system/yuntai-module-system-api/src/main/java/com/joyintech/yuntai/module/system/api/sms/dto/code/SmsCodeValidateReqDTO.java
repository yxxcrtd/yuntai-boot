package com.joyintech.yuntai.module.system.api.sms.dto.code;

import com.joyintech.yuntai.framework.common.validation.InEnum;
import com.joyintech.yuntai.framework.common.validation.Mobile;
import com.joyintech.yuntai.module.system.enums.sms.SmsSceneEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 短信验证码的校验 Request DTO
 *
 * @author 兆尹云台
 */
@Data
public class SmsCodeValidateReqDTO {

    /**
     * 手机号
     */
    @Mobile
    @NotEmpty(message = "手机号不能为空")
    private String mobile;
    /**
     * 发送场景
     */
    @NotNull(message = "发送场景不能为空")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;
    /**
     * 验证码
     */
    @NotEmpty(message = "验证码")
    private String code;

}
