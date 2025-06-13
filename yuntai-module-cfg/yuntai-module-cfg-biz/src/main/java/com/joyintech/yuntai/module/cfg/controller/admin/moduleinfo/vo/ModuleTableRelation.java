package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ModuleTableRelation {

    public void init() {
        allFields = Sets.newHashSet();
        fields = Sets.newHashSet();
        pkFields = Sets.newHashSet();
        deleteWhereFields = Sets.newHashSet();
        updateWhereFields = Sets.newHashSet();
        selectFields = new HashSet<>();
        selectWhereFields = Sets.newHashSet();
        relationFields = Lists.newArrayList();
        joinTable = Lists.newArrayList();
        childTable = Lists.newArrayList();
        isLogicDelete = true;
    }

    /**
     * 是否逻辑删除
     */
    private Boolean isLogicDelete;

    /**
     * 数据源id
     */
    private Long dataSourceId;

    /**
     * 模型表id
     */
    private Long moduleTableId;

    /**
     * 模型id
     */
    private Long moduleId;

    /**
     * 模型的code
     */
    private String moduleCode;

    /**
     * 表id
     */
    private Long tableId;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表 类型 ： 虚拟表 virtual_table
     */
    private String tableType;

    /**
     * 表 SQL，select语句，api中 拼接 虚拟表 select 语句
     */
    private String tableSql;

    /**
     * 表别名
     */
    private String tableAlias;

    /**
     * 是否只读表
     */
    private Boolean readonly;

    /**
     * 记录下父级表id
     */
    private Long mainTableId;

    /**
     * 记录下父级id
     */
    private Long relationModuleTableTd;

    /**
     * 是否子表
     */
    private Boolean isChild;

    /**
     * 是否主表
     */
    private Boolean isMain;

    /**
     * 关联类型 left /right join
     */
    private String relationType;

    /**
     * 参数类型
     */
    private String parameterType;

    /**
     * 参数名称
     */
    private String parameterName;

    /**
     * where 条件
     */
    private String searchSql;

    /**
     * join sql
     */
    private List<String> joinSql;

    /**
     * join sql
     */
    private List<String> whereSqlList;

    /**
     * 查询字段
     */
    private Set<String> selectFields;

    /**
     * 所有字段
     */
    private Set<TableField> allFields ;

    /**
     * 表字段
     */
    private Set<TableField> fields ;
    /**
     * 主键字段
     */
    private Set<TableField> pkFields ;

    /**
     * 删除条件字段
     */
    private Set<TableField> deleteWhereFields ;
    /**
     * 更新条件字段
     */
    private Set<TableField> updateWhereFields;
    /**
     * 查询条件字段
     */
    private Set<TableField> selectWhereFields;

    /**
     * 表关联字段
     */
    private List<ModuleRelationField> relationFields;

    /**
     * join的表
     */
    private List<ModuleTableRelation> joinTable;

    /**
     * 子表
     */
    private List<ModuleTableRelation> childTable ;

    public Boolean getIsChild() {
        if (isChild == null) {
            return false;
        }
        return isChild;
    }


    public Boolean getIsMain() {
        if (isMain == null) {
            return false;
        }
        return isMain;
    }

    public Boolean getReadonly() {
        if (readonly == null) {
            return false;
        }
        return readonly;
    }

    public boolean isVirtualTable() {
        return "virtual_table".equalsIgnoreCase(tableType);
    }

    /**
     * 虚拟表组装SQL时，返回SQL，而不是表名
     */
    public String getTableNameForSql() {
        if (isVirtualTable()) {
            return " ( " + tableSql + " ) ";
        }
        return tableName;
    }
}
