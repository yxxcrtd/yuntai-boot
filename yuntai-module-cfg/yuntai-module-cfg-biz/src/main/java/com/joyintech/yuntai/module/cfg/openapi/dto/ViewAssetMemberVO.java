package com.joyintech.yuntai.module.cfg.openapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "表单发起入参")
@Data
public class ViewAssetMemberVO {

    private String dyxtjlgh;

    private String dextjlgh;

}
