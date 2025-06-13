package com.joyintech.yuntai.module.cfg.service.pageparameter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.PageListConfigSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagegroup.PageGroupDO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.joyintech.yuntai.module.cfg.controller.admin.pageparameter.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageparameter.PageParameterDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.pageparameter.PageParameterMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 页面参数 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class PageParameterServiceImpl implements PageParameterService {

    @Resource
    private PageParameterMapper pageParameterMapper;

    @Override
    public Long createPageParameter(PageParameterSaveReqVO createReqVO) {
        // 插入
        PageParameterDO pageParameter = BeanUtils.toBean(createReqVO, PageParameterDO.class);
        pageParameterMapper.insert(pageParameter);
        // 返回
        return pageParameter.getId();
    }

    @Override
    public void updatePageParameter(PageParameterSaveReqVO updateReqVO) {
        // 校验存在
        validatePageParameterExists(updateReqVO.getId());
        // 更新
        PageParameterDO updateObj = BeanUtils.toBean(updateReqVO, PageParameterDO.class);
        pageParameterMapper.updateById(updateObj);
    }

    @Override
    public void deletePageParameter(Long id) {
        // 校验存在
        validatePageParameterExists(id);
        // 删除
        pageParameterMapper.deleteById(id);
    }

    private void validatePageParameterExists(Long id) {
        if (pageParameterMapper.selectById(id) == null) {
            throw exception(PAGE_PARAMETER_NOT_EXISTS);
        }
    }

    @Override
    public PageParameterDO getPageParameter(Long id) {
        return pageParameterMapper.selectById(id);
    }

    @Override
    public PageResult<PageParameterDO> getPageParameterPage(PageParameterPageReqVO pageReqVO) {
        return pageParameterMapper.selectPage(pageReqVO);
    }

    public void addPageParameter(List<PageParameterSaveReqVO> pageParameterSaveReqVOS) {
        // 新增参数
        List<PageParameterDO> bean = BeanUtils.toBean(pageParameterSaveReqVOS, PageParameterDO.class);
        pageParameterMapper.insertOrUpdateBatch(bean);

        // 删除没有的分组
        if (!bean.isEmpty()) {
            List<Long> list = bean.stream().map(PageParameterDO::getId).collect(Collectors.toList());
            pageParameterMapper.delete(new QueryWrapper<PageParameterDO>()
                    .eq("page_id", bean.get(0).getPageId())
                    .notIn("id", list)
            );
        }
    }
}
