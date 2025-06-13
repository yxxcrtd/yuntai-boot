alter table cfg_module_field add value_type number(1) default 0;
comment on column cfg_module_field.value_type is '单选 0 多选 1';

alter table cfg_module_field add text_sql varchar2(3000);
comment on column cfg_module_field.value_type is '回显sql,列表行内数据或表单数据会被传入,可以根据此写sql,单选返回一条,多选,返回多条,代码中会进行合并处理,返回列SHOW_VALUE,SHOW_TEXT';