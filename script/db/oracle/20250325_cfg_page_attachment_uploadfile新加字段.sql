alter table cfg_page_attachment_uploadfile add file_type_id VARCHAR2(128);
comment on column cfg_page_attachment_uploadfile.file_type_id is '文件名称ID';
alter table cfg_page_attachment_uploadfile add file_type_text VARCHAR2(128);
comment on column cfg_page_attachment_uploadfile.file_type_text is '文件名称';
update cfg_page_attachment_uploadfile set file_type_text = param_name where param_name is not null;
alter table cfg_page_attachment_uploadfile drop column param_name;
commit;