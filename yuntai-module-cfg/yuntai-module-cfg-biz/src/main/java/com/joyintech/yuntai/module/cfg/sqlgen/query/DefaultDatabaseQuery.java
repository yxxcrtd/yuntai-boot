/*
 * Copyright (c) 2011-2024, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joyintech.yuntai.module.cfg.sqlgen.query;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.joyintech.yuntai.module.cfg.sqlgen.jdbc.DefaultDatabaseMetaDataWrapper;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Table;

import lombok.extern.slf4j.Slf4j;

/**
 * 元数据查询数据库信息.
 *
 * @author abator 2024/09/25.
 * <p>
 * 测试通过的数据库：H2、Mysql-5.7.37、Mysql-8.0.25、PostgreSQL-11.15、PostgreSQL-14.1、Oracle-11.2.0.1.0、DM8
 * </p>
 * <p>
 * FAQ:
 * 1.Mysql无法读取表注释: 链接增加属性 remarks=true&useInformationSchema=true
 * 或者通过{@link DataSourceConfig.Builder#addConnectionProperty(String, String)}设置
 * 2.Oracle无法读取注释: 增加属性remarks=true，也有些驱动版本说是增加remarksReporting=true
 * {@link DataSourceConfig.Builder#addConnectionProperty(String, String)}
 * </p>
 */
@Slf4j
public class DefaultDatabaseQuery {
    protected final ConfigBuilder configBuilder;

    protected final DataSourceConfig dataSourceConfig;

    protected final StrategyConfig strategyConfig;

    protected final GlobalConfig globalConfig;
    protected final DefaultDatabaseMetaDataWrapper databaseMetaDataWrapper;

    public DefaultDatabaseQuery(@NotNull ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        this.dataSourceConfig = configBuilder.getDataSourceConfig();
        this.strategyConfig = configBuilder.getStrategyConfig();
        this.globalConfig = configBuilder.getGlobalConfig();
        this.databaseMetaDataWrapper = new DefaultDatabaseMetaDataWrapper(
                dataSourceConfig.getConn(),
                dataSourceConfig.getSchemaName());
    }

    /**
     * 查询数据库所有表信息(根据过滤策略过滤表)
     */
    public @NotNull List<Table> queryTables() {
        try {
            boolean isInclude = !strategyConfig.getInclude().isEmpty();
            boolean isExclude = !strategyConfig.getExclude().isEmpty();
            //所有的表信息
            List<Table> tables = this.getTables();
            //需要反向生成或排除的表信息
            List<Table> includeTableList = new ArrayList<>();
            List<Table> excludeTableList = new ArrayList<>();
            tables.forEach(table -> {
                String tableName = table.getTableName();
                if (StringUtils.isNotBlank(tableName)) {
                    if (isInclude && strategyConfig.matchIncludeTable(tableName)) {
                        includeTableList.add(table);
                    } else if (isExclude && strategyConfig.matchExcludeTable(tableName)) {
                        excludeTableList.add(table);
                    }
                }
            });
            filter(tables, includeTableList, excludeTableList);

            return tables;
        } finally {
            // 数据库操作完成,释放连接对象
            databaseMetaDataWrapper.closeConnection();
        }
    }
    protected void filter(List<Table> tableList, List<Table> includeTableList, List<Table> excludeTableList) {
        boolean isInclude = !strategyConfig.getInclude().isEmpty();
        boolean isExclude = !strategyConfig.getExclude().isEmpty();
        if (isExclude || isInclude) {
            // 需要反向生成的表信息
            if (isExclude) {
                tableList.removeAll(excludeTableList);
            } else {
                tableList.clear();
                tableList.addAll(includeTableList);
            }
        }
    }

    /**
     * 注意关闭数据库连接
     */
    public List<Table> getTables() {
        // 是否跳过视图
        boolean skipView = strategyConfig.isSkipView();
        // 获取表过滤
        String tableNamePattern = null;
        if (strategyConfig.getLikeTable() != null) {
            tableNamePattern = strategyConfig.getLikeTable().getValue();
        }
        return databaseMetaDataWrapper.getTables(tableNamePattern,
                                                 skipView ? new String[] {"TABLE"} : new String[] {"TABLE", "VIEW"});
    }

}
