package com.joyintech.yuntai.module.cfg.service.dataformat;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.dataformat.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataformat.DataFormatDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面数据格式化 Service 接口
 *
 * @author 兆尹云台
 */
public interface DataFormatService {

    /**
     * 创建页面数据格式化
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDataFormat(@Valid DataFormatSaveReqVO createReqVO);

    /**
     * 更新页面数据格式化
     *
     * @param updateReqVO 更新信息
     */
    void updateDataFormat(@Valid DataFormatSaveReqVO updateReqVO);

    /**
     * 删除页面数据格式化
     *
     * @param id 编号
     */
    void deleteDataFormat(Long id);

    /**
     * 获得页面数据格式化
     *
     * @param id 编号
     * @return 页面数据格式化
     */
    DataFormatDO getDataFormat(Long id);

    /**
     * 获得页面数据格式化分页
     *
     * @param pageReqVO 分页查询
     * @return 页面数据格式化分页
     */
    PageResult<DataFormatDO> getDataFormatPage(DataFormatPageReqVO pageReqVO);

}