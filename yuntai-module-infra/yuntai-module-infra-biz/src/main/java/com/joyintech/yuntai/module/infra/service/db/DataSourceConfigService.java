package com.joyintech.yuntai.module.infra.service.db;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.infra.controller.admin.db.vo.DataSourceConfigRespVO;
import com.joyintech.yuntai.module.infra.controller.admin.db.vo.DataSourceConfigSaveReqVO;
import com.joyintech.yuntai.module.infra.dal.dataobject.db.DataSourceConfigDO;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * 数据源配置 Service 接口
 *
 * @author 兆尹云台
 */
public interface DataSourceConfigService {

    /**
     * 创建数据源配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDataSourceConfig(@Valid DataSourceConfigSaveReqVO createReqVO);

    /**
     * 更新数据源配置
     *
     * @param updateReqVO 更新信息
     */
    void updateDataSourceConfig(@Valid DataSourceConfigSaveReqVO updateReqVO);

    /**
     * 删除数据源配置
     *
     * @param id 编号
     */
    void deleteDataSourceConfig(Long id);

    /**
     * 获得数据源配置
     *
     * @param id 编号
     * @return 数据源配置
     */
    DataSourceConfigDO getDataSourceConfig(Long id);

    /**
     * 获得数据源配置列表
     *
     * @return 数据源配置列表
     */
    List<DataSourceConfigDO> getDataSourceConfigList(String name);

    /**
     * 获得数据源配置列表
     *
     * @return 数据源配置列表
     */
    List<DataSourceConfigDO> getDataSourceList(Set<Long> dataSourceIds);

    /**
     * 获得数据源配置列表
     *
     * @return 数据源配置列表
     */
    DataSourceConfigDO getDataSource(String code);

    /**
     * 从数据库加载数据源配置
     *
     */
    void loadDataSourceFromDb();
}
