alter table cfg_sub_table_setting add is_allow_add VARCHAR2(120);
comment on column cfg_sub_table_setting.is_allow_add is '是否允许新增';