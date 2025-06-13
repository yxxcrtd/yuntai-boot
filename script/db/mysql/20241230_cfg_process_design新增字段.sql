alter table cfg_process_design add column `flow_id` varchar(64) DEFAULT NULL COMMENT '流程ID';

alter table infra_data_set_config add column `call_method` varchar(32) DEFAULT NULL COMMENT '调用方式';
alter table infra_data_set_config add column `url` varchar(128) DEFAULT NULL COMMENT '请求地址';
alter table infra_data_set_config add column `request_methods` varchar(32) DEFAULT NULL COMMENT '请求地址';

DROP TABLE IF EXISTS infra_data_set_config_http;
CREATE TABLE infra_data_set_config_http(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `data_id` BIGINT NOT NULL  COMMENT '列表页ID' ,
    `type` VARCHAR(64) COMMENT '类型',
    `key_code` VARCHAR(128) COMMENT '请求头/参数名称',
	`value` VARCHAR(128) COMMENT '内容/参数值',
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '数据集-http请求内容';
CREATE INDEX idx_idsch_data_id ON infra_data_set_config_http(data_id);

