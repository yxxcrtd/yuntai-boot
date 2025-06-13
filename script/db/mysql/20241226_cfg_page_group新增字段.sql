alter table cfg_page_group add column `parent_id` varchar(64) DEFAULT NULL COMMENT '父分组id' after is_hidden;
alter table cfg_page_group add column `group_head_tips` varchar(256) DEFAULT NULL COMMENT '分组头部提示语'  after parent_id;
alter table cfg_page_group add column `group_foot_tips` varchar(256) DEFAULT NULL COMMENT '分组尾部提示语'  after group_head_tips;
alter table cfg_page_group add column `group_display` varchar(64) DEFAULT NULL COMMENT '分组展示方式'  after group_foot_tips;
alter table cfg_page_group add column `group_title` varchar(64) DEFAULT NULL COMMENT '标题'  after group_display;