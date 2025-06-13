package com.joyintech.yuntai.module.cfg.service.pageapi;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pageapi.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageapi.PageApiDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面api Service 接口
 *
 * @author 兆尹云台
 */
public interface PageApiService {

    /**
     * 创建页面api
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageApi(@Valid PageApiSaveReqVO createReqVO);

    /**
     * 更新页面api
     *
     * @param updateReqVO 更新信息
     */
    void updatePageApi(@Valid PageApiSaveReqVO updateReqVO);

    /**
     * 删除页面api
     *
     * @param id 编号
     */
    void deletePageApi(Long id);

    /**
     * 获得页面api
     *
     * @param id 编号
     * @return 页面api
     */
    PageApiDO getPageApi(Long id);

    /**
     * 获得页面api分页
     *
     * @param pageReqVO 分页查询
     * @return 页面api分页
     */
    PageResult<PageApiDO> getPageApiPage(PageApiPageReqVO pageReqVO);

}