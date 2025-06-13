alter table cfg_sub_table_setting add column `table_sort` varchar(32) DEFAULT NULL COMMENT '显示行号';
alter table cfg_sub_table_setting add column `table_begin_index` varchar(32) DEFAULT NULL COMMENT '每页行号默认起始';
alter table cfg_sub_table_setting add column `table_stripes` varchar(32) DEFAULT NULL COMMENT '斑马纹';
alter table cfg_sub_table_setting add column `is_show_check_box` varchar(32) DEFAULT NULL COMMENT '是否显示复选框';
alter table cfg_sub_table_setting add column `table_fixed_action` varchar(32) DEFAULT NULL COMMENT '固定操作列';
alter table cfg_sub_table_setting add column `table_action_postion` varchar(32) DEFAULT NULL COMMENT '操作列位置';
alter table cfg_sub_table_setting add column `is_page_list` varchar(32) DEFAULT NULL COMMENT '是否支持分页';
alter table cfg_sub_table_setting add column `table_def_page_size` varchar(64) DEFAULT NULL COMMENT '默认分页大小';