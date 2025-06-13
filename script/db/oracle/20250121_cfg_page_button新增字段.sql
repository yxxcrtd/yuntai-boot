alter table cfg_page_button add module_table_id NUMBER(20,0);
comment on column cfg_page_button.module_table_id is '子表id';

alter table cfg_conditional_table add show_page_type_code varchar2(64);
comment on column cfg_conditional_table.show_page_type_code is '显示类型';