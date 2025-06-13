alter table cfg_table_definition add table_type varchar2(32);
comment on column cfg_table_definition.table_type is '表类型';

alter table cfg_table_definition add table_sql clob;
comment on column cfg_table_definition.table_sql is '表sql';

update cfg_table_definition set table_type='biz_table' where is_sys = '0';
update cfg_table_definition set table_type='sys_table' where is_sys = '1';

commit;