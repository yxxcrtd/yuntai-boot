alter table cfg_column_component_attribute add column `linkage_id` bigint COMMENT '页面联动配置id';
-- 修改成默认为空值
alter table cfg_column_component_attribute modify column column_id bigint DEFAULT null COMMENT '列主键';