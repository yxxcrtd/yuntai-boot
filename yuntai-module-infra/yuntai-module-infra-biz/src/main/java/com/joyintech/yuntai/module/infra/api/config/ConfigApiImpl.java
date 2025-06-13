package com.joyintech.yuntai.module.infra.api.config;

import com.joyintech.yuntai.module.infra.dal.dataobject.config.ConfigDO;
import com.joyintech.yuntai.module.infra.service.config.ConfigService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 参数配置 API 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ConfigApiImpl implements ConfigApi {

    @Resource
    private ConfigService configService;

    @Override
    public String getConfigValueByKey(String key) {
        ConfigDO config = configService.getConfigByKey(key);
        return config != null ? config.getValue() : null;
    }

}
