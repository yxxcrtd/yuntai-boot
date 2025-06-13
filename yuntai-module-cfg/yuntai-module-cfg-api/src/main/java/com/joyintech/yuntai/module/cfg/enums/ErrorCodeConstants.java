package com.joyintech.yuntai.module.cfg.enums;

import com.joyintech.yuntai.framework.common.exception.ErrorCode;

/**
 * cfg 错误码枚举类
 * cfg 系统，使用 1_003_000_000 段
 */
public interface ErrorCodeConstants {

    // ========== 建表相关 模块 1_003_000_000 ==========
    ErrorCode TABLE_DEFINITION_NOT_EXISTS = new ErrorCode(1_003_000_001, "表定义不存在");

    ErrorCode COLUMN_DEFINITION_NOT_EXISTS = new ErrorCode(1_003_000_002, "字段定义不存在");

    ErrorCode INDEX_DEFINITION_NOT_EXISTS = new ErrorCode(1_003_000_003, "索引定义不存在");

    ErrorCode DB_SYSTEM_COLUMN_NOT_EXISTS = new ErrorCode(1_003_000_004, "数据库系统字段不存在");

    ErrorCode DB_DATA_DOMAIN_NOT_EXISTS = new ErrorCode(1_003_000_005, "数据库字段类型表(数据域)不存在");

    ErrorCode COLUMN_DEFINITION_TYPE_ERROR = new ErrorCode(1_003_000_006, "列类型错误");


    // ========== 建表相关 模块 1_003_001_000 ==========
    ErrorCode MODULE_INFO_NOT_EXISTS = new ErrorCode(1_003_001_001, "模型信息不存在");
    ErrorCode MODULE_RELATION_NOT_EXISTS = new ErrorCode(1_003_001_002, "模型关联不存在");
    ErrorCode MODULE_VIEW_NOT_EXISTS = new ErrorCode(1_003_001_003, "模型视图不存在");
    ErrorCode MODULE_API_NOT_EXISTS = new ErrorCode(1_003_001_004, "模型API不存在");
    ErrorCode MODULE_INFO_USE = new ErrorCode(1_003_001_005, "该模型信息已被使用");
    ErrorCode MODULE_INFO_CHANGE = new ErrorCode(1_003_001_006, "保存失败,模型信息已经发生变更");


    // ========== 建表相关 组件 1_003_002_000 ==========
    ErrorCode COMPONENT_ATTRIBUTE_NOT_EXISTS = new ErrorCode(1_003_002_001, "组件属性不存在");

    ErrorCode COMPONENT_GROUP_NOT_EXISTS = new ErrorCode(1_003_002_002, "组件分组不存在");

    ErrorCode COMPONENT_TABLE_NOT_EXISTS = new ErrorCode(1_003_002_003, "组件不存在");

    ErrorCode APP_INFO_NOT_EXISTS = new ErrorCode(1_003_002_004, "多应用不存在");

    // ========== 建表相关 流程 1_003_003_000 ==========
    ErrorCode PROCESS_DESIGN_NOT_EXISTS = new ErrorCode(1_003_003_001, "流程设计不存在");
    ErrorCode PROCESS_NODE_PERMISSION_NOT_EXISTS = new ErrorCode(1_003_003_002, "流程节点权限不存在");
    ErrorCode PROCESS_NODE_NOT_EXISTS = new ErrorCode(1_003_003_003, "流程设计节点不存在");
    ErrorCode PROCESS_CONDITIONS_NOT_EXISTS = new ErrorCode(1_003_003_004, "流程条件关联不存在");
    ErrorCode PROCESS_CHANGE = new ErrorCode(1_003_003_005, "保存失败,流程信息已发生变更");


    // ========== 页面配置  1_003_004_000 ==========
    ErrorCode PAGE_BUTTON_NOT_EXISTS = new ErrorCode(1_003_004_005, "页面操作按钮不存在");
    ErrorCode PAGE_FORM_CONFIG_NOT_EXISTS = new ErrorCode(1_003_004_006, "表单页配置不存在");
    ErrorCode PAGE_GROUP_NOT_EXISTS = new ErrorCode(1_003_004_007, "页面分组不存在");
    ErrorCode PAGE_INFO_NOT_EXISTS = new ErrorCode(1_003_004_008, "页面基本信息不存在");
    ErrorCode PAGE_LIST_CONDITION_NOT_EXISTS = new ErrorCode(1_003_004_009, "表单页查询条件（待定）不存在");
    ErrorCode PAGE_LIST_CONFIG_NOT_EXISTS = new ErrorCode(1_003_004_010, "列表页配置不存在");
    ErrorCode EVENT_CONFIG_NOT_EXISTS = new ErrorCode(1_003_004_011, "页面事件配置不存在");
    ErrorCode PAGE_LINKAGE_NOT_EXISTS = new ErrorCode(1_003_004_012, "页面联动配置不存在");
    ErrorCode VALIDATE_RULES_NOT_EXISTS = new ErrorCode(1_003_004_013, "页面校验规则不存在");
    ErrorCode FUNCTION_INFO_NOT_EXISTS = new ErrorCode(1_003_004_015, "开发平台功能管理不存在");
    ErrorCode DATA_FORMAT_NOT_EXISTS = new ErrorCode(1_003_004_016, "页面数据格式化不存在");
    ErrorCode DATA_CONVERSION_NOT_EXISTS = new ErrorCode(1_003_004_017, "数据转换不存在");
    ErrorCode CONDITIONAL_TABLE_NOT_EXISTS = new ErrorCode(1_003_004_018, "条件不存在");
    ErrorCode PAGE_PARAMETER_NOT_EXISTS = new ErrorCode(1_003_004_019, "页面参数不存在");
    ErrorCode PAGE_API_NOT_EXISTS = new ErrorCode(1_003_004_020, "页面api不存在");
    ErrorCode PARAMTER_LIST_NOT_EXISTS = new ErrorCode(1_003_004_021, "页面路由参数不存在");
    ErrorCode BUTTON_ACTION_NOT_EXISTS = new ErrorCode(1_003_004_022, "页面按钮动作不存在");
    ErrorCode PAGE_EXTEND_EVENT_NOT_EXISTS = new ErrorCode(1_003_004_023, "页面事件扩展配置不存在");
    ErrorCode PAGE_ATTACHMENT_INFO_NOT_EXISTS = new ErrorCode(1_003_004_024, "表单页配置-附件管理不存在");
    ErrorCode PAGE_ATTACHMENT_UPLOADFILE_NOT_EXISTS = new ErrorCode(1_003_004_025, "表单页配置-附件管理-指定上传文件不存在");
    ErrorCode DATA_SET_CONFIG_NOT_EXISTS = new ErrorCode(1_003_004_026, "数据集管理不存在");
    ErrorCode DATA_SET_CONFIG_HTTP_NOT_EXISTS = new ErrorCode(1_003_004_027, "数据集-http请求内容不存在");
    ErrorCode PAGE_INFO_ID_NOT_EXISTS = new ErrorCode(1_003_004_028, "页面基本信息id值为空");
    ErrorCode FILE_INFO_NOT_EXISTS = new ErrorCode(1_003_004_029, "上传附件不存在");
    ErrorCode PAGE_INFO_CHANGE = new ErrorCode(1_003_004_030, "保存失败,页面信息已经发生变更");

    /********************************** 1_005_005_000 *************************************/
    ErrorCode SERVICE_NOT_EXISTS = new ErrorCode(1_005_005_001, "未找到对应的操作!,请检查服务编码");
    ErrorCode SERVICE_EXECUTE_ERROR = new ErrorCode(1_005_005_003, "服务执行异常");
    ErrorCode SERVICE_EVENT_UN_PASS = new ErrorCode(1_005_005_004, "服务扩展事件执校验未通过");



    // ========== 建表相关 模版 1_003_006_000 ==========
    ErrorCode TEMPLATE_INFO_NOT_EXISTS = new ErrorCode(1_003_006_001, "模版不存在");
    ErrorCode TEMPLATE_GROUP_NOT_EXISTS = new ErrorCode(1_003_006_002, "模版分组不存在");
    ErrorCode TEMPLATE_CONTENT_NOT_EXISTS = new ErrorCode(1_003_006_003, "模版内容不存在");
    ErrorCode TEMPLATE_GROUP_NOT_DELETE = new ErrorCode(1_003_006_004, "分组下存在模板信息,删除失败!");

    // ========== 外部系统 1_003_007_000 ===============
    ErrorCode OUT_SYSTEM_TABLE_NOT_EXISTS = new ErrorCode(1_003_007_001, "外部系统关联不存在");
    ErrorCode LOG_INFO_NOT_EXISTS = new ErrorCode(1_003_007_002, "日志记录不存在");
}
