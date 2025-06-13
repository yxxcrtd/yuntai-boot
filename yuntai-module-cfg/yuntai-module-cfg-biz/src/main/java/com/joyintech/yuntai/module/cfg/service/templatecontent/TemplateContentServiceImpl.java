package com.joyintech.yuntai.module.cfg.service.templatecontent;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.templatecontent.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templatecontent.TemplateContentDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.templatecontent.TemplateContentMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 模版内容 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class TemplateContentServiceImpl implements TemplateContentService {

    @Resource
    private TemplateContentMapper templateContentMapper;

    @Override
    public Long createTemplateContent(TemplateContentSaveReqVO createReqVO) {
        // 插入
        TemplateContentDO templateContent = BeanUtils.toBean(createReqVO, TemplateContentDO.class);
        templateContentMapper.insert(templateContent);
        // 返回
        return templateContent.getId();
    }

    @Override
    public void updateTemplateContent(TemplateContentSaveReqVO updateReqVO) {
        // 校验存在
        validateTemplateContentExists(updateReqVO.getId());
        // 更新
        TemplateContentDO updateObj = BeanUtils.toBean(updateReqVO, TemplateContentDO.class);
        templateContentMapper.updateById(updateObj);
    }

    @Override
    public void deleteTemplateContent(Long id) {
        // 校验存在
        validateTemplateContentExists(id);
        // 删除
        templateContentMapper.deleteById(id);
    }

    private void validateTemplateContentExists(Long id) {
        if (templateContentMapper.selectById(id) == null) {
            throw exception(TEMPLATE_CONTENT_NOT_EXISTS);
        }
    }

    @Override
    public TemplateContentDO getTemplateContent(Long id) {
        return templateContentMapper.selectById(id);
    }

    @Override
    public PageResult<TemplateContentDO> getTemplateContentPage(TemplateContentPageReqVO pageReqVO) {
        return templateContentMapper.selectPage(pageReqVO);
    }

}