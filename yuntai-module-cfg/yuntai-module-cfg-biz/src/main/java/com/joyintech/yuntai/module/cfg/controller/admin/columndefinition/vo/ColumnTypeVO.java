package com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 字段类型 DO
 *
 * @author 兆尹云台
 */
@Data
@Schema(description = "管理后台 - 列类型 Response VO")
public class ColumnTypeVO  {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 主键ID
     */
    private Long dataDomainId;

    /**
     * 数据库类型,mysql,oracle
     */
    private String typeSource;

    /**
     * 列类型
     */
    private String columnTypeWithId;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * 列类型名称
     */
    private String columnTypeName;

    /**
     * 列长度
     */
    private Integer columnLength;

    /**
     * 列长度
     */
    private Integer columnScale;

    /**
     * 输入规则：0-任意输入,1-需输入长度,2-需输入长度和精度
     */
    private Integer inputRule;
}
