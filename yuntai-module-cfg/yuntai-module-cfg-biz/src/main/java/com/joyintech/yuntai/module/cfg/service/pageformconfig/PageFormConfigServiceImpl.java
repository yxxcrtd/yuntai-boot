package com.joyintech.yuntai.module.cfg.service.pageformconfig;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pageformconfig.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageformconfig.PageFormConfigDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.pageformconfig.PageFormConfigMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 表单页配置 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class PageFormConfigServiceImpl implements PageFormConfigService {

    @Resource
    private PageFormConfigMapper pageFormConfigMapper;

    @Override
    public Long createPageFormConfig(PageFormConfigSaveReqVO createReqVO) {
        // 插入
        PageFormConfigDO pageFormConfig = BeanUtils.toBean(createReqVO, PageFormConfigDO.class);
        pageFormConfigMapper.insert(pageFormConfig);
        // 返回
        return pageFormConfig.getId();
    }

    @Override
    public void updatePageFormConfig(PageFormConfigSaveReqVO updateReqVO) {
        // 校验存在
        validatePageFormConfigExists(updateReqVO.getId());
        // 更新
        PageFormConfigDO updateObj = BeanUtils.toBean(updateReqVO, PageFormConfigDO.class);
        pageFormConfigMapper.updateById(updateObj);
    }

    @Override
    public void deletePageFormConfig(Long id) {
        // 校验存在
        validatePageFormConfigExists(id);
        // 删除
        pageFormConfigMapper.deleteById(id);
    }

    private void validatePageFormConfigExists(Long id) {
        if (pageFormConfigMapper.selectById(id) == null) {
            throw exception(PAGE_FORM_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public PageFormConfigDO getPageFormConfig(Long id) {
        return pageFormConfigMapper.selectById(id);
    }

    @Override
    public PageResult<PageFormConfigDO> getPageFormConfigPage(PageFormConfigPageReqVO pageReqVO) {
        return pageFormConfigMapper.selectPage(pageReqVO);
    }

}