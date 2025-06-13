alter table cfg_data_format add transfer_label_field varchar2(64);
comment on column cfg_data_format.transfer_label_field is '回显字段';