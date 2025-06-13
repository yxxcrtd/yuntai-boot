DROP TABLE IF EXISTS cfg_page_attachment_info;
CREATE TABLE cfg_page_attachment_info(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `page_id` BIGINT NOT NULL  COMMENT '列表页ID' ,
    `attachment_type` VARCHAR(64) COMMENT '附件模式',
    `is_require` VARCHAR(64) COMMENT '是否必传',
    `is_allow_download` VARCHAR(64) COMMENT '是否允许下载',
    `is_support_attachment` VARCHAR(64) COMMENT '是否允许预览',
    `allow_file_suffix_code` VARCHAR(64) COMMENT '允许上传的文件类型',
    `allow_size_code` VARCHAR(64) COMMENT '上传文件大小范围',
	`file_source_dict` VARCHAR(64) COMMENT '文件类型（字典）',
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '表单页配置-附件管理';
CREATE INDEX idx_cpai_attachment_type ON cfg_page_attachment_info(attachment_type);

DROP TABLE IF EXISTS cfg_page_attachment_uploadfile;
CREATE TABLE cfg_page_attachment_uploadfile(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `attachment_id` BIGINT NOT NULL  COMMENT '附件管理ID' ,
    `param_name` VARCHAR(64) COMMENT '文件名称',	
	`is_require` bit(1) DEFAULT b'0' COMMENT '是否必传',
	`is_valid_file_name` bit(1) DEFAULT b'0' COMMENT '是否校验文件名',
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '表单页配置-附件管理-指定上传文件';
CREATE INDEX idx_cpau_attachment_id ON cfg_page_attachment_uploadfile(attachment_id);