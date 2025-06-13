alter table cfg_file_info add column `IS_PUBLIC`  int(10) DEFAULT NULL COMMENT '是否对外披露';
alter table cfg_file_info add column `FILE_TITLE` VARCHAR(1000) DEFAULT NULL COMMENT '文件标题';