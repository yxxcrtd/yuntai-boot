alter table cfg_module_info add column `old_id` bigint DEFAULT NULL COMMENT '复制原表id';
alter table cfg_module_table add column `old_id` bigint DEFAULT NULL COMMENT '复制原表id';
alter table cfg_module_relation_field add column `old_id` bigint DEFAULT NULL COMMENT '复制原表id';
alter table cfg_module_field add column `old_id` bigint DEFAULT NULL COMMENT '复制原表id';
alter table cfg_module_sql add column `old_id` bigint DEFAULT NULL COMMENT '复制原表id';
alter table cfg_module_api add column `old_id` bigint DEFAULT NULL COMMENT '复制原表id';
alter table cfg_module_api_param add column `old_id` bigint DEFAULT NULL COMMENT '复制原表id';

alter table cfg_module_field add column `text_table_id` bigint DEFAULT NULL COMMENT '回显表';
alter table cfg_module_field add column `text_column_id` bigint DEFAULT NULL COMMENT '回显字段';