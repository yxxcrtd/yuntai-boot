package com.joyintech.yuntai.module.infra.api.db.dto;

import lombok.Data;

/**
 * 数据源配置dto Response DTO
 *
 * @author 兆尹云台
 */
@Data
public class DataSourceConfigRespDTO {

    /**
     * 主键编号 - Master 数据源
     */
    public static final Long ID_MASTER = 0L;

    /**
     * 主键编号
     */
    private Long id;
    /**
     * 连接名
     */
    private String name;

    /**
     * 数据源连接
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 数据源类型
     */
    private String typeSource;

    /**
     * 数据源类型名称
     */
    private String typeName;

}
