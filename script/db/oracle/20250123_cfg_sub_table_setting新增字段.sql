alter table cfg_sub_table_setting add table_sort varchar2(32);
comment on column cfg_sub_table_setting.table_sort is '显示行号';

alter table cfg_sub_table_setting add table_begin_index varchar2(32);
comment on column cfg_sub_table_setting.table_begin_index is '每页行号默认起始';

alter table cfg_sub_table_setting add table_stripes varchar2(32);
comment on column cfg_sub_table_setting.table_stripes is '斑马纹';

alter table cfg_sub_table_setting add is_show_check_box varchar2(32);
comment on column cfg_sub_table_setting.is_show_check_box is '是否显示复选框';

alter table cfg_sub_table_setting add table_fixed_action varchar2(32);
comment on column cfg_sub_table_setting.table_fixed_action is '固定操作列';

alter table cfg_sub_table_setting add table_action_postion varchar2(32);
comment on column cfg_sub_table_setting.table_action_postion is '操作列位置';

alter table cfg_sub_table_setting add is_page_list varchar2(32);
comment on column cfg_sub_table_setting.is_page_list is '是否支持分页';

alter table cfg_sub_table_setting add table_def_page_size varchar2(64);
comment on column cfg_sub_table_setting.table_def_page_size is '默认分页大小';