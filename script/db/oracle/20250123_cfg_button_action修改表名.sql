alter table cfg_button_action add page_type varchar2(32);
comment on column cfg_button_action.page_type is '关联页面类型';

alter table cfg_button_action add relevance_url varchar2(2000);
comment on column cfg_button_action.relevance_url is '跳转地址';