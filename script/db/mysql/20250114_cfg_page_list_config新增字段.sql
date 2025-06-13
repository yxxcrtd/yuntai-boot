alter table cfg_page_list_config add column `column_head_width` int DEFAULT NULL COMMENT '标签长度';
alter table cfg_page_list_config add column `column_tag_config` text DEFAULT NULL COMMENT '字段标签配置';

alter table cfg_page_button add column `action_default_params` text DEFAULT NULL COMMENT '按钮默认参数';

CREATE TABLE `log_process_data` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `page_id` bigint NOT NULL COMMENT '页面id',
  `flow_id` varchar(64) DEFAULT NULL COMMENT '流程id',
  `flow_request_id` varchar(64) DEFAULT NULL COMMENT '流程实例',
  `node_id` varchar(64) DEFAULT NULL COMMENT '流程节点id',
  `form_id` varchar(64) DEFAULT NULL COMMENT '数据id',
  `form_data` text DEFAULT NULL COMMENT '数据明细',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) COMMENT='流程Log日志表';
CREATE INDEX idx_lpd_flow_id ON log_process_data(page_id, flow_id);