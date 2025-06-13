package com.joyintech.yuntai.module.infra.api.db;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.infra.api.db.dto.DataSourceConfigRespDTO;
import com.joyintech.yuntai.module.infra.dal.dataobject.db.DataSourceConfigDO;
import com.joyintech.yuntai.module.infra.service.db.DataSourceConfigService;

import java.util.List;
import java.util.Set;

/**
 * 数据源配置 API 实现类
 *
 * @author abator 2024/9/25
 */
@Service
@Validated
public class DataSourceConfigApiImpl implements DataSourceConfigApi {

    @Resource
    private DataSourceConfigService dataSourceConfigService;

    @Override
    public DataSourceConfigRespDTO getDataSourceConfig(Long id) {
        DataSourceConfigDO dataSourceConfig = dataSourceConfigService.getDataSourceConfig(id);
        return BeanUtils.toBean(dataSourceConfig, DataSourceConfigRespDTO.class);
    }

    @Override
    public DataSourceConfigRespDTO getDataSource(String code) {
        DataSourceConfigDO dataSourceConfig = dataSourceConfigService.getDataSource(code);
        return BeanUtils.toBean(dataSourceConfig, DataSourceConfigRespDTO.class);
    }

    @Override
    public List<DataSourceConfigRespDTO> getDataSourceList(Set<Long> dataSourceIds) {
        List<DataSourceConfigDO> list = dataSourceConfigService.getDataSourceList(dataSourceIds);
        return BeanUtils.toBean(list, DataSourceConfigRespDTO.class);
    }
}
