DROP TABLE IF EXISTS infra_data_set_config;
CREATE TABLE infra_data_set_config(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `type` VARCHAR(64) NOT NULL  COMMENT '数据集类型' ,
    `name` VARCHAR(64) COMMENT '数据集名称',
    `code` VARCHAR(64) COMMENT '数据集编码',
    `source_code` BIGINT COMMENT '数据源',
    `remark` VARCHAR(64) COMMENT '描述',
    `json_data` varchar(4000) COMMENT 'JSON数据',
    `sql_data` varchar(4000) COMMENT 'SQL语句',
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '数据集管理';
CREATE INDEX idx_idsc_type ON infra_data_set_config(type);