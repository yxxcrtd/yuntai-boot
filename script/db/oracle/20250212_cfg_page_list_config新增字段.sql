alter table cfg_page_list_config add column_field_alias varchar2(64);
comment on column cfg_page_list_config.column_field_alias is '字段别名';