package com.joyintech.yuntai.module.cfg.openapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 外部调用实体 - 泛微
 * @author hzz
 * @since 2024-12-13
 */
@Data
public class FwSaveData {

    @Schema(description = "url")
    private String url;

//    private String pageType;

    private String productId;

//    private String outFlowNodeId;
}
