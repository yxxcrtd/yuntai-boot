alter table cfg_page_attachment_uploadfile add column `file_type_id` VARCHAR(128) DEFAULT NULL COMMENT '文件名称ID';
alter table cfg_page_attachment_uploadfile add column `file_type_text` VARCHAR(128) DEFAULT NULL COMMENT '文件名称';
update cfg_page_attachment_uploadfile set file_type_text = param_name where param_name is not null;
alter table cfg_page_attachment_uploadfile drop column param_name;