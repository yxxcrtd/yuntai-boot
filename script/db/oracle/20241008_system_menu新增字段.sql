alter table system_menu add module_type varchar2(1) default 0;
comment on column system_menu.module_type is '菜单所属模块，0：系统后台，1：云台';
