alter table cfg_module_table add table_alias varchar2(64);
comment on column cfg_module_table.table_alias is '表别名';