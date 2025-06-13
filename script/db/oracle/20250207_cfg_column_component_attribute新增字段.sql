alter table cfg_column_component_attribute add linkage_id INTEGER;
comment on column cfg_column_component_attribute.linkage_id is '页面联动配置id';
-- 修改成默认为空值
alter table cfg_column_component_attribute modify (column_id null);