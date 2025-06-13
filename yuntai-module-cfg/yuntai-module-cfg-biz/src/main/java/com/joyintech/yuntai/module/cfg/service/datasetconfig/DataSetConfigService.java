package com.joyintech.yuntai.module.cfg.service.datasetconfig;

import java.util.List;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig.vo.DataSetConfigPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig.vo.DataSetConfigSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfig.DataSetConfigDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;

/**
 * 数据集管理 Service 接口
 *
 * @author 兆尹云台
 */
public interface DataSetConfigService {

    /**
     * 创建数据集管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDataSetConfig(@Valid DataSetConfigSaveReqVO createReqVO);

    /**
     * 更新数据集管理
     *
     * @param updateReqVO 更新信息
     */
    void updateDataSetConfig(@Valid DataSetConfigSaveReqVO updateReqVO);

    /**
     * 删除数据集管理
     *
     * @param id 编号
     */
    void deleteDataSetConfig(Long id);

    /**
     * 获得数据集管理
     *
     * @param id 编号
     * @return 数据集管理
     */
    DataSetConfigDO getDataSetConfig(Long id);

    /**
     * 获得数据集管理分页
     *
     * @param pageReqVO 分页查询
     * @return 数据集管理分页
     */
    PageResult<DataSetConfigDO> getDataSetConfigPage(DataSetConfigPageReqVO pageReqVO);

    /**
     * 获得数据集配置列表
     *
     * @return 数据源配置列表
     */
    List<DataSetConfigDO> getDataSourceConfigList(String name);

    /**
     * 获得数据集管理
     *
     * @param code 编号
     * @return 数据集管理
     */
    DataSetConfigDO getDataSetConfigByCode(String code);
}