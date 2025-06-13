package com.joyintech.yuntai.module.cfg.enums;



/**
 * 模块用的常量
 *
 * @author abator 2024/9/27
 */
public class CfgConstants {
    public static final String TABLE_NAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9_]{1,32}$";
    public static final String YES = "Y";
    public static final String NO = "N";

    /** 生成sql类型 **/
    public static final String ACTION_TYPE_INSERT = "INSERT";
    public static final String ACTION_TYPE_UPDATE = "UPDATE";
    public static final String ACTION_TYPE_DELETE = "DELETE";
    public static final String ACTION_TYPE_SELECT_LIST = "SELECT_LIST";
    public static final String ACTION_TYPE_SELECT_BY_ID = "SELECT_BY_ID";
    public static final String ACTION_TYPE_CHILD_SELECT = "CHILD_SELECT";
    public static final String ACTION_TYPE_CHILD_INSERT = "CHILD_INSERT";
    public static final String ACTION_TYPE_CHILD_UPDATE = "CHILD_UPDATE";
    public static final String ACTION_TYPE_CHILD_DELETE = "CHILD_DELETE";

    /** 接口类型类型 **/
    public static final String API_TYPE_CREATE = "_CREATE";
    public static final String API_TYPE_UPDATE = "_UPDATE";
    public static final String API_TYPE_DELETE = "_DELETE";
    public static final String API_TYPE_PAGE = "_PAGE";
    public static final String API_TYPE_LIST = "_LIST";
    public static final String API_TYPE_GET = "_GET";

    /** 关联类型 **/
    public static final String RELATION_TYPE_JOIN = "1";
    public static final String RELATION_TYPE_CHILD = "2";
}
