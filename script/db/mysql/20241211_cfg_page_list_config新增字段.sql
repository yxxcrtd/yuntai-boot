ALTER TABLE `cfg_page_list_config`ADD COLUMN `def_value` varchar(64) 
CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '默认值' AFTER `column_display_component_name`;

ALTER TABLE `cfg_page_list_config`ADD COLUMN `column_span` TINYINT COMMENT '占据列数' AFTER `def_value`;