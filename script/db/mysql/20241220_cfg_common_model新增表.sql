CREATE TABLE `cfg_common_model` (
    `id` bigint NOT NULL COMMENT '主键ID',
    `model_code` varchar(64) DEFAULT NULL COMMENT '模型编码',
    `model_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型名称',
    `remark` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='公共模型表';


INSERT INTO `lowcode-db`.infra_codegen_table
(id, data_source_config_id, scene, table_name, table_comment, remark, module_name, business_name, class_name, class_comment, author, template_type, front_type, parent_menu_id, master_table_id, sub_join_column_id, sub_join_many, tree_parent_column_id, tree_name_column_id, creator, create_time, updater, update_time, deleted)
VALUES(1868841230344361229, 28, 1, 'cfg_common_model', '公共模型', NULL, 'cfg', 'commonmodel', 'CommonModel', '公共模型', '兆尹云台', 1, 10, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2024-12-17 10:10:45', '1', '2024-12-17 10:10:45', 0);

INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361229, 'id', 'BIGINT', '主键ID', 0, 1, 1, 'Long', 'id', '', '21028', 0, 1, 0, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361229, 'model_code', 'VARCHAR', '模型编码', 0, 0, 2, 'String', 'modelCode', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361229, 'model_name', 'VARCHAR', '模型名称', 0, 0, 3, 'String', 'modelName', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361229, 'remark', 'VARCHAR', '备注', 1, 0, 4, 'String', 'remark', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);

INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361229, 'creator', 'VARCHAR', '创建者', 1, 0, 5, 'String', 'creator', '', NULL, 0, 0, 0, '=', 0, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361229, 'create_time', 'TIMESTAMP', '创建时间', 0, 0, 6, 'LocalDateTime', 'createTime', '', NULL, 0, 0, 1, 'BETWEEN', 1, 'datetime', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361229, 'updater', 'VARCHAR', '更新者', 1, 0, 7, 'String', 'updater', '', NULL, 0, 0, 0, '=', 0, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361229, 'update_time', 'TIMESTAMP', '更新时间', 0, 0, 8, 'LocalDateTime', 'updateTime', '', NULL, 0, 0, 0, 'BETWEEN', 0, 'datetime', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361229, 'deleted', 'BIT', '是否删除', 0, 0, 9, 'Boolean', 'deleted', '', NULL, 0, 0, 0, '=', 0, 'radio', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);



INSERT INTO `lowcode-db`.infra_codegen_table
(id, data_source_config_id, scene, table_name, table_comment, remark, module_name, business_name, class_name, class_comment, author, template_type, front_type, parent_menu_id, master_table_id, sub_join_column_id, sub_join_many, tree_parent_column_id, tree_name_column_id, creator, create_time, updater, update_time, deleted)
VALUES(1868841230344361223, 28, 1, 'cfg_common_var', '公共变量', NULL, 'cfg', 'commonvar', 'CommonVar', '公共变量', '兆尹云台', 1, 10, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2024-12-17 10:10:45', '1', '2024-12-17 10:10:45', 0);

INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361223, 'id', 'BIGINT', '主键ID', 0, 1, 1, 'Long', 'id', '', '21028', 0, 1, 0, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361223, 'field_name', 'VARCHAR', '字段名称', 0, 0, 2, 'String', 'fieldName', '', NULL, 1, 1, 1, 'like', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361223, 'field_describe', 'VARCHAR', '字段描述', 0, 0, 3, 'String', 'fieldDescribe', '', NULL, 1, 1, 1, 'like', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361223, 'cache_type', 'VARCHAR', '缓存类型', 1, 0, 4, 'String', 'cacheType', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361223, 'type', 'VARCHAR', '前端/后端类型', 1, 0, 5, 'String', 'type', '', NULL, 1, 1, 1, '=', 1, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361223, 'creator', 'VARCHAR', '创建者', 1, 0, 6, 'String', 'creator', '', NULL, 0, 0, 0, '=', 0, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361223, 'create_time', 'TIMESTAMP', '创建时间', 0, 0, 7, 'LocalDateTime', 'createTime', '', NULL, 0, 0, 1, 'BETWEEN', 1, 'datetime', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361223, 'updater', 'VARCHAR', '更新者', 1, 0, 8, 'String', 'updater', '', NULL, 0, 0, 0, '=', 0, 'input', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361223, 'update_time', 'TIMESTAMP', '更新时间', 0, 0, 9, 'LocalDateTime', 'updateTime', '', NULL, 0, 0, 0, 'BETWEEN', 0, 'datetime', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);
INSERT INTO `lowcode-db`.infra_codegen_column
(id, table_id, column_name, data_type, column_comment, nullable, primary_key, ordinal_position, java_type, java_field, dict_type, example, create_operation, update_operation, list_operation, list_operation_condition, list_operation_result, html_type, creator, create_time, updater, update_time, deleted)
VALUES(NULL, 1868841230344361223, 'deleted', 'BIT', '是否删除', 0, 0, 10, 'Boolean', 'deleted', '', NULL, 0, 0, 0, '=', 0, 'radio', '1', '2024-09-20 15:17:33', '1', '2024-09-23 09:55:53', 0);


drop table cfg_common_var;
CREATE TABLE `cfg_common_var` (
                                  `id` bigint NOT NULL COMMENT '主键ID',
                                  `field_name` varchar(64) DEFAULT NULL COMMENT '字段名称',
                                  `field_describe` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段描述',
                                  `cache_type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '缓存类型',
                                  `type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '前后端类型',
                                  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
                                  `create_time` datetime NOT NULL COMMENT '创建时间',
                                  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
                                  `update_time` datetime NOT NULL COMMENT '更新时间',
                                  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除''',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='公共变量表';
