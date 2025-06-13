alter table cfg_file_info add column `file_type_text` VARCHAR(128) DEFAULT NULL COMMENT '附件文件类型';
alter table cfg_file_info add column `ori_attachment_id` VARCHAR(128) DEFAULT NULL COMMENT '文件id';