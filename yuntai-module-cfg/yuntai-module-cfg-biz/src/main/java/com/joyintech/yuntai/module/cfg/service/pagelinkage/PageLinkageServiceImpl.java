package com.joyintech.yuntai.module.cfg.service.pagelinkage;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelinkage.PageLinkageDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.pagelinkage.PageLinkageMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 页面联动配置 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class PageLinkageServiceImpl implements PageLinkageService {

    @Resource
    private PageLinkageMapper pageLinkageMapper;

    @Override
    public Long createPageLinkage(PageLinkageSaveReqVO createReqVO) {
        // 插入
        PageLinkageDO pageLinkage = BeanUtils.toBean(createReqVO, PageLinkageDO.class);
        pageLinkageMapper.insert(pageLinkage);
        // 返回
        return pageLinkage.getId();
    }

    @Override
    public void updatePageLinkage(PageLinkageSaveReqVO updateReqVO) {
        // 校验存在
        validatePageLinkageExists(updateReqVO.getId());
        // 更新
        PageLinkageDO updateObj = BeanUtils.toBean(updateReqVO, PageLinkageDO.class);
        pageLinkageMapper.updateById(updateObj);
    }

    @Override
    public void deletePageLinkage(Long id) {
        // 校验存在
        validatePageLinkageExists(id);
        // 删除
        pageLinkageMapper.deleteById(id);
    }

    private void validatePageLinkageExists(Long id) {
        if (pageLinkageMapper.selectById(id) == null) {
            throw exception(PAGE_LINKAGE_NOT_EXISTS);
        }
    }

    @Override
    public PageLinkageDO getPageLinkage(Long id) {
        return pageLinkageMapper.selectById(id);
    }

    @Override
    public PageResult<PageLinkageDO> getPageLinkagePage(PageLinkagePageReqVO pageReqVO) {
        return pageLinkageMapper.selectPage(pageReqVO);
    }

}