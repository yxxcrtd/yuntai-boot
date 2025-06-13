alter table cfg_page_attachment_info add is_allow_preview varchar2(64);
comment on column cfg_page_attachment_info.is_allow_preview is '是否允许预览';
update cfg_page_attachment_info set is_allow_preview = is_support_attachment where is_support_attachment is not null;
alter table cfg_page_attachment_info drop column is_support_attachment;