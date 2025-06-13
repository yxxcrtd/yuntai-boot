alter table infra_file add parent_id NUMBER(20,0);
comment on column infra_file.parent_id is '主表id';