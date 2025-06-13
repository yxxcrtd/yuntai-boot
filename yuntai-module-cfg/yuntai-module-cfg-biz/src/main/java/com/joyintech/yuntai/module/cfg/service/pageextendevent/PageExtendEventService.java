package com.joyintech.yuntai.module.cfg.service.pageextendevent;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pageextendevent.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageextendevent.PageExtendEventDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面事件扩展配置 Service 接口
 *
 * @author 兆尹云台
 */
public interface PageExtendEventService {

    /**
     * 创建页面事件扩展配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageExtendEvent(@Valid PageExtendEventSaveReqVO createReqVO);

    /**
     * 更新页面事件扩展配置
     *
     * @param updateReqVO 更新信息
     */
    void updatePageExtendEvent(@Valid PageExtendEventSaveReqVO updateReqVO);

    /**
     * 删除页面事件扩展配置
     *
     * @param id 编号
     */
    void deletePageExtendEvent(Long id);

    /**
     * 获得页面事件扩展配置
     *
     * @param id 编号
     * @return 页面事件扩展配置
     */
    PageExtendEventDO getPageExtendEvent(Long id);

    /**
     * 获得页面事件扩展配置分页
     *
     * @param pageReqVO 分页查询
     * @return 页面事件扩展配置分页
     */
    PageResult<PageExtendEventDO> getPageExtendEventPage(PageExtendEventPageReqVO pageReqVO);

}