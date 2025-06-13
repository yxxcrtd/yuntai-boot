alter table cfg_file_info add file_type_text VARCHAR2(128);
comment on column cfg_file_info.file_type_text is '附件文件类型';
alter table cfg_file_info add ori_attachment_id VARCHAR2(128);
comment on column cfg_file_info.ori_attachment_id is '文件id';