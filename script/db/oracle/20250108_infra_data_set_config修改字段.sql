alter table infra_data_set_config rename column json_data to json_data_back;
alter table infra_data_set_config add json_data clob;
comment on column infra_data_set_config.json_data is 'JSON数据';
update infra_data_set_config a set a.json_data=a.json_data_back;
alter table infra_data_set_config drop column json_data_back;

alter table log_process_data rename column sql_data to sql_data_back;
alter table log_process_data add sql_data clob;
comment on column log_process_data.sql_data is 'SQL语句';
update log_process_data a set a.sql_data=a.sql_data_back;
alter table log_process_data drop column sql_data_back;