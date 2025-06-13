alter table cfg_table_definition add column `table_type` varchar(32) DEFAULT NULL COMMENT '表类型';
alter table cfg_table_definition add column `table_sql` text DEFAULT NULL COMMENT '表sql';

update cfg_table_definition set table_type='biz_table' where is_sys = '0';
update cfg_table_definition set table_type='sys_table' where is_sys = '1';
