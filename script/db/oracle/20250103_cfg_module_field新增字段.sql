alter table cfg_module_field add compute_sql varchar2(512);
comment on column cfg_module_field.compute_sql is '计算字段公式';