package com.joyintech.yuntai.module.cfg.service.componentattribute;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentattribute.ComponentAttributeDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.componentattribute.ComponentAttributeMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 组件属性 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ComponentAttributeServiceImpl implements ComponentAttributeService {

    @Resource
    private ComponentAttributeMapper componentAttributeMapper;

    @Override
    public Long createComponentAttribute(ComponentAttributeSaveReqVO createReqVO) {
        // 插入
        ComponentAttributeDO componentAttribute = BeanUtils.toBean(createReqVO, ComponentAttributeDO.class);
        componentAttributeMapper.insert(componentAttribute);
        // 返回
        return componentAttribute.getId();
    }

    @Override
    public void updateComponentAttribute(ComponentAttributeSaveReqVO updateReqVO) {
        // 校验存在
        validateComponentAttributeExists(updateReqVO.getId());
        // 更新
        ComponentAttributeDO updateObj = BeanUtils.toBean(updateReqVO, ComponentAttributeDO.class);
        componentAttributeMapper.updateById(updateObj);
    }

    @Override
    public void deleteComponentAttribute(Long id) {
        // 校验存在
        validateComponentAttributeExists(id);
        // 删除
        componentAttributeMapper.deleteById(id);
    }

    private void validateComponentAttributeExists(Long id) {
        if (componentAttributeMapper.selectById(id) == null) {
            throw exception(COMPONENT_ATTRIBUTE_NOT_EXISTS);
        }
    }

    @Override
    public ComponentAttributeDO getComponentAttribute(Long id) {
        ComponentAttributeDO componentAttributeDO = componentAttributeMapper.selectById(id);
        if (componentAttributeDO == null) {
            throw exception(COMPONENT_ATTRIBUTE_NOT_EXISTS);
        }
        return componentAttributeDO;
    }

    @Override
    public PageResult<ComponentAttributeDO> getComponentAttributePage(ComponentAttributePageReqVO pageReqVO) {
        return componentAttributeMapper.selectPage(pageReqVO);
    }

    @Override
    public boolean createComponentAttributeList(List<ComponentAttributeSaveReqVO> createReqVO) {
        return componentAttributeMapper.insertBatch(BeanUtils.toBean(createReqVO, ComponentAttributeDO.class));
    }

    @Override
    public List<ComponentAttributeRespVO> selectComponent(Long id) {
        List<ComponentAttributeDO> componentAttributeDOS = componentAttributeMapper.selectList(new QueryWrapper<ComponentAttributeDO>()
                .eq("component_id", id)
                .orderByAsc("sort")
        );
        return BeanUtils.toBean(componentAttributeDOS, ComponentAttributeRespVO.class);
    }

}