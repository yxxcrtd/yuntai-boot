alter table cfg_module_table add parameter_name varchar2(64);
comment on column cfg_module_table.parameter_name is '参数名称';

alter table cfg_module_table add parameter_type varchar2(64);
comment on column cfg_module_table.parameter_type is '参数类型';

alter table cfg_module_table rename column where_sql to search_sql;