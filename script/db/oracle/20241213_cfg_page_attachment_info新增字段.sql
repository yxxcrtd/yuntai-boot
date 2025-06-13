create table cfg_page_attachment_info
(
    id NUMBER(20,0) not null,
    page_id NUMBER(20,0) NOT NULL,
    attachment_type varchar2(64),
    is_require varchar2(64),
    is_allow_download varchar2(64),
    is_support_attachment varchar2(64),
    allow_file_suffix_code varchar2(64),
    allow_size_code varchar2(64),
    file_source_dict varchar2(64),
    creator varchar2(64),
    create_time date,
    updater varchar2(64),
    update_time date,
    deleted NUMBER(1) DEFAULT 0,
    PRIMARY KEY (id)
);
comment on table cfg_page_attachment_info
  is '表单页配置-附件管理';
comment on column cfg_page_attachment_info.id
  is '唯一标识';
comment on column cfg_page_attachment_info.page_id
  is '列表页ID';
comment on column cfg_page_attachment_info.attachment_type
  is '附件模式';
comment on column cfg_page_attachment_info.is_require
  is '是否必传';
comment on column cfg_page_attachment_info.is_allow_download
  is '是否允许下载';
comment on column cfg_page_attachment_info.is_support_attachment
  is '是否允许预览';
comment on column cfg_page_attachment_info.allow_file_suffix_code
  is '允许上传的文件类型';
comment on column cfg_page_attachment_info.allow_size_code
  is '上传文件大小范围';
comment on column cfg_page_attachment_info.file_source_dict
  is '文件类型（字典）';
comment on column cfg_page_attachment_info.creator
  is '创建者';
comment on column cfg_page_attachment_info.create_time
  is '创建时间';
comment on column cfg_page_attachment_info.updater
  is '更新者';
comment on column cfg_page_attachment_info.update_time
  is '更新时间';
comment on column cfg_page_attachment_info.deleted
  is '是否删除';
CREATE INDEX idx_cpai_attachment_type ON cfg_page_attachment_info(attachment_type);

create table cfg_page_attachment_uploadfile
(
    id NUMBER(20,0) not null,
    attachment_id NUMBER(20,0) NOT NULL,
    param_name varchar2(64),
    is_require NUMBER(1) DEFAULT 0,
    is_valid_file_name NUMBER(1) DEFAULT 0,
    creator varchar2(64),
    create_time date,
    updater varchar2(64),
    update_time date,
    deleted NUMBER(1) DEFAULT 0,
    PRIMARY KEY (id)
);
comment on table cfg_page_attachment_uploadfile
  is '表单页配置-附件管理-指定上传文件';
comment on column cfg_page_attachment_uploadfile.id
  is '唯一标识';
comment on column cfg_page_attachment_uploadfile.attachment_id
  is '附件管理ID';
comment on column cfg_page_attachment_uploadfile.param_name
  is '文件名称';
comment on column cfg_page_attachment_uploadfile.is_require
  is '是否必传';
comment on column cfg_page_attachment_uploadfile.is_valid_file_name
  is '是否校验文件名';
comment on column cfg_page_attachment_uploadfile.creator
  is '创建者';
comment on column cfg_page_attachment_uploadfile.create_time
  is '创建时间';
comment on column cfg_page_attachment_uploadfile.updater
  is '更新者';
comment on column cfg_page_attachment_uploadfile.update_time
  is '更新时间';
comment on column cfg_page_attachment_uploadfile.deleted
  is '是否删除';
CREATE INDEX idx_cpau_attachment_id ON cfg_page_attachment_uploadfile(attachment_id);
