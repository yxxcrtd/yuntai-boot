package com.joyintech.yuntai.module.cfg.dal.dataobject.moduleinfo;

import lombok.Data;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/11
 */
@Data
public class ModuleFieldInfoDO {
    private Long relationId;
    private Long tableId;
    private Long moduleId;
    private String tableName;
    private String tableComment;
    private Boolean isMain;
    private String relationType;
    private String tableType;
    private String joinType;
    private String fieldName;
    private String fieldAliasName;
    private String defaultValue;
    private Boolean tableReadonly;
    private Boolean fieldReadonly;
    private String mainFieldName;
    private String relationFieldName;
    private Boolean isPrimaryKey;
    private String fieldType;
    private String relationDirection;

}
