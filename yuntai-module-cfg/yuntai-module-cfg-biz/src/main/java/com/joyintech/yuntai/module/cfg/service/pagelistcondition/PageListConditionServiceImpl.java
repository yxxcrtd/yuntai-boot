package com.joyintech.yuntai.module.cfg.service.pagelistcondition;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.ConditionalTableSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo.PageLinkageRespVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute.ColumnComponentAttributeDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.conditionaltable.ConditionalTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelinkage.PageLinkageDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.columncomponentattribute.ColumnComponentAttributeMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.conditionaltable.ConditionalTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pagelinkage.PageLinkageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.joyintech.yuntai.module.cfg.controller.admin.pagelistcondition.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistcondition.PageListConditionDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.pagelistcondition.PageListConditionMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 表单页查询条件（待定） Service 实现类
 *
 * @author 兆尹云台
 */
@Slf4j
@Service
@Validated
public class PageListConditionServiceImpl implements PageListConditionService {

    @Resource
    private PageListConditionMapper pageListConditionMapper;

    @Resource
    private ConditionalTableMapper conditionalTableMapper;

    @Resource
    private PageLinkageMapper pageLinkageMapper;

    @Resource
    private ColumnComponentAttributeMapper columnComponentAttributeMapper;

    @Override
    public Long createPageListCondition(PageListConditionSaveReqVO createReqVO) {
        // 插入
        PageListConditionDO pageListCondition = BeanUtils.toBean(createReqVO, PageListConditionDO.class);
        pageListConditionMapper.insert(pageListCondition);
        // 返回
        return pageListCondition.getId();
    }

    @Override
    public void updatePageListCondition(PageListConditionSaveReqVO updateReqVO) {
        // 校验存在
        validatePageListConditionExists(updateReqVO.getId());
        // 更新
        PageListConditionDO updateObj = BeanUtils.toBean(updateReqVO, PageListConditionDO.class);
        pageListConditionMapper.updateById(updateObj);
    }

    @Override
    public void deletePageListCondition(Long id) {
        // 校验存在
        validatePageListConditionExists(id);
        // 删除
        pageListConditionMapper.deleteById(id);
    }

    private void validatePageListConditionExists(Long id) {
        if (pageListConditionMapper.selectById(id) == null) {
            throw exception(PAGE_LIST_CONDITION_NOT_EXISTS);
        }
    }

    @Override
    public PageListConditionDO getPageListCondition(Long id) {
        return pageListConditionMapper.selectById(id);
    }

    @Override
    public PageResult<PageListConditionDO> getPageListConditionPage(PageListConditionPageReqVO pageReqVO) {
        return pageListConditionMapper.selectPage(pageReqVO);
    }

    public void addPageListCondition(List<PageListConditionSaveReqVO> pageListConditionSaveReqVOS) {
        List<PageListConditionDO> pageListConditionDOS = BeanUtils.toBean(pageListConditionSaveReqVOS, PageListConditionDO.class);
        pageListConditionMapper.insertOrUpdateBatch(pageListConditionDOS);

        // 删除没有的查询条件
        if (!pageListConditionDOS.isEmpty()) {
            List<Long> list = pageListConditionDOS.stream().map(PageListConditionDO::getId).collect(Collectors.toList());
            pageListConditionMapper.delete(new QueryWrapper<PageListConditionDO>()
                    .eq("page_id", pageListConditionDOS.get(0).getPageId())
                    .notIn("id", list)
            );
        }
        Map<Long, PageListConditionDO> collect = pageListConditionDOS.stream().collect(Collectors.toMap(s -> s.getFieldId() + s.getTableId(), Function.identity()));
        Set<Long> set = pageListConditionDOS.stream().map(PageListConditionDO::getId).collect(Collectors.toSet());

        List<PageLinkageRespVO> pageLinkageRespVOS =new ArrayList<>();
        List<ColumnComponentAttributeDO> componentAttributeDOS = new ArrayList<>();
        // 组件属性的删除ID
        List<Long> longList = new ArrayList<>();
        pageListConditionSaveReqVOS.forEach(s -> {
            // 新增联动配置
            if (CollectionUtil.isNotEmpty(s.getPageLinkageRespVOS())) {
                s.getPageLinkageRespVOS().forEach(v -> {
                    v.setPageConfigId(collect.get(s.getFieldId() + s.getTableId()).getId());
                    pageLinkageRespVOS.add(v);
                });
            }

            // 新增组件属性
            if (CollectionUtil.isNotEmpty(s.getColumnComponentAttributeDO())) {
                s.getColumnComponentAttributeDO().forEach(v -> {
                    v.setPageId(s.getPageId());
                    v.setColumnId(collect.get(s.getFieldId() + s.getTableId()).getId());
                    Long id = v.getId();
                    v.setId(null);
                    v.setAttributeId(id);
                    componentAttributeDOS.add(v);
                });
            }
        });
        if (!pageLinkageRespVOS.isEmpty()) {
            List<PageLinkageDO> bean1 = BeanUtils.toBean(pageLinkageRespVOS, PageLinkageDO.class);
            pageLinkageMapper.insertOrUpdateBatch(bean1);

            // 删除没有的联动配置
            List<Long> longs1 = bean1.stream().map(PageLinkageDO::getId).collect(Collectors.toList());
            pageLinkageMapper.delete(new QueryWrapper<PageLinkageDO>()
                    .in("page_config_id", set)
                    .notIn("id", longs1)
            );

            // 新增条件内容
            Map<String, PageLinkageDO> map = bean1.stream().collect(Collectors.toMap(s-> s.getPageConfigId() + s.getLinkageName(),Function.identity()));
            List<ConditionalTableDO> conditionalTableDOList =new ArrayList<>();
            pageLinkageRespVOS.forEach(v -> {
                if(v.getSceneList()!=null && !v.getSceneList().isEmpty()){
                    for(ConditionalTableSaveReqVO scene : v.getSceneList()){
                        ConditionalTableDO bean2 = BeanUtils.toBean(scene, ConditionalTableDO.class);
                        bean2.setRelevanceId(map.get(v.getPageConfigId() + v.getLinkageName()).getId());
                        bean2.setContent(scene.getCondition() != null ? JSON.toJSONString(scene.getCondition()) : null);
                        conditionalTableDOList.add(bean2);
                    }
                }
            });
            if (!conditionalTableDOList.isEmpty()) {
                conditionalTableMapper.insertOrUpdateBatch(conditionalTableDOList);

                // 删除没有的条件
                conditionalTableMapper.delete(new QueryWrapper<ConditionalTableDO>()
                        .eq("relevance_id", conditionalTableDOList.get(0).getRelevanceId())
                        .notIn("id", conditionalTableDOList.stream().map(ConditionalTableDO::getId).collect(Collectors.toList()))
                );
            }
        }else {
            if(CollUtil.isNotEmpty(set)){
                pageLinkageMapper.delete(new QueryWrapper<PageLinkageDO>().in("page_config_id", set));
            }
        }

        if (!componentAttributeDOS.isEmpty()) {
            // 新增组件属性
            columnComponentAttributeMapper.insertOrUpdateBatch(componentAttributeDOS);

            // 删除没有的联动配置
            List<Long> longs1 = componentAttributeDOS.stream().map(ColumnComponentAttributeDO::getId).collect(Collectors.toList());
            columnComponentAttributeMapper.delete(new QueryWrapper<ColumnComponentAttributeDO>()
                    .in("column_id", componentAttributeDOS.stream().map(ColumnComponentAttributeDO::getColumnId).collect(Collectors.toList()))
                    .notIn("id", longs1)
            );
        }
    }
}
