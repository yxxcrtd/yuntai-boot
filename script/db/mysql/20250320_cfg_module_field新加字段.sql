ALTER TABLE cfg_module_field MODIFY COLUMN `text_table_id` VARCHAR(64) DEFAULT NULL COMMENT '回显表';
ALTER TABLE cfg_module_field MODIFY COLUMN `text_column_id` VARCHAR(64) DEFAULT NULL COMMENT '回显字段';
alter table cfg_module_field add column `text_table_key` VARCHAR(64) DEFAULT NULL COMMENT '回显表外键';