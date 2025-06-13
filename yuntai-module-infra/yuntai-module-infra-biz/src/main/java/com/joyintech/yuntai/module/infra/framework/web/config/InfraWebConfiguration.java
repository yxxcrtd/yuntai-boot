package com.joyintech.yuntai.module.infra.framework.web.config;

import com.joyintech.yuntai.framework.swagger.config.YuntaiSwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * infra 模块的 web 组件的 Configuration
 *
 * @author 兆尹云台
 */
@Configuration(proxyBeanMethods = false)
public class InfraWebConfiguration {

    /**
     * infra 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi infraGroupedOpenApi() {
        return YuntaiSwaggerAutoConfiguration.buildGroupedOpenApi("infra");
    }

}
