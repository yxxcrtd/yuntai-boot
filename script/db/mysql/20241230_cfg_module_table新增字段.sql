alter table cfg_module_table add column `parameter_name` varchar(64) DEFAULT NULL COMMENT '参数名称';
alter table cfg_module_table add column `parameter_type` varchar(64) DEFAULT NULL COMMENT '参数类型';
alter table cfg_module_table rename column `where_sql` to `search_sql`;