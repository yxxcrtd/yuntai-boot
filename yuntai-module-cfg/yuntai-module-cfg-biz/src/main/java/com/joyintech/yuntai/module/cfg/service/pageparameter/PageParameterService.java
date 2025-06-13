package com.joyintech.yuntai.module.cfg.service.pageparameter;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pageparameter.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageparameter.PageParameterDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面参数 Service 接口
 *
 * @author 兆尹云台
 */
public interface PageParameterService {

    /**
     * 创建页面参数
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageParameter(@Valid PageParameterSaveReqVO createReqVO);

    /**
     * 更新页面参数
     *
     * @param updateReqVO 更新信息
     */
    void updatePageParameter(@Valid PageParameterSaveReqVO updateReqVO);

    /**
     * 删除页面参数
     *
     * @param id 编号
     */
    void deletePageParameter(Long id);

    /**
     * 获得页面参数
     *
     * @param id 编号
     * @return 页面参数
     */
    PageParameterDO getPageParameter(Long id);

    /**
     * 获得页面参数分页
     *
     * @param pageReqVO 分页查询
     * @return 页面参数分页
     */
    PageResult<PageParameterDO> getPageParameterPage(PageParameterPageReqVO pageReqVO);

}