alter table cfg_sub_table_setting add is_hidden_action VARCHAR2(32);
comment on column cfg_sub_table_setting.is_hidden_action is '是否隐藏操作列';