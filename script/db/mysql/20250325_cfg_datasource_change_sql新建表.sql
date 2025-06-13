DROP TABLE IF EXISTS cfg_datasource_change_sql;
CREATE TABLE cfg_datasource_change_sql(
    `id` BIGINT NOT NULL COMMENT '主键ID' ,
    `data_source_type` BIGINT COMMENT '数据库类型' ,
    `table_id` BIGINT COMMENT '表id' ,
    `table_sql` VARCHAR(2000) COMMENT '表结构变更sql',
    `creator` VARCHAR(64) COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `updater` VARCHAR(64) COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '数据库设计变更表';
CREATE INDEX idx_cdcs_table_id ON cfg_datasource_change_sql(table_id);


DROP TABLE IF EXISTS cfg_sys_version;
CREATE TABLE cfg_sys_version(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `version` VARCHAR(32) COMMENT '版本号' ,
    `update_content` VARCHAR(2000) COMMENT '更新内容' ,
    `result` VARCHAR(32) COMMENT '更新结果',
    `reson` VARCHAR(2000) COMMENT '失败原因',
    `file_url` VARCHAR(128) COMMENT '脚本源文件地址',
    `start_date` DATETIME COMMENT '脚本开始日期',
    `end_date` DATETIME COMMENT '脚本结束日期',    
    `creator` VARCHAR(64) COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `updater` VARCHAR(64) COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '系统版本管理表';
CREATE INDEX idx_csv_version ON cfg_sys_version(version);
