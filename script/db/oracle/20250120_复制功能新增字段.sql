alter table cfg_page_info add old_id NUMBER(20,0);
comment on column cfg_page_info.old_id is '复制原表id';

alter table cfg_button_action add old_id NUMBER(20,0);
comment on column cfg_button_action.old_id is '复制原表id';

alter table cfg_column_component_attribute add old_id NUMBER(20,0);
comment on column cfg_column_component_attribute.old_id is '复制原表id';

alter table cfg_conditional_table add old_id NUMBER(20,0);
comment on column cfg_conditional_table.old_id is '复制原表id';

alter table cfg_data_conversion add old_id NUMBER(20,0);
comment on column cfg_data_conversion.old_id is '复制原表id';

alter table cfg_data_format add old_id NUMBER(20,0);
comment on column cfg_data_format.old_id is '复制原表id';

alter table cfg_event_config add old_id NUMBER(20,0);
comment on column cfg_event_config.old_id is '复制原表id';

alter table cfg_page_api add old_id NUMBER(20,0);
comment on column cfg_page_api.old_id is '复制原表id';

alter table cfg_page_attachment_info add old_id NUMBER(20,0);
comment on column cfg_page_attachment_info.old_id is '复制原表id';

alter table cfg_page_attachment_uploadfile add old_id NUMBER(20,0);
comment on column cfg_page_attachment_uploadfile.old_id is '复制原表id';

alter table cfg_page_button add old_id NUMBER(20,0);
comment on column cfg_page_button.old_id is '复制原表id';

alter table cfg_page_extend_event add old_id NUMBER(20,0);
comment on column cfg_page_extend_event.old_id is '复制原表id';

alter table cfg_page_group add old_id NUMBER(20,0);
comment on column cfg_page_group.old_id is '复制原表id';

alter table cfg_page_linkage add old_id NUMBER(20,0);
comment on column cfg_page_linkage.old_id is '复制原表id';

alter table cfg_page_list_condition add old_id NUMBER(20,0);
comment on column cfg_page_list_condition.old_id is '复制原表id';

alter table cfg_page_list_config add old_id NUMBER(20,0);
comment on column cfg_page_list_config.old_id is '复制原表id';

alter table cfg_page_parameter add old_id NUMBER(20,0);
comment on column cfg_page_parameter.old_id is '复制原表id';

alter table cfg_paramter_list add old_id NUMBER(20,0);
comment on column cfg_paramter_list.old_id is '复制原表id';

alter table cfg_sub_table_setting add old_id NUMBER(20,0);
comment on column cfg_sub_table_setting.old_id is '复制原表id';

alter table cfg_validate_rules add old_id NUMBER(20,0);
comment on column cfg_validate_rules.old_id is '复制原表id';

alter table system_menu add old_id NUMBER(20,0);
comment on column system_menu.old_id is '复制原表id';
