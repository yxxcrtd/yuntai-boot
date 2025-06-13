ALTER TABLE `infra_data_source_config`
ADD COLUMN `type_source` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '类型' AFTER `id`,
ADD COLUMN `source` tinyint NOT NULL COMMENT '0->是；1->否' AFTER `type`,
ADD COLUMN `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据源编码' AFTER `source`,
ADD COLUMN `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注' AFTER `password`;

ALTER TABLE `infra_data_source_config`
ADD COLUMN `ip` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '服务器IP' AFTER `name`;

ALTER TABLE `infra_data_source_config`
ADD COLUMN `type_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '类型名称' AFTER `type`;

ALTER TABLE `cfg_component_group`
ADD COLUMN `parent_id` bigint NOT NULL COMMENT '组件分组表ID' AFTER `num_sort`;

ALTER TABLE `lowcode-db`.`cfg_component_group`
MODIFY COLUMN `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父级ID' AFTER `num_sort`;

ALTER TABLE `lowcode-db`.`system_menu`
ADD COLUMN `page_id` bigint NOT NULL COMMENT '页面设置ID' AFTER `id`;
ALTER TABLE `lowcode-db`.`system_menu`
MODIFY COLUMN `page_id` bigint NULL COMMENT '页面设置ID' AFTER `id`;

ALTER TABLE `lowcode-db`.`cfg_function_info`
ADD COLUMN `function_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能菜单类型' AFTER `function_icon`;

ALTER TABLE `lowcode-db`.`cfg_page_info`
ADD COLUMN `menu_id` bigint NULL COMMENT '菜单ID' AFTER `id`;

ALTER TABLE `cfg_page_info`
ADD COLUMN `tree_server_id` bigint NULL COMMENT '页面模版下服务ID' AFTER `page_template`,
ADD COLUMN `tree_module_id` bigint NULL COMMENT '页面模版下数据模型' AFTER `tree_server_id`,
ADD COLUMN `sub_table` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '子表格' AFTER `tree_module_id`,
ADD COLUMN `table_module_id` bigint NULL COMMENT '子表格下数据模型' AFTER `sub_table`,
ADD COLUMN `table_server_id` bigint NULL COMMENT '子表格下服务ID' AFTER `table_module_id`,
MODIFY COLUMN `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注' AFTER `tail_slot`;

ALTER TABLE `cfg_page_list_config`
ADD COLUMN `sort` int NULL DEFAULT 0 COMMENT '字段排序' AFTER `column_min_width`;

ALTER TABLE `cfg_page_list_condition`

ADD COLUMN `sort` int NULL DEFAULT 0 COMMENT '字段排序' AFTER `column_display_component`;

ALTER TABLE `cfg_page_button`
ADD COLUMN `custom_method` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义方法' AFTER `relevance_page`,
ADD COLUMN `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注' AFTER `custom_method`;

ALTER TABLE `cfg_page_list_config`
ADD COLUMN `field_id` bigint NULL DEFAULT NULL COMMENT '字段ID' AFTER `relation_table_id`,
ADD COLUMN `table_id` bigint NULL DEFAULT NULL COMMENT '数据库表ID' AFTER `field_id`;

ALTER TABLE `cfg_page_list_config`
ADD COLUMN `column_display_component` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示组件' AFTER `column_head_slot`;

ALTER TABLE `cfg_page_group`
CHANGE COLUMN `page_group_name` `group_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组名称' AFTER `page_id`,
ADD COLUMN `group_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分组编码' AFTER `page_id`,
ADD COLUMN `group_sort` int NULL DEFAULT 0 COMMENT '分组排序' AFTER `group_name`;

ALTER TABLE `cfg_page_list_config`
ADD COLUMN `page_api_id` bigint NULL COMMENT '页面APIID' AFTER `table_id`;

ALTER TABLE `cfg_page_list_condition`
ADD COLUMN `page_api_id` bigint NULL COMMENT '页面APIID' AFTER `page_id`;

ALTER TABLE `cfg_page_button`
ADD COLUMN `page_api_id` bigint NULL COMMENT '页面APIID' AFTER `id`;

ALTER TABLE `cfg_page_extend_event`
ADD COLUMN `page_api_id` bigint NULL COMMENT '页面APIID' AFTER `page_id`;

ALTER TABLE `cfg_page_group`
ADD COLUMN `page_api_id` bigint NULL DEFAULT NULL COMMENT '页面APIID' AFTER `page_id`;

ALTER TABLE `cfg_page_info`
MODIFY COLUMN `module_id` bigint NULL COMMENT '数据模型' AFTER `page_type`,
MODIFY COLUMN `server_id` bigint NULL COMMENT '服务ID' AFTER `module_id`;

ALTER TABLE `cfg_page_parameter`
ADD COLUMN `parameter_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数编码' AFTER `page_api_id`,
ADD COLUMN `parameter_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数类型' AFTER `parameter_code`;

ALTER TABLE `cfg_page_list_config`
ADD COLUMN `api_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api编码' AFTER `page_api_id`;

ALTER TABLE `cfg_page_list_config`
ADD COLUMN `group_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组编码' AFTER `api_code`;

ALTER TABLE `cfg_page_list_config`
ADD COLUMN `child_table_id` bigint NULL DEFAULT NULL COMMENT '子表ID' AFTER `page_api_id`;

ALTER TABLE `cfg_data_conversion`
ADD COLUMN `data_regular` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据转换正则表达式' AFTER `data_content`;

ALTER TABLE `cfg_data_format`
ADD COLUMN `script` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脚本' AFTER `component_name`;

ALTER TABLE `cfg_page_info`
ADD COLUMN `page_state` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页面状态' AFTER `page_style`;

ALTER TABLE `cfg_validate_rules`
CHANGE COLUMN `rules_name` `condition` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则定义' AFTER `tool_tips`,
ADD COLUMN `is_select` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否必填' AFTER `check_name`,
ADD COLUMN `validate_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '校验类型' AFTER `tool_tips`;

ALTER TABLE `cfg_validate_rules`
CHANGE COLUMN `condition` `rules_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则定义' AFTER `validate_type`;

ALTER TABLE `cfg_page_linkage`
ADD COLUMN `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置图标' AFTER `linkage_type`;

ALTER TABLE `cfg_conditional_table`
ADD COLUMN `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '条件类型(1->初始化；2->运行时)' AFTER `relevance_id`,
ADD COLUMN `set_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设置类型' AFTER `type`,
ADD COLUMN `script` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脚本' AFTER `content`;

ALTER TABLE `cfg_event_config`
CHANGE COLUMN `event_content` `script` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事件内容' AFTER `event_name`,
ADD COLUMN `event_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事件类型' AFTER `event_name`;

ALTER TABLE `cfg_event_config`
ADD COLUMN `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事件图标' AFTER `event_type`;

ALTER TABLE `cfg_page_list_config`
ADD COLUMN `is_require` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否必填' AFTER `is_column_sort`,
ADD COLUMN `is_disabled` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否置灰' AFTER `is_require`,
ADD COLUMN `is_hidden` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否隐藏' AFTER `is_disabled`;

ALTER TABLE `cfg_page_list_condition`
ADD COLUMN `field_id` bigint NULL DEFAULT NULL COMMENT '字段ID' AFTER `page_api_id`,
ADD COLUMN `table_id` bigint NULL DEFAULT NULL COMMENT '数据库表ID' AFTER `field_id`,
ADD COLUMN `api_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api编码' AFTER `table_id`,
ADD COLUMN `group_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组编码' AFTER `api_code`,
ADD COLUMN `child_table_id` bigint NULL DEFAULT NULL COMMENT '子表ID' AFTER `group_code`;

ALTER TABLE `cfg_page_list_config`
ADD COLUMN `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典类型' AFTER `group_code`;

ALTER TABLE `cfg_page_info`
ADD COLUMN `external_js_file` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '页面外部JS' AFTER `tail_slot`;

ALTER TABLE `cfg_page_list_config`
MODIFY COLUMN `is_visible` int NULL DEFAULT NULL COMMENT '是否显示' AFTER `sort`,
MODIFY COLUMN `is_column_fixed` int NULL DEFAULT NULL COMMENT '是否固定列' AFTER `is_visible`,
MODIFY COLUMN `is_total_column` int NULL DEFAULT NULL COMMENT '是否合计列' AFTER `column_slot`,
MODIFY COLUMN `is_column_sort` int NULL DEFAULT NULL COMMENT '是否支持排序' AFTER `is_total_column`,
MODIFY COLUMN `is_require` int NULL DEFAULT NULL COMMENT '是否必填' AFTER `is_column_sort`,
MODIFY COLUMN `is_disabled` int NULL DEFAULT NULL COMMENT '是否置灰' AFTER `is_require`,
MODIFY COLUMN `is_hidden` int NULL DEFAULT NULL COMMENT '是否隐藏' AFTER `is_disabled`;

ALTER TABLE `cfg_page_list_config`
MODIFY COLUMN `is_column_fixed` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否固定列' AFTER `is_visible`;

ALTER TABLE `cfg_page_extend_event`
MODIFY COLUMN `service_id` bigint NULL DEFAULT NULL COMMENT '服务id' AFTER `page_api_id`,
ADD COLUMN `api_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api编码' AFTER `service_id`;

ALTER TABLE `cfg_page_list_config`
CHANGE COLUMN `relation_table_id` `module_table_id` bigint NULL DEFAULT NULL COMMENT '模型关联表ID' AFTER `page_id`,
ADD COLUMN `column_tag_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段标签编码' AFTER `sort`;

ALTER TABLE `cfg_page_parameter`
ADD COLUMN `template_id` bigint NULL DEFAULT NULL COMMENT '模版ID' AFTER `page_id`;

ALTER TABLE `cfg_page_button`
ADD COLUMN `model_server_id` bigint NULL DEFAULT NULL COMMENT '关联服务ID' AFTER `page_api_id`,
ADD COLUMN `server_params` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务参数' AFTER `model_server_id`;

ALTER TABLE `cfg_page_button`
CHANGE COLUMN `server_params` `server_params_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务参数' AFTER `model_server_id`;

ALTER TABLE `cfg_page_button`
ADD COLUMN `api_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api编码' AFTER `model_server_id`;

ALTER TABLE `cfg_page_list_condition`
ADD COLUMN `column_dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典类型' AFTER `column_display_component`;

ALTER TABLE `cfg_page_list_condition`
ADD COLUMN `column_display_component_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示组件名称' AFTER `column_display_component`;

ALTER TABLE `cfg_page_list_config`
ADD COLUMN `column_display_component_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示组件名称' AFTER `column_display_component`;

ALTER TABLE `cfg_page_button`
ADD COLUMN `permission_sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '按钮权限标识' AFTER `api_code`;