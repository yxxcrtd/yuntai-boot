alter table system_menu add column `module_type` tinyint(1) default 0 comment '菜单所属模块，0：系统后台，1：云台' after `type`;
