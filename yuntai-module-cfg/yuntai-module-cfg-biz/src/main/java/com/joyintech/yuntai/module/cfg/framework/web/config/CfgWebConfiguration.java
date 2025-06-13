package com.joyintech.yuntai.module.cfg.framework.web.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.joyintech.yuntai.framework.swagger.config.YuntaiSwaggerAutoConfiguration;

/**
 * cfg 模块的 web 组件的 Configuration
 *
 * @author 兆尹云台
 */
@Configuration(proxyBeanMethods = false)
public class CfgWebConfiguration {

    /**
     * infra 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi cfgGroupedOpenApi() {
        return YuntaiSwaggerAutoConfiguration.buildGroupedOpenApi("cfg");
    }

    @Bean
    public GroupedOpenApi apiGroupedOpenApi() {
        return YuntaiSwaggerAutoConfiguration.buildGroupedOpenApi("api");
    }

}
