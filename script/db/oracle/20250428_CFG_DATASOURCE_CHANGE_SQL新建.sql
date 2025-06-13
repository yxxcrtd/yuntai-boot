create table CFG_DATASOURCE_CHANGE_SQL
(
    ID               NUMBER(38)           not null,
    ORDER_NO         NUMBER(38),
    SQL_TYPE         VARCHAR2(128),
    TABLE_NAME       VARCHAR2(128),
    SQL_CONTENT      VARCHAR2(4000)  ,
    CREATOR          VARCHAR2(64),
    CREATE_TIME      TIMESTAMP(6)
)
/

comment on table CFG_DATASOURCE_CHANGE_SQL is 'SQL执行记录表'
/

comment on column CFG_DATASOURCE_CHANGE_SQL.ID is '主键ID'
/

comment on column CFG_DATASOURCE_CHANGE_SQL.ORDER_NO is 'sql执行的时的时间戳'
/

comment on column CFG_DATASOURCE_CHANGE_SQL.TABLE_NAME is '表名'
/

comment on column CFG_DATASOURCE_CHANGE_SQL.SQL_CONTENT is 'SQL内容'
/

comment on column CFG_DATASOURCE_CHANGE_SQL.SQL_TYPE is '待执行sql的类型 I(insert)/U(update)/D(update)'
/

comment on column CFG_DATASOURCE_CHANGE_SQL.CREATOR is '创建人'
/

comment on column CFG_DATASOURCE_CHANGE_SQL.CREATE_TIME is '创建时间'
/


