package com.joyintech.yuntai.module.cfg.service.eventconfig;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.eventconfig.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.eventconfig.EventConfigDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.eventconfig.EventConfigMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 页面事件配置 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class EventConfigServiceImpl implements EventConfigService {

    @Resource
    private EventConfigMapper eventConfigMapper;

    @Override
    public Long createEventConfig(EventConfigSaveReqVO createReqVO) {
        // 插入
        EventConfigDO eventConfig = BeanUtils.toBean(createReqVO, EventConfigDO.class);
        eventConfigMapper.insert(eventConfig);
        // 返回
        return eventConfig.getId();
    }

    @Override
    public void updateEventConfig(EventConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateEventConfigExists(updateReqVO.getId());
        // 更新
        EventConfigDO updateObj = BeanUtils.toBean(updateReqVO, EventConfigDO.class);
        eventConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteEventConfig(Long id) {
        // 校验存在
        validateEventConfigExists(id);
        // 删除
        eventConfigMapper.deleteById(id);
    }

    private void validateEventConfigExists(Long id) {
        if (eventConfigMapper.selectById(id) == null) {
            throw exception(EVENT_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public EventConfigDO getEventConfig(Long id) {
        return eventConfigMapper.selectById(id);
    }

    @Override
    public PageResult<EventConfigDO> getEventConfigPage(EventConfigPageReqVO pageReqVO) {
        return eventConfigMapper.selectPage(pageReqVO);
    }

}