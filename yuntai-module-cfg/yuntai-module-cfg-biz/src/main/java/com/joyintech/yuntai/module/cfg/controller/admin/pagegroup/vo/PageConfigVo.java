package com.joyintech.yuntai.module.cfg.controller.admin.pagegroup.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.ConditionalTableSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;

public class PageConfigVo {

    @Schema(description = "条件配置")
    private ConditionalTableSaveReqVO initConfig;

    @Schema(description = "条件配置")
    private ConditionalTableSaveReqVO runConfig;
}
