alter table cfg_process_design add flow_id varchar2(64);
comment on column cfg_process_design.flow_id is '流程ID';

alter table infra_data_set_config add call_method varchar2(32);
comment on column infra_data_set_config.call_method is '调用方式';

alter table infra_data_set_config add url varchar2(128);
comment on column infra_data_set_config.url is '请求地址';

alter table infra_data_set_config add request_methods varchar2(32);
comment on column infra_data_set_config.request_methods is '请求地址';

create table infra_data_set_config_http
(
    id BIGINT NOT NULL  COMMENT '主键ID' ,
    data_id BIGINT NOT NULL  COMMENT '列表页ID' ,
    type VARCHAR(64) COMMENT '类型',
    key_code VARCHAR(128) COMMENT '请求头/参数名称',
    value VARCHAR(128) COMMENT '内容/参数值',
    creator VARCHAR(64)   COMMENT '创建者' ,
    create_time DATETIME NOT NULL  COMMENT '创建时间' ,
    updater VARCHAR(64)   COMMENT '更新者' ,
    update_time DATETIME NOT NULL  COMMENT '更新时间' ,
    deleted BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
);
comment on table infra_data_set_config_http
  is '数据集-http请求内容';
comment on column infra_data_set_config_http.id
  is '唯一标识';
comment on column infra_data_set_config_http.data_id
  is '列表页ID';
comment on column infra_data_set_config_http.type
  is '类型';
comment on column infra_data_set_config_http.key_code
  is '请求头/参数名称';
comment on column infra_data_set_config_http.value
  is '内容/参数值';
comment on column infra_data_set_config_http.creator
  is '创建者';
comment on column infra_data_set_config_http.create_time
  is '创建时间';
comment on column infra_data_set_config_http.updater
  is '更新者';
comment on column infra_data_set_config_http.update_time
  is '更新时间';
comment on column infra_data_set_config_http.deleted
  is '是否删除';
CREATE INDEX idx_idsch_data_id ON infra_data_set_config_http(data_id);

