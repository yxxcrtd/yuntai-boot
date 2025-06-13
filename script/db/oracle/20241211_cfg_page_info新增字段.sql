alter table cfg_page_info add is_support_attachment varchar2(64);
comment on column cfg_page_info.is_support_attachment is '支持附件';

alter table cfg_page_info add column_span varchar2(64);
comment on column cfg_page_info.column_span is '表单排版';

alter table cfg_page_info add label_width varchar2(64);
comment on column cfg_page_info.label_width is '标签长度';

alter table cfg_page_info add column_span NUMBER(3,0);
comment on column cfg_page_info.column_span is '表单排版';

alter table cfg_page_info add label_position varchar2(64);
comment on column cfg_page_info.label_position is '标签位置';