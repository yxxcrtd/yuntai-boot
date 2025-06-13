package com.joyintech.yuntai.module.cfg.dal.mysql.datasourcechangesql;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasourcechangesql.CfgDatasourceChangeSqlDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * SQL执行记录Mapper接口
 * 提供了对CFG_DATASOURCE_CHANGE_SQL表的基础CRUD操作
 */
@Mapper
public interface CfgDatasourceChangeSqlMapper extends BaseMapperX<CfgDatasourceChangeSqlDO> {
    // 如果需要，可以在这里添加自定义的数据库操作方法及其注释
}