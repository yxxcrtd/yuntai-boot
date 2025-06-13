alter table cfg_module_info add old_id NUMBER(20,0);
comment on column cfg_module_info.old_id is '复制原表id';

alter table cfg_module_table add old_id NUMBER(20,0);
comment on column cfg_module_table.old_id is '复制原表id';

alter table cfg_module_relation_field add old_id NUMBER(20,0);
comment on column cfg_module_relation_field.old_id is '复制原表id';

alter table cfg_module_field add old_id NUMBER(20,0);
comment on column cfg_module_field.old_id is '复制原表id';

alter table cfg_module_sql add old_id NUMBER(20,0);
comment on column cfg_module_sql.old_id is '复制原表id';

alter table cfg_module_api add old_id NUMBER(20,0);
comment on column cfg_module_api.old_id is '复制原表id';

alter table cfg_module_api_param add old_id NUMBER(20,0);
comment on column cfg_module_api_param.old_id is '复制原表id';

alter table cfg_module_field add text_table_id NUMBER(20,0);
comment on column cfg_module_field.text_table_id is '回显表';

alter table cfg_module_field add text_column_id NUMBER(20,0);
comment on column cfg_module_field.text_column_id is '回显字段';
