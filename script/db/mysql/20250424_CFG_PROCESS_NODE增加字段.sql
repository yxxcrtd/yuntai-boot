alter table CFG_PROCESS_NODE add IS_PUSH_BACK_MODEL_DATA  NUMBER(10,0) comment '是否回推模型数据';

alter table CFG_PROCESS_NODE add IS_PUSH_BACK  NUMBER(10,0) comment '是否回推';

alter table CFG_PROCESS_NODE add PUSH_BACK_URL  varchar(128) comment '指定回推地址';

alter table CFG_MODULE_INFO add IS_PUSH_BACK_MODEL_DATA  NUMBER(10,0) comment '是否回推模型数据';

alter table CFG_MODULE_INFO add PUSH_BACK_URL  varchar(128) comment '指定回推地址';