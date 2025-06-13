alter table cfg_page_list_config add column_head_width NUMBER(3,0);
comment on column cfg_page_list_config.column_head_width is '标签长度';

alter table cfg_page_list_config add column_tag_config clob;
comment on column cfg_page_list_config.column_tag_config is '字段标签配置';

alter table cfg_page_button add action_default_params clob;
comment on column cfg_page_button.action_default_params is '按钮默认参数';

create table log_process_data
(
    id NUMBER(20,0) not null,
    page_id NUMBER(20,0) not null,
    flow_id varchar2(64),
    flow_request_id varchar2(64),
    node_id varchar2(64),
    form_id varchar2(64),
    form_data clob,
    creator varchar2(64),
    create_time date,
    updater varchar2(64),
    update_time date,
    deleted NUMBER(1) DEFAULT 0,
    PRIMARY KEY (id)
);

comment on table log_process_data
  is '流程Log日志表';
comment on column log_process_data.id
  is '唯一标识';
comment on column log_process_data.page_id
  is '页面id';
comment on column log_process_data.flow_id
  is '流程id';
comment on column log_process_data.flow_request_id
  is '流程实例';
comment on column log_process_data.node_id
  is '流程节点id';
comment on column log_process_data.form_id
  is '数据id';
comment on column log_process_data.form_data
  is '数据明细';
comment on column log_process_data.creator
  is '创建者';
comment on column log_process_data.create_time
  is '创建时间';
comment on column log_process_data.updater
  is '更新者';
comment on column log_process_data.update_time
  is '更新时间';
comment on column log_process_data.deleted
  is '是否删除';
CREATE INDEX idx_lpd_flow_id ON log_process_data(page_id, flow_id);
