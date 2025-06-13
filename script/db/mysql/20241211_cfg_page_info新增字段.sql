ALTER TABLE `cfg_page_info`ADD COLUMN `is_support_attachment` varchar(64) 
CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '支持附件' AFTER `external_js_file`;

ALTER TABLE `cfg_page_info`ADD COLUMN `column_span` varchar(64) 
CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表单排版' AFTER `is_support_attachment`;

ALTER TABLE `cfg_page_info`ADD COLUMN `label_width` TINYINT COMMENT '标签长度' AFTER `column_span`;

ALTER TABLE `cfg_page_info`ADD COLUMN `label_position` varchar(64) 
CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '标签位置' AFTER `label_width`;