alter table cfg_page_attachment_info add column `filter_file_type_value` VARCHAR(512) DEFAULT NULL COMMENT '上传文件过滤类型配置';

alter table cfg_page_list_config add column `is_show_app` int DEFAULT NULL COMMENT '移动端展示';

alter table cfg_process_node_form_property add column `field_alias` VARCHAR(255) DEFAULT NULL COMMENT '移动端展示';