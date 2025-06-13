package com.joyintech.yuntai.module.cfg.service.dataconversion;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.dataconversion.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataconversion.DataConversionDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 数据转换 Service 接口
 *
 * @author 兆尹云台
 */
public interface DataConversionService {

    /**
     * 创建数据转换
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDataConversion(@Valid DataConversionSaveReqVO createReqVO);

    /**
     * 更新数据转换
     *
     * @param updateReqVO 更新信息
     */
    void updateDataConversion(@Valid DataConversionSaveReqVO updateReqVO);

    /**
     * 删除数据转换
     *
     * @param id 编号
     */
    void deleteDataConversion(Long id);

    /**
     * 获得数据转换
     *
     * @param id 编号
     * @return 数据转换
     */
    DataConversionDO getDataConversion(Long id);

    /**
     * 获得数据转换分页
     *
     * @param pageReqVO 分页查询
     * @return 数据转换分页
     */
    PageResult<DataConversionDO> getDataConversionPage(DataConversionPageReqVO pageReqVO);

}