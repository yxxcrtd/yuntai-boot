package com.joyintech.yuntai.module.cfg.service.lowcodeapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/12
 */
@Component
public class ModuleCacheInit implements CommandLineRunner {
    @Resource
    private ModuleCacheComponent cacheComponent;

    @Override
    public void run(String... args) throws Exception {
        cacheComponent.initCache();
    }
}
