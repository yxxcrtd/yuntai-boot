package com.joyintech.yuntai.module.cfg.service.componenttable;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.framework.common.util.tree.TreeNode;
import com.joyintech.yuntai.framework.common.util.tree.TreeUtil;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentattribute.ComponentAttributeDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentgroup.ComponentGroupDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templategroup.TemplateGroupDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.componentattribute.ComponentAttributeMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.componentgroup.ComponentGroupMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.componenttable.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componenttable.ComponentTableDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.componenttable.ComponentTableMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 组件 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ComponentTableServiceImpl implements ComponentTableService {

    @Resource
    private ComponentTableMapper componentTableMapper;

    @Resource
    private ComponentAttributeMapper componentAttributeMapper;

    @Resource
    private ComponentGroupMapper componentGroupMapper;

    @Override
    public Long createComponentTable(ComponentTableSaveReqVO createReqVO) {
        // 插入
        ComponentTableDO componentTable = BeanUtils.toBean(createReqVO, ComponentTableDO.class);
        componentTableMapper.insert(componentTable);

        // 插入属性
        if (createReqVO.getAddComponentAttribute() != null && !createReqVO.getAddComponentAttribute().isEmpty()) {
            createReqVO.getAddComponentAttribute().forEach(attribute -> {
                attribute.setComponentId(componentTable.getId());
            });
            componentAttributeMapper.insert(BeanUtils.toBean(createReqVO.getAddComponentAttribute(), ComponentAttributeDO.class));
        }

        // 修改属性
        if (createReqVO.getUpdateComponentAttribute() != null && !createReqVO.getUpdateComponentAttribute().isEmpty()) {
            componentAttributeMapper.updateById(BeanUtils.toBean(createReqVO.getUpdateComponentAttribute(), ComponentAttributeDO.class));
        }

        // 删除属性
        if (createReqVO.getDelComponentAttribute() != null && !createReqVO.getDelComponentAttribute().isEmpty()) {
            componentAttributeMapper.deleteByIds(createReqVO.getDelComponentAttribute());
        }
        // 返回
        return componentTable.getId();
    }

    @Override
    public void updateComponentTable(ComponentTableSaveReqVO updateReqVO) {
        // 校验存在
        validateComponentTableExists(updateReqVO.getId());
        // 更新
        ComponentTableDO updateObj = BeanUtils.toBean(updateReqVO, ComponentTableDO.class);

        // 插入属性
        if (updateReqVO.getAddComponentAttribute() != null && !updateReqVO.getAddComponentAttribute().isEmpty()) {
            updateReqVO.getAddComponentAttribute().forEach(attribute -> {
                attribute.setComponentId(updateObj.getId());
            });
            componentAttributeMapper.insert(BeanUtils.toBean(updateReqVO.getAddComponentAttribute(), ComponentAttributeDO.class));
        }

        // 修改属性
        if (updateReqVO.getUpdateComponentAttribute() != null && !updateReqVO.getUpdateComponentAttribute().isEmpty()) {
            componentAttributeMapper.updateById(BeanUtils.toBean(updateReqVO.getUpdateComponentAttribute(), ComponentAttributeDO.class));
        }

        // 删除属性
        if (updateReqVO.getDelComponentAttribute() != null && !updateReqVO.getDelComponentAttribute().isEmpty()) {
            componentAttributeMapper.deleteByIds(updateReqVO.getDelComponentAttribute());
        }
        componentTableMapper.updateById(updateObj);
    }

    @Override
    public void deleteComponentTable(Long id) {
        // 校验存在
        validateComponentTableExists(id);
        // 删除
        componentTableMapper.deleteById(id);

        // 删除属性
        componentAttributeMapper.delete(new QueryWrapper<ComponentAttributeDO>().eq("component_id", id));
    }

    private void validateComponentTableExists(Long id) {
        if (componentTableMapper.selectById(id) == null) {
            throw exception(COMPONENT_TABLE_NOT_EXISTS);
        }
    }

    @Override
    public ComponentTableDO getComponentTable(Long id) {
        ComponentTableDO componentTableDO = componentTableMapper.selectById(id);
        if (componentTableDO == null) {
            throw exception(COMPONENT_TABLE_NOT_EXISTS);
        }
        return componentTableDO;
    }

    @Override
    public ComponentTableDO getComponentTableByCode(String code) {
        ComponentTableDO componentTableDO = componentTableMapper.selectOne(ComponentTableDO::getComponentCode,code);
        if (componentTableDO == null) {
            throw exception(COMPONENT_TABLE_NOT_EXISTS);
        }
        return componentTableDO;
    }

    @Override
    public List<ComponentTableDO> getComponentTableByCode(Set<String> code) {
        return componentTableMapper.selectList(new QueryWrapper<ComponentTableDO>().in("component_code", code));
    }

    @Override
    public PageResult<ComponentTableDO> getComponentTablePage(ComponentTablePageReqVO pageReqVO) {
        if (Objects.nonNull(pageReqVO.getGroupId())){
            List<Long> groupIds = new ArrayList<>();
            List<ComponentGroupDO> groupList = componentGroupMapper.selectList();
            TreeUtil.findByIdWithChildren(BeanUtils.toBean(groupList, TreeNode.class), pageReqVO.getGroupId(),groupIds);
            pageReqVO.setGroupIds(groupIds);
            pageReqVO.setGroupId(null);
        }
        PageResult<ComponentTableDO> list = componentTableMapper.selectPage(pageReqVO);

        List<ComponentGroupDO> groupList = componentGroupMapper.selectList();
        list.getList().forEach(table -> {
            for(ComponentGroupDO group : groupList){
                if(group.getId()!=null && group.getId().equals(table.getGroupId())){
                    table.setGroupName(group.getGroupName());
                    break;
                }
            }
        });

        return list;
    }

    @Override
    public List<ComponentTableDO> getComponentTableAllList() {
        return componentTableMapper.selectList();
    }
}
