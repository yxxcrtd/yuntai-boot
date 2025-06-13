create table infra_data_set_config
(
    id NUMBER(20,0) not null,
    type varchar2(64),
    name varchar2(64),
    code varchar2(64),
    source_code NUMBER(20,0),
    remark varchar2(64),
    json_data varchar2(4000),
    sql_data varchar2(4000),
    creator varchar2(64),
    create_time date NOT NULL,
    updater varchar2(64),
    update_time date NOT NULL,
    deleted NUMBER(1) DEFAULT 0,
    PRIMARY KEY (id)
);
comment on table infra_data_set_config
  is '数据集管理';
comment on column infra_data_set_config.id
  is '唯一标识';
comment on column infra_data_set_config.type
  is '数据集类型';
comment on column infra_data_set_config.name
  is '数据集名称';
comment on column infra_data_set_config.code
  is '数据集编码';
comment on column infra_data_set_config.source_code
  is '数据源';
comment on column infra_data_set_config.remark
  is '描述';
comment on column infra_data_set_config.json_data
  is 'JSON数据';
comment on column infra_data_set_config.sql_data
  is 'SQL语句';
comment on column infra_data_set_config.creator
  is '创建者';
comment on column infra_data_set_config.create_time
  is '创建时间';
comment on column infra_data_set_config.updater
  is '更新者';
comment on column infra_data_set_config.update_time
  is '更新时间';
comment on column infra_data_set_config.deleted
  is '是否删除';
CREATE INDEX idx_idsc_type ON infra_data_set_config(type);