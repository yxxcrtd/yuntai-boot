
alter table cfg_module_field add column `value_type` tinyint(1) default 0 comment '单选 0 多选 1' ;
alter table cfg_module_field add column `text_sql` varchar(3000) default 0 comment '回显sql,列表行内数据或表单数据会被传入,可以根据此写sql,单选返回一条,多选,返回多条,代码中会进行合并处理,返回列SHOW_VALUE,SHOW_TEXT' ;
