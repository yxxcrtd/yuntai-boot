DROP TABLE IF EXISTS cfg_file_info;
CREATE TABLE cfg_file_info(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `page_id` BIGINT NOT NULL  COMMENT '列表页ID' ,
	`flow_id` VARCHAR(64) COMMENT '流程id',
	`flow_node_id` VARCHAR(64) COMMENT '流程节点id',
	`file_type` VARCHAR(64) COMMENT '附件文件类型',
	`file_name` VARCHAR(64) COMMENT '附件文件名称',
	`upload_user_id` VARCHAR(64) COMMENT '上传人',
	`upload_date` DATETIME COMMENT '上传时间',
	`is_require` BIT(1) COMMENT '是否必传',
	`file_id` VARCHAR(64) COMMENT '文件上传统一ID',
	`file_url` VARCHAR(512) COMMENT '文件地址',
	`file_size` DECIMAL(8,2) COMMENT '文件大小',
    `attachment_type` VARCHAR(64) COMMENT '附件类型',
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '上传附件';
CREATE INDEX idx_cfi_page_id ON cfg_file_info(page_id);