package com.joyintech.yuntai.module.cfg.tabledefinition.dto;

import lombok.Data;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/31
 */
@Data
public class TableDefinitionDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 数据源id
     */
    private Long datasourceId;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 描述
     */
    private String remark;

    /**
     * 是否生效,true:生效,false:不生效
     */
    private Boolean status;

    /**
     * 是否系统表,true:是,false:否
     */
    private Boolean isSys;

    /**
     * 表类型
     */
    private String tableType;
}
