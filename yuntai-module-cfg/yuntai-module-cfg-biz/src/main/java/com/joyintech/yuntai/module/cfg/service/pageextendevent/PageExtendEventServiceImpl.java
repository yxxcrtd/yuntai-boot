package com.joyintech.yuntai.module.cfg.service.pageextendevent;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pageextendevent.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageextendevent.PageExtendEventDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.pageextendevent.PageExtendEventMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 页面事件扩展配置 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class PageExtendEventServiceImpl implements PageExtendEventService {

    @Resource
    private PageExtendEventMapper pageExtendEventMapper;

    @Override
    public Long createPageExtendEvent(PageExtendEventSaveReqVO createReqVO) {
        // 插入
        PageExtendEventDO pageExtendEvent = BeanUtils.toBean(createReqVO, PageExtendEventDO.class);
        pageExtendEventMapper.insert(pageExtendEvent);
        // 返回
        return pageExtendEvent.getId();
    }

    @Override
    public void updatePageExtendEvent(PageExtendEventSaveReqVO updateReqVO) {
        // 校验存在
        validatePageExtendEventExists(updateReqVO.getId());
        // 更新
        PageExtendEventDO updateObj = BeanUtils.toBean(updateReqVO, PageExtendEventDO.class);
        pageExtendEventMapper.updateById(updateObj);
    }

    @Override
    public void deletePageExtendEvent(Long id) {
        // 校验存在
        validatePageExtendEventExists(id);
        // 删除
        pageExtendEventMapper.deleteById(id);
    }

    private void validatePageExtendEventExists(Long id) {
        if (pageExtendEventMapper.selectById(id) == null) {
            throw exception(PAGE_EXTEND_EVENT_NOT_EXISTS);
        }
    }

    @Override
    public PageExtendEventDO getPageExtendEvent(Long id) {
        return pageExtendEventMapper.selectById(id);
    }

    @Override
    public PageResult<PageExtendEventDO> getPageExtendEventPage(PageExtendEventPageReqVO pageReqVO) {
        return pageExtendEventMapper.selectPage(pageReqVO);
    }

}