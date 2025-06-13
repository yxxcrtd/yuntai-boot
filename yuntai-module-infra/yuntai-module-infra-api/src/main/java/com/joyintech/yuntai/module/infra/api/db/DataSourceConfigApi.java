package com.joyintech.yuntai.module.infra.api.db;

import com.joyintech.yuntai.module.infra.api.db.dto.DataSourceConfigRespDTO;

import java.util.List;
import java.util.Set;

/**
 * 数据源配置 API 接口
 *
 * @author abator 2024/9/25
 */
public interface DataSourceConfigApi {

    /**
     * 获得数据源配置
     *
     * @param id 编号
     * @return 数据源配置
     */
    DataSourceConfigRespDTO getDataSourceConfig(Long id);

    /**
     * 获取数据源
     * @param code
     * @return
     */
    DataSourceConfigRespDTO getDataSource(String code);

    /**
     * 根据数据源id查询数据源列表
     * @param dataSourceIds
     * @return
     */
    List<DataSourceConfigRespDTO> getDataSourceList(Set<Long> dataSourceIds);
}
