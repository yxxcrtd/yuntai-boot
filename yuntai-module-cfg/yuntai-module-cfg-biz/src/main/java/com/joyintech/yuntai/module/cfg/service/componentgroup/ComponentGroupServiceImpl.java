package com.joyintech.yuntai.module.cfg.service.componentgroup;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componenttable.ComponentTableDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.componenttable.ComponentTableMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.componentgroup.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentgroup.ComponentGroupDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.componentgroup.ComponentGroupMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.*;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 组件分组 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ComponentGroupServiceImpl implements ComponentGroupService {

    @Resource
    private ComponentGroupMapper componentGroupMapper;
    @Resource
    private ComponentTableMapper componentTableMapper;

    @Override
    public Long createComponentGroup(ComponentGroupSaveReqVO createReqVO) {
        // 插入
        ComponentGroupDO componentGroup = BeanUtils.toBean(createReqVO, ComponentGroupDO.class);
        componentGroupMapper.insert(componentGroup);
        // 返回
        return componentGroup.getId();
    }

    @Override
    public void updateComponentGroup(ComponentGroupSaveReqVO updateReqVO) {
        // 校验存在
        validateComponentGroupExists(updateReqVO.getId());
        // 更新
        ComponentGroupDO updateObj = BeanUtils.toBean(updateReqVO, ComponentGroupDO.class);
        componentGroupMapper.updateById(updateObj);
    }

    @Override
    public void deleteComponentGroup(Long id) {
        // 校验存在
        validateComponentGroupExists(id);
        // 校验是否存在子集
        List<ComponentGroupDO> componentGroupDOS = componentGroupMapper.selectList(ComponentGroupDO::getParentId,id);
        if (CollUtil.isNotEmpty(componentGroupDOS)) {
            throw exception0(1_003_002_002, "请先删除子分组！");
        }
        List<ComponentTableDO> list = componentTableMapper.selectList(ComponentTableDO::getGroupId,id);
        if (CollUtil.isNotEmpty(list)) {
            throw exception0(1_003_002_002, "该分组下存在组件,无法删除！");
        }
        // 删除该数据下的子数据
        List<ComponentGroupRespVO> tree = this.getTree();
        Set<Long> inMultipleTrees = findDescendantsInMultipleTrees(tree, id);
        // 删除
        componentGroupMapper.deleteByIds(inMultipleTrees);
    }

    private Set<Long> findDescendantsInMultipleTrees(List<ComponentGroupRespVO> tree, Long id) {
        Set<Long> inMultipleTrees = new HashSet<>();
        for (ComponentGroupRespVO treeRoot : tree) {
            Set<Long> descendantsInTree = findDescendantsInTree(treeRoot, id);
            inMultipleTrees.addAll(descendantsInTree);
        }
        return inMultipleTrees;
    }

    private Set<Long> findDescendantsInTree(ComponentGroupRespVO treeRoot, Long id) {
        Set<Long> descendants = new HashSet<>();
        if (findAndCollectDescendants(treeRoot, id, descendants)) {
            // 从结果中移除目标节点ID（如果不需要包括目标节点）
            descendants.add(id);
        }
        return descendants;
    }

    private boolean findAndCollectDescendants(ComponentGroupRespVO treeRoot, Long id, Set<Long> descendants) {
        if (treeRoot == null) {
            return false;
        }

        if (Objects.equals(treeRoot.getId(), id)) {
            // 对于目标节点，递归地添加其所有子节点的ID
            if (!(treeRoot.getChildren() == null)) {
                for (ComponentGroupRespVO child : treeRoot.getChildren()) {
                    findAndCollectDescendants(child, id, descendants);
                    descendants.add(child.getId());
                }
            }
            return true; // 找到目标节点
        } else {
            // 在子节点中继续查找
            if (!(treeRoot.getChildren() == null)) {
                for (ComponentGroupRespVO child : treeRoot.getChildren()) {
                    if (findAndCollectDescendants(child, id, descendants)) {
                        return true; // 在子树中找到了目标节点
                    }
                }
            }
        }
        return false; // 未找到目标节点
    }

    private void validateComponentGroupExists(Long id) {
        if (componentGroupMapper.selectById(id) == null) {
            throw exception(COMPONENT_GROUP_NOT_EXISTS);
        }
    }

    @Override
    public ComponentGroupDO getComponentGroup(Long id) {
        ComponentGroupDO componentGroupDO = componentGroupMapper.selectById(id);
        if (componentGroupDO == null) {
            throw exception(COMPONENT_GROUP_NOT_EXISTS);
        }
        return componentGroupDO;
    }

    @Override
    public PageResult<ComponentGroupDO> getComponentGroupPage(ComponentGroupPageReqVO pageReqVO) {
        return componentGroupMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ComponentGroupRespVO> getTree() {
        List<ComponentGroupDO> componentGroupDOS = componentGroupMapper.selectList();
        componentGroupDOS.sort(Comparator.comparing(ComponentGroupDO::getNumSort));
        List<ComponentGroupRespVO> bean = BeanUtils.toBean(componentGroupDOS, ComponentGroupRespVO.class);

        // 创建映射
        Map<Long, ComponentGroupRespVO> map = new HashMap<>();
        for (ComponentGroupRespVO componentGroupDO : bean) {
            map.put(componentGroupDO.getId(), componentGroupDO);
        }

        // 存放根节点
        List<ComponentGroupRespVO> componentGroupRespVOS = new ArrayList<>();

        bean.forEach(componentGroupDO -> {
            if (map.containsKey(componentGroupDO.getParentId())) {
                if (map.get(componentGroupDO.getParentId()).getChildren() == null) {
                    map.get(componentGroupDO.getParentId()).setChildren(new ArrayList<>());
                }
                map.get(componentGroupDO.getParentId()).getChildren().add(componentGroupDO);
            } else {
                componentGroupRespVOS.add(componentGroupDO);
            }
        });
        componentGroupRespVOS.sort(Comparator.comparingInt(ComponentGroupRespVO::getNumSort).thenComparing(ComponentGroupRespVO::getCreateTime));
        return componentGroupRespVOS;
    }

}
