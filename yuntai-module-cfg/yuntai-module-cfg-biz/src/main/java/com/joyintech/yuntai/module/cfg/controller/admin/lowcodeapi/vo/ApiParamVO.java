package com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/15
 */
@Schema(description = "管理后台 -通用参数")
@Data
@ToString(callSuper = true)
public class ApiParamVO {
    @Schema(description = "管理后台 -通用查询参数")
    private Map<String, Object> queryParam;
    @Schema(description = "管理后台 -通用保存参数")
    private Map<String, Object> bodyParam;

}
