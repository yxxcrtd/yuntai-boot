-- 附件类型、上传人、上传时间、备注
alter table infra_file add attachment_type varchar2(128);
comment on column infra_file.attachment_type is '附件类型';

alter table infra_file add upload_user varchar2(64);
comment on column infra_file.upload_user is '上传人';

alter table infra_file add upload_time date;
comment on column infra_file.upload_time is '上传时间';

alter table infra_file add remark varchar2(512);
comment on column infra_file.remark is '备注';