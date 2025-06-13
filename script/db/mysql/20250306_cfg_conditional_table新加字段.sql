alter table cfg_page_linkage drop column `fill_value`;
alter table cfg_page_linkage drop column `column_display_component`;

alter table cfg_conditional_table add column `fill_value` varchar(64) DEFAULT NULL COMMENT '赋值为';
alter table cfg_conditional_table add column `column_display_component` varchar(64) DEFAULT NULL COMMENT '切换为';