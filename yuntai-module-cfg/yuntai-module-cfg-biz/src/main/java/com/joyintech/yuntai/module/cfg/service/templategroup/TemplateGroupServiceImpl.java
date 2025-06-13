package com.joyintech.yuntai.module.cfg.service.templategroup;

import cn.hutool.core.collection.CollUtil;
import com.joyintech.yuntai.module.cfg.controller.admin.componentgroup.vo.ComponentGroupRespVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentgroup.ComponentGroupDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templateinfo.TemplateInfoDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.templateinfo.TemplateInfoMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.templategroup.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templategroup.TemplateGroupDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.templategroup.TemplateGroupMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 模版分组 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class TemplateGroupServiceImpl implements TemplateGroupService {

    @Resource
    private TemplateGroupMapper templateGroupMapper;
    @Resource
    private TemplateInfoMapper templateInfoMapper;

    @Override
    public Long createTemplateGroup(TemplateGroupSaveReqVO createReqVO) {
        // 插入
        TemplateGroupDO templateGroup = BeanUtils.toBean(createReqVO, TemplateGroupDO.class);
        templateGroupMapper.insert(templateGroup);
        // 返回
        return templateGroup.getId();
    }

    @Override
    public void updateTemplateGroup(TemplateGroupSaveReqVO updateReqVO) {
        // 校验存在
        validateTemplateGroupExists(updateReqVO.getId());
        // 更新
        TemplateGroupDO updateObj = BeanUtils.toBean(updateReqVO, TemplateGroupDO.class);
        templateGroupMapper.updateById(updateObj);
    }

    @Override
    public void deleteTemplateGroup(Long id) {
        // 校验存在
        validateTemplateGroupExists(id);
        if (CollUtil.isNotEmpty(templateInfoMapper.selectList(TemplateInfoDO::getGroupId, id)) || CollUtil.isNotEmpty(templateGroupMapper.selectList(TemplateGroupDO::getParentId, id))) {
            throw exception(TEMPLATE_GROUP_NOT_DELETE);
        }
        // 删除
        templateGroupMapper.deleteById(id);
    }

    private void validateTemplateGroupExists(Long id) {
        if (templateGroupMapper.selectById(id) == null) {
            throw exception(TEMPLATE_GROUP_NOT_EXISTS);
        }
    }

    @Override
    public TemplateGroupDO getTemplateGroup(Long id) {
        return templateGroupMapper.selectById(id);
    }

    @Override
    public PageResult<TemplateGroupDO> getTemplateGroupPage(TemplateGroupPageReqVO pageReqVO) {
        return templateGroupMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TemplateGroupRespVO> getTree() {
        List<TemplateGroupDO> templateGroupDO = templateGroupMapper.selectList();
        List<TemplateGroupRespVO> bean = BeanUtils.toBean(templateGroupDO, TemplateGroupRespVO.class);
        // 创建映射
        Map<Long, TemplateGroupRespVO> map = new HashMap<>();
        for (TemplateGroupRespVO groupDO : bean) {
            map.put(groupDO.getId(), groupDO);
        }
        // 存放根节点
        List<TemplateGroupRespVO> groupRespVOS = new ArrayList<>();
        bean.forEach(groupDO -> {
            if (map.containsKey(groupDO.getParentId())) {
                if (map.get(groupDO.getParentId()).getChildren() == null) {
                    map.get(groupDO.getParentId()).setChildren(new ArrayList<>());
                }
                map.get(groupDO.getParentId()).getChildren().add(groupDO);
            } else {
                groupRespVOS.add(groupDO);
            }
        });
        return groupRespVOS;
    }
}
