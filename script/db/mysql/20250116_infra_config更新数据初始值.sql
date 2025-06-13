-- dataSourceBase
update infra_config set category='Cfg_Database' where config_key in (
'table_name_prefix',
'table_name_rule',
'field_name_prefix',
'field_name_rule',
'index_name_prefix',
'index_name_rule'
)