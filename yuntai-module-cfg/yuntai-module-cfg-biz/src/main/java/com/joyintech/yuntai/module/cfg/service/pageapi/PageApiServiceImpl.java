package com.joyintech.yuntai.module.cfg.service.pageapi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.PageListConfigSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagegroup.PageGroupDO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.joyintech.yuntai.module.cfg.controller.admin.pageapi.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageapi.PageApiDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.pageapi.PageApiMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 页面api Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class PageApiServiceImpl implements PageApiService {

    @Resource
    private PageApiMapper pageApiMapper;

    @Override
    public Long createPageApi(PageApiSaveReqVO createReqVO) {
        // 插入
        PageApiDO pageApi = BeanUtils.toBean(createReqVO, PageApiDO.class);
        pageApiMapper.insert(pageApi);
        // 返回
        return pageApi.getId();
    }

    @Override
    public void updatePageApi(PageApiSaveReqVO updateReqVO) {
        // 校验存在
        validatePageApiExists(updateReqVO.getId());
        // 更新
        PageApiDO updateObj = BeanUtils.toBean(updateReqVO, PageApiDO.class);
        pageApiMapper.updateById(updateObj);
    }

    @Override
    public void deletePageApi(Long id) {
        // 校验存在
        validatePageApiExists(id);
        // 删除
        pageApiMapper.deleteById(id);
    }

    private void validatePageApiExists(Long id) {
        if (pageApiMapper.selectById(id) == null) {
            throw exception(PAGE_API_NOT_EXISTS);
        }
    }

    @Override
    public PageApiDO getPageApi(Long id) {
        return pageApiMapper.selectById(id);
    }

    @Override
    public PageResult<PageApiDO> getPageApiPage(PageApiPageReqVO pageReqVO) {
        return pageApiMapper.selectPage(pageReqVO);
    }
}