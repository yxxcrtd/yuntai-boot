package com.joyintech.yuntai.module.cfg.service.pagegroup;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.module.cfg.controller.admin.dataconversion.vo.DataConversionRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.dataformat.vo.DataFormatRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.eventconfig.vo.EventConfigRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo.PageLinkageRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.PageListConfigSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.validaterules.vo.ValidateRulesRespVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute.ColumnComponentAttributeDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componenttable.ComponentTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.conditionaltable.ConditionalTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataconversion.DataConversionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataformat.DataFormatDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.eventconfig.EventConfigDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelinkage.PageLinkageDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistconfig.PageListConfigDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.validaterules.ValidateRulesDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.columncomponentattribute.ColumnComponentAttributeMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.conditionaltable.ConditionalTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.dataconversion.DataConversionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.dataformat.DataFormatMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.eventconfig.EventConfigMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pagelinkage.PageLinkageMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pagelistconfig.PageListConfigMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.validaterules.ValidateRulesMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.joyintech.yuntai.module.cfg.controller.admin.pagegroup.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagegroup.PageGroupDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.pagegroup.PageGroupMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 页面分组 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class PageGroupServiceImpl implements PageGroupService {

    @Resource
    private PageGroupMapper pageGroupMapper;

    @Resource
    private PageListConfigMapper pageListConfigMapper;

    @Resource
    private ValidateRulesMapper validateRulesMapper;

    @Resource
    private PageLinkageMapper pageLinkageMapper;

    @Resource
    private EventConfigMapper eventConfigMapper;

    @Resource
    private DataConversionMapper dataConversionMapper;

    @Resource
    private DataFormatMapper dataFormatMapper;

    @Resource
    private ColumnComponentAttributeMapper columnComponentAttributeMapper;

    @Resource
    private ConditionalTableMapper conditionalTableMapper;

    @Override
    public Long createPageGroup(PageGroupSaveReqVO createReqVO) {
        // 插入
        PageGroupDO pageGroup = BeanUtils.toBean(createReqVO, PageGroupDO.class);
        pageGroupMapper.insert(pageGroup);
        // 返回
        return pageGroup.getId();
    }

    @Override
    public void updatePageGroup(PageGroupSaveReqVO updateReqVO) {
        // 校验存在
        validatePageGroupExists(updateReqVO.getId());
        // 更新
        PageGroupDO updateObj = BeanUtils.toBean(updateReqVO, PageGroupDO.class);
        pageGroupMapper.updateById(updateObj);
    }

    @Override
    public void deletePageGroup(Long id) {
        // 校验存在
        validatePageGroupExists(id);
        // 删除
        pageGroupMapper.deleteById(id);
    }

    private void validatePageGroupExists(Long id) {
        if (pageGroupMapper.selectById(id) == null) {
            throw exception(PAGE_GROUP_NOT_EXISTS);
        }
    }

    @Override
    public PageGroupDO getPageGroup(Long id) {
        return pageGroupMapper.selectById(id);
    }

    @Override
    public PageResult<PageGroupDO> getPageGroupPage(PageGroupPageReqVO pageReqVO) {
        return pageGroupMapper.selectPage(pageReqVO);
    }

    public void addGroupList(List<PageGroupSaveReqVO> pageGroups, Map<String, ComponentTableDO> componentTableDOMap) {
        // 新增分组
        List<PageGroupDO> bean = BeanUtils.toBean(pageGroups, PageGroupDO.class);
        pageGroupMapper.insertOrUpdateBatch(bean);
//        Map<String, PageGroupDO> map = bean.stream().collect(Collectors.toMap(s -> s.getPageApiId()+s.getGroupName(), Function.identity()));
        List<PageListConfigSaveReqVO> pageListConfigSaveReqVOS = new ArrayList<>();
        pageGroups.forEach(s -> {
            if (s.getPageListConfigs() != null) {
                s.getPageListConfigs().forEach(p -> {
                    p.setPageId(s.getPageId());
                    p.setColumnGroupId(s.getId());
                    p.setPageApiId(s.getPageApiId());
                    if (CollectionUtil.isNotEmpty(p.getColumnTag())) {
                        p.setColumnTagCode(String.join(",", p.getColumnTag()));
                    }
                    if (p.getColumnDisplayComponent() != null) {
                        p.setColumnDisplayComponentName(componentTableDOMap.get(p.getColumnDisplayComponent()).getComponentName());
                    } else {
                        p.setColumnDisplayComponentName(null);
                    }
                    pageListConfigSaveReqVOS.add(p);
                });
            }

            // 表单页配置>>表单项配置，分组里面的显隐规则，也改成和联动配置那边一样的方式了，一变多
            if(s.getShowConfig()!=null && !s.getShowConfig().isEmpty()){
                List<ConditionalTableDO> conditionalTableDOList =new ArrayList<>();
                s.getShowConfig().forEach(c -> {
                    ConditionalTableDO bean2 = BeanUtils.toBean(c, ConditionalTableDO.class);
                    bean2.setRelevanceId(s.getId());
                    bean2.setContent(c.getCondition() != null ? JSON.toJSONString(c.getCondition()) : null);
                    conditionalTableDOList.add(bean2);
                });
                if (!conditionalTableDOList.isEmpty()) {
                    conditionalTableMapper.insertOrUpdateBatch(conditionalTableDOList);
                    // 删除没有的条件
                    conditionalTableMapper.delete(new QueryWrapper<ConditionalTableDO>()
                            .eq("relevance_id", s.getId())
                            .notIn("id", conditionalTableDOList.stream().map(ConditionalTableDO::getId).collect(Collectors.toList()))
                    );
                }
            }

        });

        // 删除没有的分组
        if (!bean.isEmpty()) {
            List<Long> list = bean.stream().map(PageGroupDO::getId).collect(Collectors.toList());
            pageGroupMapper.delete(new QueryWrapper<PageGroupDO>()
                    .eq("page_id", bean.get(0).getPageId())
                    .notIn("id", list)
            );
        }

        // 新增列表配置
        if (!pageListConfigSaveReqVOS.isEmpty()) {
            List<PageListConfigDO> pageListConfigDOS = BeanUtils.toBean(pageListConfigSaveReqVOS, PageListConfigDO.class);
            pageListConfigMapper.insertOrUpdateBatch(pageListConfigDOS);

            // 删除没有的列表配置
            List<Long> longs = pageListConfigDOS.stream().map(PageListConfigDO::getId).collect(Collectors.toList());
            pageListConfigMapper.delete(new QueryWrapper<PageListConfigDO>()
                    .eq("page_id", pageListConfigDOS.get(0).getPageId())
//                    .eq("column_group_id", pageListConfigDOS.get(0).getColumnGroupId())
                    .notIn("id", longs)
            );

            // 列表配置转双键Map
            Map<Map.Entry<String, String>, PageListConfigDO> collect = pageListConfigDOS.stream().collect(Collectors.toMap(
                    doConfig -> new AbstractMap.SimpleEntry<>(doConfig.getGroupCode(), doConfig.getColumnName()),
                    Function.identity()
            ));

            List<ValidateRulesRespVO> validateRulesRespVOS =new ArrayList<>();
            List<PageLinkageRespVO> pageLinkageRespVOS =new ArrayList<>();
            List<EventConfigRespVO> eventConfigRespVOS =new ArrayList<>();
            List<DataConversionRespVO> dataConversionRespVOS =new ArrayList<>();
            List<Long> longList1 = new ArrayList<>();
            List<DataFormatRespVO> dataFormatRespVOS =new ArrayList<>();
            List<Long> longList2 = new ArrayList<>();
            List<ColumnComponentAttributeDO> componentAttributeDOS = new ArrayList<>();
            List<Long> longList = new ArrayList<>();
            pageListConfigSaveReqVOS.forEach(s -> {
                // 新增校验规则
                if (s.getValidateRulesRespVOS() != null) {
                    s.getValidateRulesRespVOS().forEach(v -> {
                        v.setPageConfigId(collect.get(new AbstractMap.SimpleEntry<>(s.getGroupCode(), s.getColumnName())).getId());
                        validateRulesRespVOS.add(v);
                    });
                }

                // 新增联动配置
                if (s.getPageLinkageRespVOS() != null) {
                    s.getPageLinkageRespVOS().forEach(v -> {
                        v.setPageConfigId(collect.get(new AbstractMap.SimpleEntry<>(s.getGroupCode(), s.getColumnName())).getId());
                        pageLinkageRespVOS.add(v);
                    });
                }

                // 新增事件配置
                if (s.getEventConfigRespVOS() != null) {
                    s.getEventConfigRespVOS().forEach(v -> {
                        v.setPageConfigId(collect.get(new AbstractMap.SimpleEntry<>(s.getGroupCode(), s.getColumnName())).getId());
                        eventConfigRespVOS.add(v);
                    });
                }

                // 新增数据转换
                if (s.getDataConversionRespVOS() != null) {
                    s.getDataConversionRespVOS().setListConfigId(collect.get(new AbstractMap.SimpleEntry<>(s.getGroupCode(), s.getColumnName())).getId());
                    dataConversionRespVOS.add(s.getDataConversionRespVOS());
                } else {
                    longList1.add(collect.get(new AbstractMap.SimpleEntry<>(s.getGroupCode(), s.getColumnName())).getId());
                }

                // 新增数据格式化
                if (s.getDataFormatRespVOS() != null) {
                    s.getDataFormatRespVOS().setListConfigId(collect.get(new AbstractMap.SimpleEntry<>(s.getGroupCode(), s.getColumnName())).getId());
                    dataFormatRespVOS.add(s.getDataFormatRespVOS());
                } else {
                    longList2.add(collect.get(new AbstractMap.SimpleEntry<>(s.getGroupCode(), s.getColumnName())).getId());
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

            if (!validateRulesRespVOS.isEmpty()) {
                List<ValidateRulesDO> bean1 = BeanUtils.toBean(validateRulesRespVOS, ValidateRulesDO.class);
                validateRulesMapper.insertOrUpdateBatch(bean1);

                // 删除没有的校验规则
                List<Long> longs1 = bean1.stream().map(ValidateRulesDO::getId).collect(Collectors.toList());
                validateRulesMapper.delete(new QueryWrapper<ValidateRulesDO>()
                        .eq("page_config_id", bean1.get(0).getPageConfigId())
                        .notIn("id", longs1)
                );
            }

            if (!pageLinkageRespVOS.isEmpty()) {
                List<PageLinkageDO> bean1 = BeanUtils.toBean(pageLinkageRespVOS, PageLinkageDO.class);
                pageLinkageMapper.insertOrUpdateBatch(bean1);

                // 删除没有的联动配置
                List<Long> longs1 = bean1.stream().map(PageLinkageDO::getId).collect(Collectors.toList());
                pageLinkageMapper.delete(new QueryWrapper<PageLinkageDO>()
                        .eq("page_config_id", bean1.get(0).getPageConfigId())
                        .notIn("id", longs1)
                );
            }

            if (!eventConfigRespVOS.isEmpty()) {
                List<EventConfigDO> bean1 = BeanUtils.toBean(eventConfigRespVOS, EventConfigDO.class);
                eventConfigMapper.insertOrUpdateBatch(bean1);

                // 删除没有的事件配置
                List<Long> longs1 = bean1.stream().map(EventConfigDO::getId).collect(Collectors.toList());
                eventConfigMapper.delete(new QueryWrapper<EventConfigDO>()
                        .eq("page_config_id", bean1.get(0).getPageConfigId())
                        .notIn("id", longs1)
                );
            }

            if (!dataConversionRespVOS.isEmpty()) {
                // 新增数据转换规则
                List<DataConversionDO> bean1 = BeanUtils.toBean(dataConversionRespVOS, DataConversionDO.class);
                dataConversionMapper.insertOrUpdateBatch(bean1);
            }

            if (!longList1.isEmpty()) {
                // 删除没有的数据转换规则
                dataConversionMapper.delete(new QueryWrapper<DataConversionDO>()
                        .eq("list_config_id", longList1)
                );
            }

            if (!dataFormatRespVOS.isEmpty()) {
                List<DataFormatDO> bean1 = BeanUtils.toBean(dataFormatRespVOS, DataFormatDO.class);
                dataFormatMapper.insertOrUpdateBatch(bean1);
            }

            if (!longList2.isEmpty()) {
                // 删除没有的数据格式化
                dataFormatMapper.delete(new QueryWrapper<DataFormatDO>()
                        .eq("list_config_id", longList2)
                );
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
}