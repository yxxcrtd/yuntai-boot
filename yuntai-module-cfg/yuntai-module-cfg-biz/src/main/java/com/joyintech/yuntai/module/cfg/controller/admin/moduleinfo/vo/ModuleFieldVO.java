package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 模型 处理回显字段 VO")
@Data
public class ModuleFieldVO {

    /**
     * 系统字段别名
     */
    private String sysAliasName;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 回显表
     */
    private String textTable;

    /**
     * 回显字段
     */
    private String textColumn;

    private String tableName;

    private String tableAlias;

    private String textTableKey;
}
