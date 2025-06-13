alter table cfg_page_attachment_info add is_allow_multiple varchar2(64);
comment on column cfg_page_attachment_info.is_allow_multiple is '是否允许选择多个文件';
alter table cfg_page_attachment_info add max_size NUMBER(8,2);
comment on column cfg_page_attachment_info.max_size is '最大文件限制';
alter table cfg_page_attachment_info add max_size_unit varchar2(64);
comment on column cfg_page_attachment_info.max_size_unit is '最大文件限制单位';
alter table cfg_page_attachment_info add upload_tips varchar2(128);
comment on column cfg_page_attachment_info.upload_tips is '上传提示语';

alter table cfg_page_attachment_uploadfile add flow_node_id varchar2(128);
comment on column cfg_page_attachment_uploadfile.flow_node_id is '流程节点Id';
alter table cfg_page_attachment_uploadfile add valid_rule varchar2(128);
comment on column cfg_page_attachment_uploadfile.valid_rule is '文件名校验规则（正则）';