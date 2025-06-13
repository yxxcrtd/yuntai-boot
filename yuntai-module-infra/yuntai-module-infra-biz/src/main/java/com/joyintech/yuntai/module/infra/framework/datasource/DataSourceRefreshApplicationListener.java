package com.joyintech.yuntai.module.infra.framework.datasource;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.joyintech.yuntai.module.infra.service.db.DataSourceConfigService;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源加载监听器
 *
 * @author abator 2024/9/29
 */
@Component
@Slf4j
public class DataSourceRefreshApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private DataSourceConfigService dataSourceConfigService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("加载数据库数据源开始");
        dataSourceConfigService.loadDataSourceFromDb();
        log.info("加载数据库数据源完成");

    }
}
