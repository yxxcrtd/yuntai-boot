package com.joyintech.yuntai.module.infra.service.config;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.infra.enums.ErrorCodeConstants.*;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.annotations.VisibleForTesting;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.infra.controller.admin.config.vo.BaseConfigSaveReqVO;
import com.joyintech.yuntai.module.infra.controller.admin.config.vo.ConfigPageReqVO;
import com.joyintech.yuntai.module.infra.controller.admin.config.vo.ConfigSaveReqVO;
import com.joyintech.yuntai.module.infra.convert.config.ConfigConvert;
import com.joyintech.yuntai.module.infra.dal.dataobject.config.ConfigDO;
import com.joyintech.yuntai.module.infra.dal.mysql.config.ConfigMapper;
import com.joyintech.yuntai.module.infra.enums.BaseConfigEnum;
import com.joyintech.yuntai.module.infra.enums.SecurityConfigEnum;
import com.joyintech.yuntai.module.infra.enums.config.ConfigTypeEnum;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 参数配置 Service 实现类
 */
@Service
@Slf4j
@Validated
public class ConfigServiceImpl implements ConfigService {
    @Resource
    private ConfigMapper configMapper;
    public static final String CATEGORY_BASE = "low_base";
    public static final String CATEGORY_SECURITY = "low_security";
    public static final String CATEGORY_PARAM = "Sys-Paramter";


    @Override
    public void saveConfig(List<ConfigSaveReqVO> reqVOList) {
        // 新增list
        List<ConfigDO> insertList = new ArrayList<>();
        // 更新list
        List<ConfigDO> updateList = new ArrayList<>();
        for (ConfigSaveReqVO reqVO : reqVOList) {
            // validateConfigKeyUnique(reqVO.getId(), reqVO.getKey());

            ConfigDO config = ConfigConvert.INSTANCE.convert(reqVO);

            ConfigDO oldConfig = configMapper.selectByKey(reqVO.getKey());
            if (oldConfig != null) {
                config.setId(oldConfig.getId());
                config.setCreator(oldConfig.getCreator());
                config.setCreateTime(oldConfig.getCreateTime());
            }

            // 默认值
            fillDefault(config);
            // 插入或更新
            if (config.getId() == null) {
                insertList.add(config);
            } else {
                updateList.add(config);
            }
        }
        // insertList非空时，批量插入
        if (!insertList.isEmpty()) {
            configMapper.insertBatch(insertList);
        }
        // updateList非空时，批量更新
        if (!updateList.isEmpty()) {
            configMapper.updateBatch(updateList);
        }
    }

    @Override
    public Long createConfig(ConfigSaveReqVO createReqVO) {
        // 校验参数配置 key 的唯一性
        validateConfigKeyUnique(null, createReqVO.getKey());

        // 插入参数配置
        ConfigDO config = ConfigConvert.INSTANCE.convert(createReqVO);
        // 默认值
        fillDefault(config);
        configMapper.insert(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createBaseConfig(BaseConfigSaveReqVO createReqVO) {
        this.save(createReqVO,true);
        return 0L;
    }

    private void save(BaseConfigSaveReqVO createReqVO,Boolean isBase) {
        List<ConfigDO> list = this.getConfig(CATEGORY_BASE);
        if (!isBase) {
            list = this.getConfig(CATEGORY_SECURITY);
        }
        Map<String,Object> map = BeanUtil.beanToMap(createReqVO);
        List<ConfigDO> result = new ArrayList<>();
        if (CollUtil.isEmpty(list)) {
            map.forEach((k,v)->{
                String name = BaseConfigEnum.getByCode(k);
                if (!isBase) {
                    name = SecurityConfigEnum.getByCode(k);
                }
                if (StrUtil.isNotEmpty(name)) {
                    ConfigDO config = new ConfigDO();
                    config.setId(IdWorker.getId());
                    config.setCategory(CATEGORY_BASE);
                    if (!isBase) {
                        config.setCategory(CATEGORY_SECURITY);
                    }
                    config.setName(name);
                    config.setConfigKey(k);
                    config.setValue(v == null ? "" : String.valueOf(v));
                    config.setType(ConfigTypeEnum.CUSTOM.getType());
                    config.setVisible(true);
                    result.add(config);
                }
            });
            configMapper.insertBatch(result);
        }else {
            Map<String, ConfigDO> configMap = list.stream().collect(Collectors.toMap(ConfigDO::getConfigKey, item -> item));
            map.forEach((k,v)->{
                ConfigDO config = configMap.get(k);
                if ((Objects.isNull(config))) {
                    String name = BaseConfigEnum.getByCode(k);
                    if (!isBase) {
                        name = SecurityConfigEnum.getByCode(k);
                    }
                    if (StrUtil.isNotEmpty(name)) {
                        config = new ConfigDO();
                        config.setId(IdWorker.getId());
                        config.setCategory(CATEGORY_BASE);
                        if (!isBase) {
                            config.setCategory(CATEGORY_SECURITY);
                        }
                        config.setName(name);
                        config.setConfigKey(k);
                        config.setType(ConfigTypeEnum.CUSTOM.getType());
                        config.setValue(v == null ? "" : String.valueOf(v));
                        config.setVisible(true);
                        result.add(config);
                    }
                }else {
                    config.setValue(v == null ? "" : String.valueOf(v));
                    result.add(config);
                }
            });
            configMapper.insertOrUpdate(result);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSecurityConfig(BaseConfigSaveReqVO createReqVO) {
        this.save(createReqVO,false);
        return 0L;
    }

    @Override
    public BaseConfigSaveReqVO getBaseConfig() {
        List<ConfigDO> list = this.getConfig(CATEGORY_BASE);
        if (CollUtil.isNotEmpty(list)) {
            Map<String,Object> map = new HashMap<>();
            list.forEach(item->map.put(item.getConfigKey(),item.getValue()));
            return BeanUtil.toBean(map,BaseConfigSaveReqVO.class);
        }
        return new BaseConfigSaveReqVO();
    }

    @Override
    public BaseConfigSaveReqVO getSecurityConfig() {
        List<ConfigDO> list = this.getConfig(CATEGORY_SECURITY);
        if (CollUtil.isNotEmpty(list)) {
            Map<String,Object> map = new HashMap<>();
            list.forEach(item->map.put(item.getConfigKey(),item.getValue()));
            return BeanUtil.toBean(map,BaseConfigSaveReqVO.class);
        }
        return new BaseConfigSaveReqVO();
    }

    private static void fillDefault(ConfigDO config) {
        if (config.getType() == null) {
            config.setType(ConfigTypeEnum.CUSTOM.getType());
        }
    }

    @Override
    public void updateConfig(ConfigSaveReqVO updateReqVO) {
        // 校验自己存在
        validateConfigExists(updateReqVO.getId());
        // 校验参数配置 key 的唯一性
        validateConfigKeyUnique(updateReqVO.getId(), updateReqVO.getKey());

        // 更新参数配置
        ConfigDO config = ConfigConvert.INSTANCE.convert(updateReqVO);
        // 默认值
        fillDefault(config);
        configMapper.updateById(config);
    }

    @Override
    public void deleteConfig(Long id) {
        // 校验配置存在
        ConfigDO config = validateConfigExists(id);
        // 内置配置，不允许删除
        if (ConfigTypeEnum.SYSTEM.getType().equals(config.getType())) {
            throw exception(CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE);
        }
        // 删除
        configMapper.deleteById(id);
    }

    @Override
    public List<ConfigDO> getConfig(String category) {
        return configMapper.selectList(ConfigDO::getCategory, Arrays.asList(StringUtils.splitByWholeSeparator(category, ",")));
    }

    @Override
    public ConfigDO getConfig(Long id) {
        return configMapper.selectById(id);
    }

    @Override
    public ConfigDO getConfigByKey(String key) {
        return configMapper.selectByKey(key);
    }

    @Override
    public PageResult<ConfigDO> getConfigPage(ConfigPageReqVO pageReqVO) {
        return configMapper.selectPage(pageReqVO);
    }

    @VisibleForTesting
    public ConfigDO validateConfigExists(Long id) {
        if (id == null) {
            return null;
        }
        ConfigDO config = configMapper.selectById(id);
        if (config == null) {
            throw exception(CONFIG_NOT_EXISTS);
        }
        return config;
    }

    @VisibleForTesting
    public void validateConfigKeyUnique(Long id, String key) {
        ConfigDO config = configMapper.selectByKey(key);
        if (config == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的参数配置
        if (id == null) {
            throw exception(CONFIG_KEY_DUPLICATE);
        }
        if (!config.getId().equals(id)) {
            throw exception(CONFIG_KEY_DUPLICATE);
        }
    }

}
