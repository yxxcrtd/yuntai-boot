alter table cfg_sub_table_setting add table_title varchar2(128);
comment on column cfg_sub_table_setting.table_title is '子表标题';

alter table cfg_sub_table_setting add default_data varchar2(128);
comment on column cfg_sub_table_setting.default_data is '默认数据';