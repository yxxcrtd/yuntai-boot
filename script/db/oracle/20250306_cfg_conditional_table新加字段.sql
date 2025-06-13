alter table cfg_page_linkage drop column fill_value;
alter table cfg_page_linkage drop column column_display_component;

alter table cfg_conditional_table add fill_value varchar2(64);
comment on column cfg_conditional_table.fill_value is '赋值为';
alter table cfg_conditional_table add column_display_component varchar2(64);
comment on column cfg_conditional_table.column_display_component is '切换为';