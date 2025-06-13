create table cfg_file_info
(
    id NUMBER(20,0) not null,
    page_id NUMBER(20,0) NOT NULL,
	flow_id varchar2(64),
	flow_node_id varchar2(64),
	file_type varchar2(64),
	file_name varchar2(64),
	upload_user_id varchar2(64),
	upload_date date,
	is_require NUMBER(1),
	file_id varchar2(64),
	file_url varchar2(512),
	file_size NUMBER(8,2),
    attachment_type varchar2(64),
    creator varchar2(64),
    create_time date,
    updater varchar2(64),
    update_time date,
    deleted NUMBER(1) DEFAULT 0,
    PRIMARY KEY (id)
);
comment on table cfg_file_info
  is '上传附件';
comment on column cfg_file_info.id
  is '唯一标识';
comment on column cfg_file_info.page_id
  is '列表页ID';
comment on column cfg_file_info.flow_id
  is '流程id';
comment on column cfg_file_info.flow_node_id
  is '流程节点id';
comment on column cfg_file_info.file_type
  is '附件文件类型';  
comment on column cfg_file_info.file_name
  is '附件文件名称';
comment on column cfg_file_info.upload_user_id
  is '上传人'; 
comment on column cfg_file_info.upload_date
  is '上传时间';
comment on column cfg_file_info.is_require
  is '是否必传';
comment on column cfg_file_info.file_id
  is '文件上传统一ID';  
comment on column cfg_file_info.file_url
  is '文件地址';
comment on column cfg_file_info.file_size
  is '文件大小';
comment on column cfg_file_info.attachment_type
  is '附件类型';
comment on column cfg_file_info.creator
  is '创建者';
comment on column cfg_file_info.create_time
  is '创建时间';
comment on column cfg_file_info.updater
  is '更新者';
comment on column cfg_file_info.update_time
  is '更新时间';
comment on column cfg_file_info.deleted
  is '是否删除';
CREATE INDEX idx_cfi_page_id ON cfg_file_info(page_id);