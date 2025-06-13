package com.joyintech.yuntai.module.cfg.service.eventconfig;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.eventconfig.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.eventconfig.EventConfigDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面事件配置 Service 接口
 *
 * @author 兆尹云台
 */
public interface EventConfigService {

    /**
     * 创建页面事件配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEventConfig(@Valid EventConfigSaveReqVO createReqVO);

    /**
     * 更新页面事件配置
     *
     * @param updateReqVO 更新信息
     */
    void updateEventConfig(@Valid EventConfigSaveReqVO updateReqVO);

    /**
     * 删除页面事件配置
     *
     * @param id 编号
     */
    void deleteEventConfig(Long id);

    /**
     * 获得页面事件配置
     *
     * @param id 编号
     * @return 页面事件配置
     */
    EventConfigDO getEventConfig(Long id);

    /**
     * 获得页面事件配置分页
     *
     * @param pageReqVO 分页查询
     * @return 页面事件配置分页
     */
    PageResult<EventConfigDO> getEventConfigPage(EventConfigPageReqVO pageReqVO);

}