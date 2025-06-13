package com.joyintech.yuntai.module.cfg.utils;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;

/**
 * 需要记录sql的表
 */
public class TableListUtils {
    public static final List<String> PACKAGE_LIST = CollectionUtil.newArrayList(
            "com.joyintech.yuntai.module.cfg"
    );
    public static final List<String> TABLE_LIST = CollectionUtil.newArrayList(
            "CFG_APP_INFO",
            "CFG_BUTTON_ACTION",
            "CFG_COLUMN_COMPONENT_ATTRIBUTE",
            "CFG_COLUMN_DEFINITION",
            "CFG_COMMON_MODEL",
            "CFG_COMMON_VAR",
            "CFG_COMPONENT_ATTRIBUTE",
            "CFG_COMPONENT_GROUP",
            "CFG_COMPONENT_TABLE",
            "CFG_CONDITIONAL_TABLE",
            "CFG_DATA_CONVERSION",
            "CFG_DATA_FORMAT",
            "CFG_DB_DATA_DOMAIN",
            "CFG_DB_SYSTEM_COLUMN",
            "CFG_DB_TYPE_CONFIG",
            "CFG_EVENT_CONFIG",
            "CFG_FILE_INFO",
            "CFG_FUNCTION_INFO",
            "CFG_INDEX_DEFINITION",
            "CFG_MODULE_API",
            "CFG_MODULE_API_PARAM",
            "CFG_MODULE_FIELD",
            "CFG_MODULE_INFO",
            "CFG_MODULE_RELATION_FIELD",
            "CFG_MODULE_SQL",
            "CFG_MODULE_TABLE",
            "CFG_OUT_SYSTEM_TABLE",
            "CFG_PAGE_API",
            "CFG_PAGE_ATTACHMENT_INFO",
            "CFG_PAGE_ATTACHMENT_UPLOADFILE",
            "CFG_PAGE_BUTTON",
            "CFG_PAGE_EXTEND_EVENT",
            "CFG_PAGE_GROUP",
            "CFG_PAGE_INFO",
            "CFG_PAGE_LINKAGE",
            "CFG_PAGE_LIST_CONDITION",
            "CFG_PAGE_LIST_CONFIG",
            "CFG_PAGE_PARAMETER",
            "CFG_PARAMTER_LIST",
            "CFG_PROCESS_DESIGN",
            "CFG_PROCESS_NODE",
            "CFG_PROCESS_NODE_CONDITION",
            "CFG_PROCESS_NODE_EVENT",
            "CFG_PROCESS_NODE_FORM_PROPERTY",
            "CFG_PROCESS_NODE_PERMISSIONS",
            "CFG_SUB_TABLE_SETTING",
            "CFG_TABLE_DEFINITION",
            "CFG_TEMPLATE_CONTENT",
            "CFG_TEMPLATE_GROUP",
            "CFG_TEMPLATE_INFO",
            "CFG_VALIDATE_RULES"
    );

    /**
     * 判断是否是需要记录的表
     * @param sql
     * @return
     */
    public static boolean containsTable(String sql) {
        boolean contains = false;
        for (String table : TABLE_LIST) {
            if (sql.contains(table)) {
                contains = true;
                break;
            }
            if (sql.contains(table.toLowerCase())) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    /**
     * 判断是否是需要记录的包
     * @param id
     * @return
     */
    public static boolean containsPackage(String id) {
        boolean contains = false;
        for (String pake : PACKAGE_LIST) {
            if (id.contains(pake)) {
                contains = true;
                break;
            }
        }
        return contains;
    }
}
