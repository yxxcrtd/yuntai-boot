package com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "管理后台 - 字段定义 VO")
@Data
public class ColumnDefinitionTempVO {

    private String tableSql;

    private List<ColumnDefinitionSaveReqVO> createReqVOs;

}
