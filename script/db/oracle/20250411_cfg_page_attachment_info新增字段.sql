alter table cfg_page_attachment_info add filter_file_type_value VARCHAR2(512);
comment on column cfg_page_attachment_info.filter_file_type_value is '上传文件过滤类型配置';

alter table cfg_page_list_config add is_show_app NUMBER(10,0);
comment on column cfg_page_list_config.is_show_app is '移动端展示';

alter table cfg_process_node_form_property add field_alias VARCHAR2(255);
comment on column cfg_process_node_form_property.field_alias is '字段别名';