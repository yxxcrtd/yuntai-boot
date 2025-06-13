package com.joyintech.yuntai.module.cfg.service.moduleinfo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.yulichang.toolkit.SpringContentUtils;
import com.google.common.collect.Lists;
import com.joyintech.yuntai.framework.common.util.json.JsonUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleFieldSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleTableRelation;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.TableField;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbsystemcolumn.DbSystemColumnDO;
import com.joyintech.yuntai.module.cfg.enums.CfgActionTypeEnum;
import com.joyintech.yuntai.module.cfg.enums.CfgSystemFieldEnum;
import com.joyintech.yuntai.module.cfg.service.lowcodeapi.LowCodeService;
import com.joyintech.yuntai.module.cfg.service.lowcodeapi.ModuleCacheComponent;

import io.swagger.v3.core.util.Json;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/21
 */
@Slf4j
public class ModuleSqlHelper {
    public static final List<String> TABLE_ALIAS = Lists.newArrayList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
    public static final String FIELD_FORMAT = "%s.%s as \"%s_%d\"";
    public static final String SELECT_FIELD_FORMAT = "%s.%s as \"%s\"";
    @Getter
    private static String deleteField;
    @Getter
    private static final Set<TableField> createSystemField = new HashSet<>();
    @Getter
    private static final Set<TableField> updateSystemField = new HashSet<>();

    /**
     * 设置数据源
     *
     * @param dataSourceId 数据源id
     */
    public static void initDefault(Long dataSourceId) {
        ModuleCacheComponent dbSystemFieldConfig = SpringContentUtils.getBean(ModuleCacheComponent.class);
        deleteField = dbSystemFieldConfig.getDeleteField(dataSourceId);
        List<DbSystemColumnDO> list = dbSystemFieldConfig.getSystemFieldCache(dataSourceId);
        if (CollUtil.isNotEmpty(list)) {//初始化默认字段
            createSystemField.clear();
            updateSystemField.clear();
            list.forEach(item -> {
                if (!CfgSystemFieldEnum.DELETE_FLAG.getCode().equals(item.getCategory())) {
                    TableField field = TableField.newField(item.getColumnName(), item.getColumnName(), null);
                    field.setFieldComment(item.getColumnComment());
                    createSystemField.add(field);
                    if (CfgSystemFieldEnum.UPDATE_FLAG.getCode().equals(item.getCategory())) {
                        updateSystemField.add(field);
                    }
                }
            });
        } else {
            log.error("数据源[{}]没有默认系统字段", dataSourceId);
        }
    }

    /**
     * 插入sql
     *
     * @return
     */
    public static String insertSql(ModuleTableRelation table) {
        String insertFormat = "INSERT INTO %s (%s,%s,%s) VALUES (%s,%s,#{%s})";
        if (!Boolean.TRUE.equals(table.getIsLogicDelete())) {
            insertFormat = "INSERT INTO %s (%s,%s) VALUES (%s,%s)";
        }
        List<TableField> list = getInsertFields(table,true);
        if (CollUtil.isNotEmpty(list)) {
            String fields = CollUtil.join(list.stream().map(TableField::getFieldName).collect(Collectors.toList()), ",");
            String values = CollUtil.join(list.stream().map(k -> k.getFieldValueWithWell(true)).collect(Collectors.toList()), ",");
            String createFields = CollUtil.join(createSystemField.stream().map(TableField::getFieldName).collect(Collectors.toList()), ",");
            String createValues = CollUtil.join(createSystemField.stream().map(TableField::getFieldValueInModuleTableIdWithWell).collect(Collectors.toList()), ",");
            if (Boolean.TRUE.equals(table.getIsLogicDelete())) {
                return String.format(insertFormat, table.getTableName(), fields,createFields, deleteField, values,createValues, deleteField);
            } else {
                return String.format(insertFormat, table.getTableName(), fields, createFields,values,createValues);
            }
        }
        return null;
    }

    /**
     * 更新sql
     *
     * @return
     */
    public static String updateSql(ModuleTableRelation table) {
        String updateFormat = "UPDATE %s SET %s,%s WHERE %s";
        List<TableField> list = getInsertFields(table,false);
        if (CollUtil.isNotEmpty(list)) {
            String setFields = CollUtil.join(list.stream().map(field -> String.format("%s=%s", field.getFieldName(), field.getFieldValueWithWell(true))).collect(Collectors.toList()), ",");
            String updateFields = CollUtil.join(updateSystemField.stream().map(field -> String.format("%s=%s", field.getFieldName(), field.getFieldValueInModuleTableIdWithWell())).collect(Collectors.toList()), ",");
            String whereFields = CollUtil.join(table.getUpdateWhereFields().stream().map(field -> String.format("%s=%s", field.getFieldName(), field.getFieldValueWithWell(false))).collect(Collectors.toList()), " and ");
            return String.format(updateFormat, table.getTableName(), setFields, updateFields, whereFields);
        }
        return null;
    }

    /**
     * 删除sql
     *
     * @return
     */
    public static String deleteSql(ModuleTableRelation table) {
        String deleteFormat = "UPDATE %s SET %s=#{%s},%s WHERE %s";
        if (!Boolean.TRUE.equals(table.getIsLogicDelete())) {
            deleteFormat = "DELETE FROM %s WHERE %s";
        }
        if (CollUtil.isNotEmpty(table.getDeleteWhereFields())) {
            String updateFields = CollUtil.join(updateSystemField.stream().map(field -> String.format("%s=%s", field.getFieldName(), field.getFieldValueInModuleTableIdWithWell())).collect(Collectors.toList()), ",");
            String whereFields = CollUtil.join(table.getDeleteWhereFields().stream().map(field -> String.format("%s=%s", field.getFieldName(), field.getFieldValueWithWell(false))).collect(Collectors.toList()), " and ");
            if (Boolean.TRUE.equals(table.getIsLogicDelete())) {
                return String.format(deleteFormat, table.getTableName(), deleteField, deleteField, updateFields, whereFields);
            } else {
                return String.format(deleteFormat, table.getTableName(), whereFields);
            }
        }
        return null;
    }

    /**
     * 删除sql
     *
     * @return
     */
    public static String deleteSqlWithIn(ModuleTableRelation table) {
        String deleteFormat = "UPDATE %s SET %s=#{%s},%s WHERE %s";
        if (!Boolean.TRUE.equals(table.getIsLogicDelete())) {
            deleteFormat = "DELETE FROM %s WHERE %s";
        }
        if (CollUtil.isNotEmpty(table.getDeleteWhereFields())) {
            String updateFields = CollUtil.join(updateSystemField.stream().map(field -> String.format("%s=%s", field.getFieldName(), field.getFieldValueInModuleTableIdWithWell())).collect(Collectors.toList()), ",");
            String whereFields = CollUtil.join(table.getDeleteWhereFields().stream().map(field -> String.format("%s in %s", field.getFieldName(),foreachSql(field.getFieldValue(),field.getModuleTableId()))).collect(Collectors.toList()), " and ");
            if (Boolean.TRUE.equals(table.getIsLogicDelete())) {
                return String.format(deleteFormat, table.getTableName(), deleteField, deleteField, updateFields, whereFields);
            } else {
                return String.format(deleteFormat, table.getTableName(), whereFields);
            }
        }
        return null;
    }

    /**
     * 构建插入字段
     *
     * @param table
     * @return
     */
    public static List<TableField> getInsertFields(ModuleTableRelation table,Boolean containsPk) {
        if (CollUtil.isEmpty(table.getFields())) {
            return CollUtil.newArrayList();
        }
        List<TableField> insertFields = CollUtil.newArrayList();
        for (TableField field : table.getFields()) {
            if (!inCreateSystemField(field)) {
                if (!containsPk && Boolean.TRUE.equals(field.getIsPk())) {
                    continue;
                }
                // 计算字段，忽略
                if (StringUtils.isNotBlank(field.getComputeSql())) {
                    continue;
                }
                if (!deleteField.equalsIgnoreCase(field.getFieldName())) {
                    insertFields.add(field);
                }
            }

        }
        return insertFields;
    }

    /**
     * 构建插入字段
     *
     * @param table
     * @return
     */
    public static List<TableField> getFields(ModuleTableRelation table,Boolean containsPk,Boolean containsCreate) {
        if (CollUtil.isEmpty(table.getFields())) {
            return CollUtil.newArrayList();
        }
        List<TableField> fields = CollUtil.newArrayList();
        for (TableField field : table.getAllFields()) {
            if (containsCreate || !inCreateSystemField(field)) {
                if (!containsPk && Boolean.TRUE.equals(field.getIsPk())) {
                    continue;
                }
                // 计算字段，忽略
                if (StringUtils.isNotBlank(field.getComputeSql())) {
                    continue;
                }
                if (!deleteField.equalsIgnoreCase(field.getFieldName())) {
                    fields.add(field);
                }
            }
        }
        return fields;
    }

    /**
     * 判断是否在系统创建字段中
     * @param field
     * @return
     */
    private static boolean inCreateSystemField(TableField field) {
        for (TableField insertField : createSystemField) {
            if (insertField.getFieldName().equals(field.getFieldName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 构建查询字段
     *
     * @param fieldName
     * @param tableAlias
     * @param moudleTableId
     * @return
     */
    public static String selectKeyWithFilter(String fieldName, String tableAlias, Long moudleTableId) {
        if (fieldName.equalsIgnoreCase(deleteField)) {
            return null;
        }
        return String.format(FIELD_FORMAT, tableAlias, fieldName, fieldName, moudleTableId);
    }

    /**
     * 构建查询字段
     *
     * @param fieldName
     * @param tableAlias
     * @param sysAliasName
     * @return
     */
    public static String selectKeyWithFilter(String fieldName, String tableAlias, String sysAliasName) {
        if (fieldName.equalsIgnoreCase(deleteField)) {
            return null;
        }
        return String.format(SELECT_FIELD_FORMAT, tableAlias, fieldName, sysAliasName);
    }

    /**
     * where 后的删除条件
     *
     * @param table
     * @return
     */
    public static String notDeleteSql(ModuleTableRelation table,String and) {
        if (table.getIsLogicDelete() && hasLogicDelete(table)) {
            return String.format(" %s %s.%s=#{%s}",and,table.getTableAlias(), deleteField, deleteField);
        }
        return "";
    }

    /**
     * 判断是否有逻辑删除字段
     * @param table 表信息
     * @return true/false
     */
    private static boolean hasLogicDelete(ModuleTableRelation table) {
        return table.getFields().stream()
                .anyMatch(field -> field.getFieldName().equalsIgnoreCase(deleteField));
    }

    /**
     * join sql
     *
     * @param relation
     * @return
     */
    public static String joinSql(ModuleTableRelation relation) {
        String onSql = CollUtil.join(relation.getRelationFields().stream().map(field -> String.format("%s.%s=%s.%s", field.getModuleTableAlias(), field.getFieldName(),
                field.getRelationMoudleAlias(), field.getRelationFieldName())).collect(Collectors.toList()), " and ");
        String deleteSql = notDeleteSql(relation,"and");
        if (StrUtil.isNotEmpty(relation.getSearchSql())) {
            return String.format(" %s (select * from %s where %s) %s on %s %s", relation.getRelationType(), relation.getTableNameForSql(), relation.getSearchSql(), relation.getTableAlias(), onSql,deleteSql);
        } else {
            return String.format(" %s %s %s on %s %s", relation.getRelationType(), relation.getTableNameForSql(), relation.getTableAlias(), onSql,deleteSql);
        }
    }

    /**
     * 构建查询sql
     *
     * @param relation
     * @return
     */
    public static Map<String, String> selectSql(ModuleTableRelation relation) {
        Map<String, String> sqlMap = new HashMap<>();
        StringBuilder selectSql = new StringBuilder();
        if (StrUtil.isNotEmpty(relation.getSearchSql())) {
            selectSql.append(String.format("select %s from (select * from %s where %s ) %s ",
                    CollUtil.join(relation.getSelectFields(), ","), relation.getTableNameForSql(), relation.getSearchSql(), relation.getTableAlias()));
        } else {
            selectSql.append(String.format("select %s from %s %s ", CollUtil.join(relation.getSelectFields(), ","), relation.getTableNameForSql(), relation.getTableAlias()));
        }
        if (CollUtil.isNotEmpty(relation.getJoinSql())) {
            selectSql.append(CollUtil.join(relation.getJoinSql(), " "));
        }
        String deleteSql = notDeleteSql(relation,"");
        // 使用where标签，帮助移除多余and
        selectSql.append(" <where> ");
        if (StrUtil.isNotEmpty(deleteSql)) {
            selectSql.append(deleteSql);
        }
        if (relation.getIsMain()) {
            sqlMap.put(CfgActionTypeEnum.SELECT_PAGE.name(), selectSql + "</where>");
            // GET_BY_ID: id_module_table_id foreach in
            selectSql.append(String.format(" and %s ", CollUtil.join(relation.getPkFields().stream().
                                                                             map(field -> String.format("%s.%s in %s", relation.getTableAlias(), field.getFieldName(), foreachSql(field.getFieldValue() , relation.getModuleTableId()))).collect(Collectors.toList()), " and ")));
            // GET_BY_ID: parameterName in
            processParameterSql(relation, selectSql);
            sqlMap.put(CfgActionTypeEnum.GET_BY_ID.name(), selectSql + "</where>");
        } else {
            selectSql.append(String.format(" and %s ", CollUtil.join(relation.getRelationFields().stream()
                         .map(field ->{
                             // id in 查询 (忽略大小写)
                             if (LowCodeService.PK_NAME_ONE.equalsIgnoreCase(field.getRelationFieldName())) {
                                 return String.format("%s.%s in %s", relation.getTableAlias(),
                                                      field.getFieldName(),
                                                      foreachSql(field.getRelationFieldName() , relation.getRelationModuleTableTd()));
                             } else {
                                // 非id = 查询
                                return String.format("%s.%s=#{%s_%d}",
                                               relation.getTableAlias(),
                                               field.getFieldName(),
                                               field.getRelationFieldName(),
                                               relation.getRelationModuleTableTd());
                             }
                         })
                         .collect(Collectors.toList()), " and ")));
            // CHILD_SELECT_LIST: parameterName in
            processParameterSql(relation, selectSql);
            sqlMap.put(CfgActionTypeEnum.CHILD_SELECT_LIST.name(), selectSql + "</where>");
        }

        return sqlMap;
    }

    private static void processParameterSql(ModuleTableRelation relation, StringBuilder selectSql) {
        if (StringUtils.isNotEmpty(relation.getParameterName())) {
            // 当前仅支持子表的主键 的外部参数 查询
            String idColumnName = LowCodeService.PK_NAME_ONE;
            // 现在parameterType固定为 parameter [parameterType 的作用待体现]
            if (!"parameter".equals(relation.getParameterType())) {
                idColumnName = relation.getParameterType();
            }
            selectSql.append(String.format(" <if test=\"%s != null and %s.size() > 0\">", relation.getParameterName(), relation.getParameterName()));
            selectSql.append(String.format(" and %s.%s in %s", relation.getTableAlias(), idColumnName, foreachSql(
                    relation.getParameterName())));
            selectSql.append("</if> ");
        }
    }

    /**
     * 构建foreach sql
     * @param fieldName
     * @param moudleTableId
     * @return
     */
    public static String foreachSql(String fieldName,Long moudleTableId) {
        return String.format(" <foreach item=\"it\" collection=\"%s_%d\" open=\"(\" close=\") \" separator=\",\" >#{it}</foreach>", fieldName,moudleTableId);
    }

    public static String foreachSql(String fieldName) {
        return String.format(" <foreach item=\"it\" collection=\"%s\" open=\"(\" close=\") \" separator=\",\" >#{it}</foreach>", fieldName);
    }
}
