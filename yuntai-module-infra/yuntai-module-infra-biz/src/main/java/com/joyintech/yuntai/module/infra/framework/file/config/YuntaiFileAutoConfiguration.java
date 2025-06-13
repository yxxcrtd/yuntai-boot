package com.joyintech.yuntai.module.infra.framework.file.config;

import com.joyintech.yuntai.module.infra.framework.file.core.client.FileClientFactory;
import com.joyintech.yuntai.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author 兆尹云台
 */
@Configuration(proxyBeanMethods = false)
public class YuntaiFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
