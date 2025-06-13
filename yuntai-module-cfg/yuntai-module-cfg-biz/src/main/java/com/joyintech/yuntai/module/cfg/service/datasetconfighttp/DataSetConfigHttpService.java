package com.joyintech.yuntai.module.cfg.service.datasetconfighttp;

import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfighttp.DataSetConfigHttpDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;

/**
 * 数据集-http请求内容 Service 接口
 *
 * @author 兆尹云台
 */
public interface DataSetConfigHttpService {

    /**
     * 创建数据集-http请求内容
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDataSetConfigHttp(@Valid DataSetConfigHttpSaveReqVO createReqVO);

    /**
     * 更新数据集-http请求内容
     *
     * @param updateReqVO 更新信息
     */
    void updateDataSetConfigHttp(@Valid DataSetConfigHttpSaveReqVO updateReqVO);

    /**
     * 删除数据集-http请求内容
     *
     * @param id 编号
     */
    void deleteDataSetConfigHttp(Long id);

    /**
     * 获得数据集-http请求内容
     *
     * @param id 编号
     * @return 数据集-http请求内容
     */
    DataSetConfigHttpDO getDataSetConfigHttp(Long id);

    /**
     * 获得数据集-http请求内容分页
     *
     * @param pageReqVO 分页查询
     * @return 数据集-http请求内容分页
     */
    PageResult<DataSetConfigHttpDO> getDataSetConfigHttpPage(DataSetConfigHttpPageReqVO pageReqVO);

}