alter table cfg_page_attachment_info add column `is_allow_preview` varchar(64) DEFAULT NULL COMMENT '是否允许预览';
update cfg_page_attachment_info set is_allow_preview = is_support_attachment where is_support_attachment is not null;
alter table cfg_page_attachment_info drop column `is_support_attachment`;