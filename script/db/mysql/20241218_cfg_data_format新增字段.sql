ALTER TABLE cfg_data_format ADD transfer_label_field varchar(64) NULL COMMENT '回显字段';
ALTER TABLE cfg_data_format CHANGE transfer_label_field transfer_label_field varchar(64) NULL COMMENT '回显字段' AFTER script;



