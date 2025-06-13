
-- 创建普通索引
CREATE INDEX idx_vr_cofid ON cfg_validate_rules(page_config_id);
CREATE INDEX idx_pl_cofid ON cfg_page_linkage(page_config_id);
CREATE INDEX idx_ec_cofid ON cfg_event_config(page_config_id);
CREATE INDEX idx_dc_lcofid ON cfg_data_conversion(list_config_id);
CREATE INDEX idx_df_lcofid ON cfg_data_format(list_config_id);
CREATE INDEX idx_cca_colid ON cfg_column_component_attribute(column_id);
