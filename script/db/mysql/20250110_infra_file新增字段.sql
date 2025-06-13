-- 附件类型、上传人、上传时间、备注
alter table infra_file add column `attachment_type` varchar(128) DEFAULT NULL COMMENT '附件类型';
alter table infra_file add column `upload_user` varchar(64) DEFAULT NULL COMMENT '上传人';
alter table infra_file add column `upload_time` datetime DEFAULT NULL COMMENT '上传时间';
alter table infra_file add column `remark` varchar(512) DEFAULT NULL COMMENT '备注';