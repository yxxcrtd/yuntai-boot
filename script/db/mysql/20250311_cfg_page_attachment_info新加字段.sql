alter table cfg_page_attachment_info add column `is_allow_multiple` varchar(64) DEFAULT NULL COMMENT '是否允许选择多个文件';
alter table cfg_page_attachment_info add column `max_size` DECIMAL(8,2) DEFAULT NULL COMMENT '最大文件限制';
alter table cfg_page_attachment_info add column `max_size_unit` varchar(64) DEFAULT NULL COMMENT '最大文件限制单位';
alter table cfg_page_attachment_info add column `upload_tips` varchar(128) DEFAULT NULL COMMENT '上传提示语';

alter table cfg_page_attachment_uploadfile add column `flow_node_id` varchar(128) DEFAULT NULL COMMENT '流程节点Id';
alter table cfg_page_attachment_uploadfile add column `valid_rule` varchar(128) DEFAULT NULL COMMENT '文件名校验规则（正则）';