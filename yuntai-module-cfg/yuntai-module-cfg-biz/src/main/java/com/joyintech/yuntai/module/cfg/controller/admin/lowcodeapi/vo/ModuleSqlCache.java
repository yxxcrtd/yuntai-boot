package com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo;

import lombok.Data;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/11
 */
@Data
public class ModuleSqlCache {

    /**
     * 模型id
     */
    private Long moduleId;

    /**
     * module table表主键
     */
    private Long moduleTableId;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表别名
     */
    private String tableAlias;
    /**
     * sql内容
     */
    private String actionSql;

    /**
     * sql类型
     */
    private String actionType;

    /**
     * 是否主表 因对接联调泛微，需要返回新增数据ID，顾根据主表SQL进行ID返回
     */
    private Boolean isMain;


}
