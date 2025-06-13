DROP TABLE IF EXISTS cfg_table_definition;
CREATE TABLE cfg_table_definition(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `datasource_id` BIGINT NOT NULL  COMMENT '数据源id' ,
    `table_name` VARCHAR(32) NOT NULL  COMMENT '表名' ,
    `table_comment` VARCHAR(64) NOT NULL  COMMENT '表注释' ,
    `remark` VARCHAR(1000)   COMMENT '描述' ,
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号' ,
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '表定义表';


CREATE INDEX idx_ctd_table_name ON cfg_table_definition(table_name);
CREATE INDEX idx_ctd_dsid_table_name ON cfg_table_definition(datasource_id,table_name);

DROP TABLE IF EXISTS cfg_column_definition;
CREATE TABLE cfg_column_definition(
  `id` BIGINT NOT NULL  COMMENT '主键ID' ,
  `table_id` BIGINT NOT NULL  COMMENT '定义表主键' ,
  `column_name` VARCHAR(32) NOT NULL  COMMENT '列名' ,
  `column_comment` VARCHAR(64)   COMMENT '列注释' ,
  `data_domain_id` BIGINT   COMMENT '数据域主键' ,
  `db_type` VARCHAR(32)   COMMENT '数据库类型（MySQL）' ,
  `java_type` VARCHAR(64)   COMMENT '对应java的字段类型' ,
  `data_type` VARCHAR(64)   COMMENT '字段数据库类型' ,
  `column_length` INT   COMMENT '长度' ,
  `column_scale` INT   COMMENT '小数位数' ,
  `default_value` VARCHAR(64)   COMMENT '默认值' ,
  `is_primary_key` BIT(1)   COMMENT '是否主键' ,
  `is_not_null` BIT(1)   COMMENT '非空' ,
  `is_auto_increment` BIT(1)   COMMENT '自增' ,
  `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号' ,
  `creator` VARCHAR(64)   COMMENT '创建者' ,
  `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
  `updater` VARCHAR(64)   COMMENT '更新者' ,
  `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
  `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
  `is_sys` VARCHAR(1)   COMMENT '是否系统字段' ,
  PRIMARY KEY (id)
)  COMMENT = '字段定义表';


CREATE INDEX idx_ccd_table_id_col_name ON cfg_column_definition(table_id,column_name);

DROP TABLE IF EXISTS cfg_index_definition;
CREATE TABLE cfg_index_definition(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `datasource_id` BIGINT NOT NULL  COMMENT '数据源id' ,
    `table_id` BIGINT NOT NULL  COMMENT '定义表主键' ,
    `index_name` VARCHAR(32) NOT NULL  COMMENT '索引名称' ,
    `index_columns` VARCHAR(255) NOT NULL  COMMENT '索引列' ,
    `is_unique_key` VARCHAR(1)   COMMENT '是否唯一索引' ,
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号' ,
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '索引定义表';


CREATE INDEX idx_cid_table_id_index_name ON cfg_index_definition(table_id,index_name);
CREATE INDEX idx_cid_dsid_index_name ON cfg_index_definition(datasource_id,index_name);

DROP TABLE IF EXISTS cfg_db_system_column;
CREATE TABLE cfg_db_system_column(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `column_name` VARCHAR(32) NOT NULL  COMMENT '字段名' ,
    `column_comment` VARCHAR(64) NOT NULL  COMMENT '字段注释' ,
    `column_position` INT   COMMENT '位置' ,
    `data_domain_id` BIGINT   COMMENT '数据域主键' ,
    `db_type` VARCHAR(32) NOT NULL  COMMENT '数据库类型（MySQL）' ,
    `column_length` INT   COMMENT '长度' ,
    `column_scale` INT   COMMENT '小数位数' ,
    `default_value` VARCHAR(64)   COMMENT '默认值' ,
    `is_primary_key` VARCHAR(1)   COMMENT '是否主键' ,
    `is_not_null` VARCHAR(1)   COMMENT '非空' ,
    `is_auto_increment` VARCHAR(1)   COMMENT '自增' ,
    `remark` VARCHAR(1000)   COMMENT '备注' ,
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号;框架底层需要' ,
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '数据库系统字段表';

DROP TABLE IF EXISTS cfg_db_data_domain;
CREATE TABLE cfg_db_data_domain(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `data_type` VARCHAR(32) NOT NULL  COMMENT '数据类型名' ,
    `db_type` VARCHAR(32) NOT NULL  COMMENT '数据库类型（MySQL）;设计：其他数据库根据MySQL类型代码中做映射' ,
    `data_length` INT   COMMENT '长度' ,
    `data_scale` INT   COMMENT '小数位数' ,
    `remark` VARCHAR(1000)   COMMENT '备注' ,
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '数据库字段类型表(数据域)';

DROP TABLE IF EXISTS cfg_module_info;
CREATE TABLE cfg_module_info(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `menu_id` VARCHAR(64) NOT NULL  COMMENT '菜单Id' ,
    `module_name` VARCHAR(64) NOT NULL  COMMENT '模型名称' ,
    `module_code` VARCHAR(32) NOT NULL  COMMENT '模型编码' ,
    `main_table_id` BIGINT(64)   COMMENT '主表id' ,
    `module_type` VARCHAR(32) NOT NULL  COMMENT '模型类型' ,
    `module_sql` VARCHAR(4000)   COMMENT '模型sql' ,
    `module_bean` VARCHAR(1000)   COMMENT '模型JavaBean' ,
    `module_method` VARCHAR(64)   COMMENT '模型方法' ,
    `remark` VARCHAR(1000)   COMMENT '备注' ,
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号;框架底层需要' ,
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    `readonly` BIT(1)   COMMENT '是否自读' ,
    `api_url` VARCHAR(255)   COMMENT '接口地址' ,
    `api_type` VARCHAR(32)   COMMENT '接口类型：get/post' ,
    PRIMARY KEY (id)
)  COMMENT = '模型信息表';

DROP TABLE IF EXISTS cfg_module_sql;
CREATE TABLE cfg_module_sql(
   `id` BIGINT NOT NULL  COMMENT '主键ID' ,
   `module_id` BIGINT NOT NULL  COMMENT '模型Id' ,
   `table_id` BIGINT(64)   COMMENT '表id' ,
   `table_name` VARCHAR(64)   COMMENT '表名称,新增/修改/删除时使用' ,
   `action_type` VARCHAR(32) NOT NULL  COMMENT '操作类型,新增/修改/删除/查询' ,
   `action_sql` TEXT NOT NULL  COMMENT 'sql内容' ,
   `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号;框架底层需要' ,
   `creator` VARCHAR(64)   COMMENT '创建者' ,
   `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
   `updater` VARCHAR(64)   COMMENT '更新者' ,
   `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
   `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
   PRIMARY KEY (id)
)  COMMENT = '模型新建后生成的SQL';


DROP TABLE IF EXISTS cfg_module_relation_field;
CREATE TABLE cfg_module_relation_field(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `module_id` BIGINT(64)   COMMENT '模型id' ,
    `module_table_id` BIGINT NOT NULL  COMMENT 'cfg_table_id表id' ,
    `relation_module_table_id` BIGINT(64)   COMMENT 'cfg_table_id表id' ,
    `relation_direction` INT(64)   COMMENT '记录是子表字段关联主表还是主表字段关联子表,0-子表字段关联主表,1-主表字段关联子表' ,
    `realtion_field_id` BIGINT(64) NOT NULL  COMMENT '关联表字段' ,
    `field_id` BIGINT(64) NOT NULL  COMMENT '表关联字段' ,
    `tenant_id` BIGINT  DEFAULT 0 COMMENT '租户编号;框架底层需要' ,
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '模型关联字段表';

DROP TABLE IF EXISTS cfg_module_field;
CREATE TABLE cfg_module_field(
 `id` BIGINT NOT NULL  COMMENT '主键ID' ,
 `module_id` BIGINT   COMMENT '模型id' ,
 `module_table_id` BIGINT   COMMENT 'cfg_module_table表id' ,
 `field_id` VARCHAR(64)   COMMENT '字段id' ,
 `field_alias_name` VARCHAR(64)   COMMENT '字段别名' ,
 `remark` VARCHAR(1000)   COMMENT '备注' ,
 `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号;框架底层需要' ,
 `creator` VARCHAR(64)   COMMENT '创建者' ,
 `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
 `updater` VARCHAR(64)   COMMENT '更新者' ,
 `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
 `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
 PRIMARY KEY (id)
)  COMMENT = '模型字段表';

DROP TABLE IF EXISTS cfg_module_api;
CREATE TABLE cfg_module_api(
   `id` BIGINT NOT NULL  COMMENT '主键ID' ,
   `service_name` VARCHAR(64) NOT NULL  COMMENT '服务名称' ,
   `service_code` VARCHAR(64) NOT NULL  COMMENT '服务编码' ,
   `module_id` BIGINT NOT NULL  COMMENT '模型id' ,
   `service_type` VARCHAR(64)   COMMENT '服务方式' ,
   `param` TEXT   COMMENT '参数' ,
   `remark` VARCHAR(100)   COMMENT '备注' ,
   `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号;框架底层需要' ,
   `creator` VARCHAR(64)   COMMENT '创建者' ,
   `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
   `updater` VARCHAR(64)   COMMENT '更新者' ,
   `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
   `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
   PRIMARY KEY (id)
)  COMMENT = '模型API表';

DROP TABLE IF EXISTS cfg_module_api_param;
CREATE TABLE cfg_module_api_param(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `api_id` BIGINT   COMMENT 'api的id' ,
    `module_table_id` BIGINT(64)   COMMENT 'cfg_module_id 的表id' ,
    `field_id` BIGINT(64)   COMMENT '表字段id' ,
    `parent_module_table_id` VARCHAR(64)   COMMENT '父级ID,对应module_table_id' ,
    `param_type` VARCHAR(64)   COMMENT '字段类型' ,
    `valid_rule` VARCHAR(512)   COMMENT '校验规则' ,
    `default_value` VARCHAR(64)   COMMENT '默认值' ,
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号;框架底层需要' ,
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '模型Api参数表';


DROP TABLE IF EXISTS cfg_page_info;
CREATE TABLE cfg_page_info(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `page_name` VARCHAR(64) NOT NULL  COMMENT '页面名称' ,
    `page_code` VARCHAR(64) NOT NULL  COMMENT '页面编码' ,
    `page_type` VARCHAR(64) NOT NULL  COMMENT '页面类型;1:列表 2-表单' ,
    `module_id` BIGINT NOT NULL  COMMENT '数据模型' ,
    `page_style` VARCHAR(64)   COMMENT '页面风格' ,
    `default_query` VARCHAR(64)   COMMENT '默认查询' ,
    `page_template` VARCHAR(64)   COMMENT '页面模板' ,
    `parent_page` VARCHAR(64)   COMMENT '父页面套壳' ,
    `inner_slot` VARCHAR(64)   COMMENT '内部插槽' ,
    `header_slot` VARCHAR(64)   COMMENT '页头插槽' ,
    `middle_slot` VARCHAR(64)   COMMENT '中间插槽' ,
    `tail_slot` VARCHAR(64)   COMMENT '尾部插槽' ,
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    `remark` VARCHAR(1000)   COMMENT '备注' ,
    PRIMARY KEY (id)
)  COMMENT = '页面基本信息';

DROP TABLE IF EXISTS cfg_page_list_config;
CREATE TABLE cfg_page_list_config(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `page_id` BIGINT NOT NULL  COMMENT '页面ID' ,
    `column_name` VARCHAR(64)   COMMENT '列字段' ,
    `column_comment` VARCHAR(64)   COMMENT '列名称' ,
    `column_name_alias` VARCHAR(64)   COMMENT '列字段别名' ,
    `column_group` VARCHAR(64)   COMMENT '所属分组' ,
    `column_alignment` VARCHAR(64)   COMMENT '对齐方式' ,
    `column_fixed_width` VARCHAR(64)   COMMENT '固定宽度' ,
    `column_min_width` VARCHAR(64)   COMMENT '最小宽度' ,
    `is_visible` VARCHAR(64)   COMMENT '是否显示' ,
    `is_column_fixed` VARCHAR(64)   COMMENT '是否固定列' ,
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    `remark` VARCHAR(1000)   COMMENT '备注' ,
    PRIMARY KEY (id)
)  COMMENT = '列表页配置';

DROP TABLE IF EXISTS cfg_page_form_config;
CREATE TABLE cfg_page_form_config(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `page_id` BIGINT NOT NULL  COMMENT '页面ID' ,
    `column_name` VARCHAR(64)   COMMENT '字段名' ,
    `column_title` VARCHAR(64)   COMMENT '标题' ,
    `column_alignment` VARCHAR(64)   COMMENT '所属模型' ,
    `column_component` VARCHAR(64)   COMMENT '显示组件' ,
    `isRequired` VARCHAR(64)   COMMENT '是否必填' ,
    `column_placeholder` VARCHAR(64)   COMMENT '占位符' ,
    `column_tips` VARCHAR(64)   COMMENT 'tips' ,
    `column_width` VARCHAR(64)   COMMENT '宽度' ,
    `column_slot` VARCHAR(64)   COMMENT '插槽' ,
    `component_config` TEXT   COMMENT '组件配置;暂定，若实际编码时需要可设计为独立表' ,
    `check_rule_config` TEXT   COMMENT '校验规则;暂定，若实际编码时需要可设计为独立表' ,
    `linked_config` TEXT   COMMENT '联动配置;暂定，若实际编码时需要可设计为独立表' ,
    `event_config` TEXT   COMMENT '事件配置;暂定，若实际编码时需要可设计为独立表' ,
    `creator` VARCHAR(64)   COMMENT '创建者' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新者' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    `remark` VARCHAR(1000)   COMMENT '备注' ,
    PRIMARY KEY (id)
)  COMMENT = '表单页配置';

DROP TABLE IF EXISTS cfg_page_list_condition;
CREATE TABLE cfg_page_list_condition(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `page_id` BIGINT NOT NULL  COMMENT '页面ID' ,
    PRIMARY KEY (id)
)  COMMENT = '表单页查询条件（待定）';

DROP TABLE IF EXISTS cfg_app_info;
CREATE TABLE cfg_app_info(
    `id` BIGINT NOT NULL  COMMENT '主键ID' ,
    `app_name` VARCHAR(64) NOT NULL  COMMENT '应用名称' ,
    `app_code` VARCHAR(64) NOT NULL  COMMENT '应用编码' ,
    `app_address` VARCHAR(128) NOT NULL  COMMENT '应用地址' ,
    `container` VARCHAR(64)   COMMENT '应用容器' ,
    `status` BIT(1) NOT NULL DEFAULT b'0' COMMENT '状态(0->开启1>停用)' ,
    `remark` VARCHAR(200)   COMMENT '备注' ,
    `creator` VARCHAR(64)   COMMENT '创建人' ,
    `create_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updater` VARCHAR(64)   COMMENT '更新人' ,
    `update_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除' ,
    PRIMARY KEY (id)
)  COMMENT = '多应用表';

