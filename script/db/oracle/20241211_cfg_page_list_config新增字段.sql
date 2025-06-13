alter table cfg_page_list_config add def_value varchar2(64);
comment on column cfg_page_list_config.def_value is '默认值';

alter table cfg_page_list_config add column_span NUMBER(3,0);
comment on column cfg_page_list_config.column_span is '占据列数';