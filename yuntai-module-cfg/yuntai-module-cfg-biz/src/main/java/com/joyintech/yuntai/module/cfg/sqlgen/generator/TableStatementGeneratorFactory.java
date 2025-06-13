package com.joyintech.yuntai.module.cfg.sqlgen.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.github.yulichang.toolkit.SpringContentUtils;
import com.joyintech.yuntai.framework.mybatis.core.util.JdbcUtils;
import com.joyintech.yuntai.module.cfg.sqlgen.generator.mysql.MysqlTableStatementGenerator;
import com.joyintech.yuntai.module.cfg.sqlgen.generator.oracle.OracleTableStatementGenerator;
import com.joyintech.yuntai.module.infra.api.db.DataSourceConfigApi;
import com.joyintech.yuntai.module.infra.api.db.dto.DataSourceConfigRespDTO;

/**
 * 建表语句生成器工厂方法
 *
 * @author abator 2024/9/24
 */
public class TableStatementGeneratorFactory {

    public static DbType getDbType(Long dataSourceId) {
        DataSourceConfigApi dataSourceConfigApi = SpringContentUtils.getBean(DataSourceConfigApi.class);
        DataSourceConfigRespDTO dataSourceConfig = dataSourceConfigApi.getDataSourceConfig(dataSourceId);
        return JdbcUtils.getDbType(dataSourceConfig.getUrl());
    }

    public static TableStatementGenerator getGenerator(Long dataSourceId) {
        DataSourceConfigApi dataSourceConfigApi = SpringContentUtils.getBean(DataSourceConfigApi.class);
        DataSourceConfigRespDTO dataSourceConfig = dataSourceConfigApi.getDataSourceConfig(dataSourceId);
        DbType dbType = JdbcUtils.getDbType(dataSourceConfig.getUrl());

        return getGenerator(dbType);
    }

    public static TableStatementGenerator getGenerator(DbType dbType) {
        switch (dbType) {
            case ORACLE:
                return new OracleTableStatementGenerator();
            case MYSQL:
                return new MysqlTableStatementGenerator();
            default:
                throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }
    }
}
