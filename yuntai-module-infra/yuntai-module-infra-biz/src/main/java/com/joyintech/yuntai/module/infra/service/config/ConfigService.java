package com.joyintech.yuntai.module.infra.service.config;

import java.util.List;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.infra.controller.admin.config.vo.BaseConfigSaveReqVO;
import com.joyintech.yuntai.module.infra.controller.admin.config.vo.ConfigPageReqVO;
import com.joyintech.yuntai.module.infra.controller.admin.config.vo.ConfigSaveReqVO;
import com.joyintech.yuntai.module.infra.dal.dataobject.config.ConfigDO;

import javax.validation.Valid;

/**
 * 参数配置 Service 接口
 *
 * @author 兆尹云台
 */
public interface ConfigService {
    /**
     * 保存参数配置
     *
     * @param reqVOList 参数配置
     */
    void saveConfig(@Valid List<ConfigSaveReqVO> reqVOList);

    /**
     * 创建参数配置
     *
     * @param createReqVO 创建信息
     * @return 配置编号
     */
    Long createConfig(@Valid ConfigSaveReqVO createReqVO);

    /**
     * 创建参数配置
     *
     * @param createReqVO 创建信息
     * @return 配置编号
     */
    Long createBaseConfig(BaseConfigSaveReqVO createReqVO);

    /**
     * 创建参数配置
     *
     * @param createReqVO 创建信息
     * @return 配置编号
     */
    Long createSecurityConfig(BaseConfigSaveReqVO createReqVO);

    /**
     * 创建参数配置
     *
     * @return 配置信息
     */
    BaseConfigSaveReqVO getBaseConfig();

    /**
     * 创建参数配置
     *
     * @return 配置信息
     */
    BaseConfigSaveReqVO getSecurityConfig();

    /**
     * 更新参数配置
     *
     * @param updateReqVO 更新信息
     */
    void updateConfig(@Valid ConfigSaveReqVO updateReqVO);

    /**
     * 删除参数配置
     *
     * @param id 配置编号
     */
    void deleteConfig(Long id);
    /**
     * 获得参数配置
     *
     * @param category 分组
     * @return 参数配置
     */
    List<ConfigDO> getConfig(String category);

    /**
     * 获得参数配置
     *
     * @param id 配置编号
     * @return 参数配置
     */
    ConfigDO getConfig(Long id);

    /**
     * 根据参数键，获得参数配置
     *
     * @param key 配置键
     * @return 参数配置
     */
    ConfigDO getConfigByKey(String key);

    /**
     * 获得参数配置分页列表
     *
     * @param reqVO 分页条件
     * @return 分页列表
     */
    PageResult<ConfigDO> getConfigPage(ConfigPageReqVO reqVO);

}
