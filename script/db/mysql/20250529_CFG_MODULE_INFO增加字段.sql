alter table CFG_MODULE_FIELD add IS_BACK_DATA  int(11) comment '是否回推';

alter table CFG_PAGE_INFO add PAGE_VERSION  VARCHAR(128) comment '页面版本';

alter table CFG_MODULE_INFO add FLOW_LOG_PARAMS text comment '流程记录回推参数';
