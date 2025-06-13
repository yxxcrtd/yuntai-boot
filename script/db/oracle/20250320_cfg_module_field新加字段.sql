ALTER TABLE cfg_module_field RENAME COLUMN text_table_id TO text_table_id_old;
ALTER TABLE cfg_module_field ADD text_table_id VARCHAR2(64);
UPDATE cfg_module_field SET text_table_id = TO_CHAR(text_table_id_old) where text_table_id_old is not null;
ALTER TABLE cfg_module_field DROP COLUMN text_table_id_old;
comment on column cfg_module_field.text_table_id is '回显表';

ALTER TABLE cfg_module_field RENAME COLUMN text_column_id TO text_column_id_old;
ALTER TABLE cfg_module_field ADD text_column_id VARCHAR2(64);
UPDATE cfg_module_field SET text_column_id = TO_CHAR(text_column_id_old) where text_column_id_old is not null;
ALTER TABLE cfg_module_field DROP COLUMN text_column_id_old;
comment on column cfg_module_field.text_column_id is '回显字段';

alter table cfg_module_field add text_table_key VARCHAR2(64);
comment on column cfg_module_field.text_table_key is '回显表外键';