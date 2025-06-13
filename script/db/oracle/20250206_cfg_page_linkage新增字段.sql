alter table cfg_page_linkage add fill_value varchar2(64);
comment on column cfg_page_linkage.fill_value is '赋值为';

alter table cfg_page_linkage add column_display_component varchar2(64);
comment on column cfg_page_linkage.column_display_component is '切换为';