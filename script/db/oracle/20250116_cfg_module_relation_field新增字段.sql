alter table cfg_module_relation_field add relation_table_key varchar2(64);
comment on column cfg_module_relation_field.relation_table_key is '关联表tableKey';