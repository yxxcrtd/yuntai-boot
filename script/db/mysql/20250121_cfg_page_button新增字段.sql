alter table cfg_page_button add column `module_table_id` bigint DEFAULT NULL COMMENT '子表id';
alter table cfg_conditional_table add column `show_page_type_code` varchar(64) DEFAULT NULL COMMENT '显示类型';
