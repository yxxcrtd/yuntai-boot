
alter table cfg_page_group add column `group_tips` varchar(64) DEFAULT NULL COMMENT '提示文本"'  after group_title;
alter table cfg_page_group add column `group_link_address` varchar(64) DEFAULT NULL COMMENT '链接地址'  after group_tips;
alter table cfg_page_group add column `group_link_name` varchar(64) DEFAULT NULL COMMENT '链接名称'  after group_link_address;
alter table cfg_page_group add column `group_link_type` varchar(64) DEFAULT NULL COMMENT '链接名称'  after group_link_name;

alter table  cfg_validate_rules add column `range_type` varchar(64)  DEFAULT NULL COMMENT '区间配置类型' after rules_name;


INSERT INTO `lowcode-db`.infra_codegen_table
(id, data_source_config_id, scene, table_name, table_comment, remark, module_name, business_name, class_name, class_comment, author, template_type, front_type, parent_menu_id, master_table_id, sub_join_column_id, sub_join_many, tree_parent_column_id, tree_name_column_id, creator, create_time, updater, update_time, deleted)
VALUES(1868841230346661223, 28, 1, 'cfg_sub_table_setting', '子表设置', NULL, 'cfg', 'SubTableSetting', 'SubTableSetting', '子表设置', '兆尹云台', 1, 10, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2024-12-17 10:10:45', '1', '2024-12-17 10:10:45', 0);

delete from infra_codegen_column where table_id = '1868841230346661223';

INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'id', 'BIGINT', '主键ID', 0, 1, 1, 'Long', 'id', '', '21028', 0, 1, 0, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'edit_mode', 'VARCHAR', '编辑模式', 0, 0, 2, 'String', 'editMode', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'is_required', 'VARCHAR', '是否必填', 0, 0, 3, 'String', 'isRequired', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'is_show_index', 'VARCHAR', '是否显示序号', 1, 0, 4, 'String', 'isShowIndex', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'is_show_total', 'VARCHAR', '是否显示合计', 1, 0, 5, 'String', 'isShowTotal', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);

INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'page_id', 'VARCHAR', '页面设置ID', 0, 0, 6, 'String', 'pageId', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'page_api_id', 'VARCHAR', '页面apiID', 0, 0, 7, 'String', 'pageApiId', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'table_id', 'VARCHAR', '表格id', 0, 0, 8, 'String', 'tableId', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);

INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'creator', 'VARCHAR', '创建者', 1, 0, 9, 'String', 'creator', '', NULL, 0, 0, 0, '=', 0, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'create_time', 'TIMESTAMP', '创建时间', 0, 0, 10, 'LocalDateTime', 'createTime', '', NULL, 0, 0, 1, 'BETWEEN', 1, 'datetime', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'updater', 'VARCHAR', '更新者', 1, 0, 11, 'String', 'updater', '', NULL, 0, 0, 0, '=', 0, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'update_time', 'TIMESTAMP', '更新时间', 0, 0, 12, 'LocalDateTime', 'updateTime', '', NULL, 0, 0, 0, 'BETWEEN', 0, 'datetime', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230346661223, 'deleted', 'BIT', '是否删除', 0, 0, 13, 'Boolean', 'deleted', '', NULL, 0, 0, 0, '=', 0, 'radio', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);

drop table cfg_sub_table_setting;
CREATE TABLE `cfg_sub_table_setting` (
                                         `id` bigint NOT NULL COMMENT '主键ID',
                                         `edit_mode` varchar(64) DEFAULT NULL COMMENT '编辑模式',
                                         `is_required` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否必填',
                                         `is_show_index` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否显示序号',
                                         `is_show_total` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否显示合计',
                                         `page_id` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '页面设置ID',
                                         `page_api_id` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '页面apiID',
                                         `table_id` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '表格id',
                                         `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
                                         `create_time` datetime NOT NULL COMMENT '创建时间',
                                         `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
                                         `update_time` datetime NOT NULL COMMENT '更新时间',
                                         `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='子表设置表';