alter table cfg_sub_table_setting add def_table_field_name VARCHAR2(120);
comment on column cfg_sub_table_setting.def_table_field_name is '自定义表字段名';