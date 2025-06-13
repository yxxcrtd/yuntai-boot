alter table cfg_button_action add column `page_type` varchar(32) DEFAULT NULL COMMENT '关联页面类型';
alter table cfg_button_action add column `relevance_url` varchar(2000) DEFAULT NULL COMMENT '跳转地址';