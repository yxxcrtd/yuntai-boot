package com.joyintech.yuntai.module.cfg.service.pagelistconfig;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.ConditionalTableSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.dataconversion.vo.DataConversionRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.dataformat.vo.DataFormatRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.eventconfig.vo.EventConfigRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo.PageLinkageRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.validaterules.vo.ValidateRulesRespVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute.ColumnComponentAttributeDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition.ColumnDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.conditionaltable.ConditionalTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataconversion.DataConversionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataformat.DataFormatDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.eventconfig.EventConfigDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelinkage.PageLinkageDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.validaterules.ValidateRulesDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.columncomponentattribute.ColumnComponentAttributeMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.columndefinition.ColumnDefinitionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.conditionaltable.ConditionalTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.dataconversion.DataConversionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.dataformat.DataFormatMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.eventconfig.EventConfigMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pagelinkage.PageLinkageMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.validaterules.ValidateRulesMapper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistconfig.PageListConfigDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.pagelistconfig.PageListConfigMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 列表页配置 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class PageListConfigServiceImpl implements PageListConfigService {

    @Resource
    private PageListConfigMapper pageListConfigMapper;

    @Resource
    private DataConversionMapper dataConversionMapper;

    @Resource
    private DataFormatMapper dataFormatMapper;

    @Resource
    private ColumnComponentAttributeMapper columnComponentAttributeMapper;

    @Resource
    private ValidateRulesMapper validateRulesMapper;

    @Resource
    private PageLinkageMapper pageLinkageMapper;

    @Resource
    private EventConfigMapper eventConfigMapper;

    @Resource
    private ConditionalTableMapper conditionalTableMapper;

    @Resource
    private ColumnDefinitionMapper columnDefinitionMapper;

    @Override
    public Long createPageListConfig(PageListConfigSaveReqVO createReqVO) {
        // 插入
        PageListConfigDO pageListConfig = BeanUtils.toBean(createReqVO, PageListConfigDO.class);
        pageListConfigMapper.insert(pageListConfig);
        // 返回
        return pageListConfig.getId();
    }

    @Override
    public void updatePageListConfig(PageListConfigSaveReqVO updateReqVO) {
        // 校验存在
        validatePageListConfigExists(updateReqVO.getId());
        // 更新
        PageListConfigDO updateObj = BeanUtils.toBean(updateReqVO, PageListConfigDO.class);
        pageListConfigMapper.updateById(updateObj);
    }

    @Override
    public void deletePageListConfig(Long id) {
        // 校验存在
        validatePageListConfigExists(id);
        // 删除
        pageListConfigMapper.deleteById(id);
    }

    private void validatePageListConfigExists(Long id) {
        if (pageListConfigMapper.selectById(id) == null) {
            throw exception(PAGE_LIST_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public PageListConfigDO getPageListConfig(Long id) {
        return pageListConfigMapper.selectById(id);
    }

    @Override
    public PageResult<PageListConfigDO> getPageListConfigPage(PageListConfigPageReqVO pageReqVO) {
        return pageListConfigMapper.selectPage(pageReqVO);
    }

    public void addPageListConfig(List<PageListConfigSaveReqVO> pageListConfigSaveReqVOS) {
        if(!pageListConfigSaveReqVOS.isEmpty()){
            for(PageListConfigSaveReqVO vo : pageListConfigSaveReqVOS){
                if(vo.getTableId()==null || vo.getFieldId()==null){
                    ColumnDefinitionDO column = columnDefinitionMapper.findColumnByModuleTableId(vo.getModuleTableId(), vo.getColumnName());
                    if(column!=null){
                        vo.setTableId(column.getTableId());
                        vo.setFieldId(column.getId());
                    }
                }
            }
        }

        // 插入模型列表字段
        List<PageListConfigDO> bean = BeanUtils.toBean(pageListConfigSaveReqVOS, PageListConfigDO.class);
        pageListConfigMapper.insertOrUpdateBatch(bean);

        // 删除没有的模型列表字段
        if (!bean.isEmpty()) {
            List<Long> list = bean.stream().map(PageListConfigDO::getId).collect(Collectors.toList());
            if(list.size()>=1000){
                List<PageListConfigDO> allList = pageListConfigMapper.selectList(new QueryWrapper<PageListConfigDO>()
                        .eq("page_id", bean.get(0).getPageId()));
                List<Long> allIdList = allList.stream().map(PageListConfigDO::getId).collect(Collectors.toList());
                Set<Long> set = new HashSet<>(list);
                allIdList.removeIf(set::contains);
                if(!allIdList.isEmpty()){
                    pageListConfigMapper.deleteByIds(allIdList);
                }
            }else{
                pageListConfigMapper.delete(new QueryWrapper<PageListConfigDO>()
                        .eq("page_id", bean.get(0).getPageId())
                        .notIn("id", list)
                );
            }
        }

        Map<String, PageListConfigDO> collect = bean.stream().collect(Collectors.toMap(s -> s.getApiCode() + s.getColumnName() + s.getModuleTableId() + s.getFieldId(), Function.identity(), (existing, replacement) -> existing));
        Set<Long> set = bean.stream().map(PageListConfigDO::getId).collect(Collectors.toSet());

        List<ValidateRulesRespVO> validateRulesRespVOS =new ArrayList<>();
        List<PageLinkageRespVO> pageLinkageRespVOS =new ArrayList<>();
        List<EventConfigRespVO> eventConfigRespVOS =new ArrayList<>();
        List<DataConversionRespVO> dataConversionRespVOS =new ArrayList<>();
        // 数据转换规则的删除ID
        List<Long> longList1 = new ArrayList<>();
        List<DataFormatRespVO> dataFormatRespVOS =new ArrayList<>();
        // 数据格式化的删除ID
        List<Long> longList2 = new ArrayList<>();
        List<ColumnComponentAttributeDO> componentAttributeDOS = new ArrayList<>();
        // 组件属性的删除ID
        pageListConfigSaveReqVOS.forEach(s -> {
            // 新增校验规则
            if (CollectionUtil.isNotEmpty(s.getValidateRulesRespVOS())) {
                s.getValidateRulesRespVOS().forEach(v -> {
                    v.setPageConfigId(collect.get(s.getApiCode() + s.getColumnName() + s.getModuleTableId() + s.getFieldId()).getId());
                    v.setRulesName(JSON.toJSONString(v.getCondition()));
                    validateRulesRespVOS.add(v);
                });
            }

            // 新增联动配置
            if (CollectionUtil.isNotEmpty(s.getPageLinkageRespVOS())) {
                s.getPageLinkageRespVOS().forEach(v -> {
                    v.setPageConfigId(collect.get(s.getApiCode() + s.getColumnName() + s.getModuleTableId() + s.getFieldId()).getId());
                    v.setPageId(s.getPageId());
                    pageLinkageRespVOS.add(v);
                });
            }

            // 新增事件配置
            if (CollectionUtil.isNotEmpty(s.getEventConfigRespVOS())) {
                s.getEventConfigRespVOS().forEach(v -> {
                    v.setPageConfigId(collect.get(s.getApiCode() + s.getColumnName() + s.getModuleTableId() + s.getFieldId()).getId());
                    eventConfigRespVOS.add(v);
                });
            }

            // 新增数据转换
            if (s.getDataConversionRespVOS() != null) {
                s.getDataConversionRespVOS().setListConfigId(collect.get(s.getApiCode() + s.getColumnName() + s.getModuleTableId() + s.getFieldId()).getId());
                dataConversionRespVOS.add(s.getDataConversionRespVOS());
            } else {
                longList1.add(collect.get(s.getApiCode() + s.getColumnName() + s.getModuleTableId() + s.getFieldId()).getId());
            }

            // 新增数据格式化
            if (s.getDataFormatRespVOS() != null) {
                s.getDataFormatRespVOS().setListConfigId(collect.get(s.getApiCode() + s.getColumnName() + s.getModuleTableId() + s.getFieldId()).getId());
                dataFormatRespVOS.add(s.getDataFormatRespVOS());
            } else {
                longList2.add(collect.get(s.getApiCode() + s.getColumnName() + s.getModuleTableId() + s.getFieldId()).getId());
            }

            // 新增组件属性
            if (CollectionUtil.isNotEmpty(s.getColumnComponentAttributeDO())) {
                s.getColumnComponentAttributeDO().forEach(v -> {
                    v.setPageId(s.getPageId());
                    v.setColumnId(collect.get(s.getApiCode() + s.getColumnName() + s.getModuleTableId() + s.getFieldId()).getId());
                    /*Long id = v.getId();
                    v.setId(null);
                    v.setAttributeId(id);*/
                    componentAttributeDOS.add(v);
                });
            }

        });
        if (!validateRulesRespVOS.isEmpty()) {
            List<ValidateRulesDO> bean1 = BeanUtils.toBean(validateRulesRespVOS, ValidateRulesDO.class);
            validateRulesMapper.insertOrUpdateBatch(bean1);

            // 删除没有的校验规则
            List<Long> longs1 = bean1.stream().map(ValidateRulesDO::getId).collect(Collectors.toList());
            if (set.size() >= 1000) {
                Set<Long> tempSet = new HashSet<>();
                for (Long id : set) {
                    tempSet.add(id);
                    if (tempSet.size() >= 999) {
                        validateRulesMapper.delete(new QueryWrapper<ValidateRulesDO>()
                                .in("page_config_id", tempSet)
                                .notIn("id", longs1)
                        );
                        tempSet.clear();
                    }
                }
                if (!tempSet.isEmpty()) {
                    validateRulesMapper.delete(new QueryWrapper<ValidateRulesDO>()
                            .in("page_config_id", tempSet)
                            .notIn("id", longs1)
                    );
                    tempSet.clear();
                }
            }else{
                validateRulesMapper.delete(new QueryWrapper<ValidateRulesDO>()
                        .in("page_config_id", set)
                        .notIn("id", longs1));
            }
        }else {
            if(CollUtil.isNotEmpty(set)) {
                if(set.size()>=1000){
                    Set<Long> tempSet = new HashSet<>();
                    for(Long id : set){
                        tempSet.add(id);
                        if(tempSet.size()>=999){
                            validateRulesMapper.delete(new QueryWrapper<ValidateRulesDO>().in("page_config_id", tempSet));
                            tempSet.clear();
                        }
                    }
                    if(!tempSet.isEmpty()){
                        validateRulesMapper.delete(new QueryWrapper<ValidateRulesDO>().in("page_config_id", tempSet));
                        tempSet.clear();
                    }
                }else{
                    validateRulesMapper.delete(new QueryWrapper<ValidateRulesDO>().in("page_config_id", set));
                }
            }
        }
        if (!pageLinkageRespVOS.isEmpty()) {
            List<PageLinkageDO> bean1 = BeanUtils.toBean(pageLinkageRespVOS, PageLinkageDO.class);
            pageLinkageMapper.insertOrUpdateBatch(bean1);

            // 删除没有的联动配置
            List<Long> longs1 = bean1.stream().map(PageLinkageDO::getId).collect(Collectors.toList());
            if (set.size() > 1000) {
                List<List<Long>> partitionList = Lists.partition(new ArrayList<>(set), 900);
                QueryWrapper<PageLinkageDO> queryWrapper = new QueryWrapper<>();
                queryWrapper.notIn("id", longs1);
                queryWrapper.and(x -> {
                    for (List<Long> longs : partitionList) {
                        x.or(y -> y.in("page_config_id", longs));
                    }
                });
                pageLinkageMapper.delete(queryWrapper);
            } else {
                pageLinkageMapper.delete(new QueryWrapper<PageLinkageDO>()
                        .in("page_config_id", set)
                        .notIn("id", longs1)
                );
            }

            // 新增条件内容
            Map<String, PageLinkageDO> map = bean1.stream().collect(Collectors.toMap(s-> s.getPageConfigId() + s.getLinkageName(),Function.identity()));
            List<ConditionalTableDO> conditionalTableDOList =new ArrayList<>();
            List<ColumnComponentAttributeDO> columnComponentAttributeDOList =new ArrayList<>();
            pageLinkageRespVOS.forEach(v -> {
                if(v.getSceneList()!=null && !v.getSceneList().isEmpty()){
                    for(ConditionalTableSaveReqVO scene : v.getSceneList()){
                        ConditionalTableDO bean2 = BeanUtils.toBean(scene, ConditionalTableDO.class);
                        bean2.setRelevanceId(map.get(v.getPageConfigId() + v.getLinkageName()).getId());
                        bean2.setContent(scene.getCondition() != null ? JSON.toJSONString(scene.getCondition()) : null);

                        // 因为【表单页>>表单项配置>>显示组件】也有属性配置 ，ColumnComponentAttributeDO的columnId，存cfg_page_list_config表的id值
                        // 所以这里的【表单页>>表单项配置>>联动配置>>切换输入类型>>切换为】的属性配置，ColumnComponentAttributeDO的LinkageId，存cfg_conditional_table表的id，只挂在cfg_conditional_table表下面
                        // 相当于一个明细表，有俩个主表，都往里面存数。
                        // 关联影响：【页面管理>>更多>>复制移动端页面】功能，copy主表、明细表的数据时，明细表的columnId、LinkageId，要替换成对应主表新的id值
                        if(bean2.getColumnComponentAttributeDO()!=null && !bean2.getColumnComponentAttributeDO().isEmpty()){
                            List<ColumnComponentAttributeDO> list = bean2.getColumnComponentAttributeDO();
                            for(ColumnComponentAttributeDO vo : list){
                                vo.setPageId(v.getPageId());
                            }
                        }

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
                for(ConditionalTableDO bean2 : conditionalTableDOList){
                    // 因为【表单页>>表单项配置>>显示组件】也有属性配置 ，ColumnComponentAttributeDO的columnId，存cfg_page_list_config表的id值
                    // 所以这里的【表单页>>表单项配置>>联动配置>>切换输入类型>>切换为】的属性配置，ColumnComponentAttributeDO的LinkageId，存cfg_conditional_table表的id，只挂在cfg_conditional_table表下面
                    // 相当于一个明细表，有俩个主表，都往里面存数。
                    // 关联影响：【页面管理>>更多>>复制移动端页面】功能，copy主表、明细表的数据时，明细表的columnId、LinkageId，要替换成对应主表新的id值
                    if(bean2.getColumnComponentAttributeDO()!=null && !bean2.getColumnComponentAttributeDO().isEmpty()){
                        List<ColumnComponentAttributeDO> list = bean2.getColumnComponentAttributeDO();
                        for(ColumnComponentAttributeDO vo : list){
                            vo.setLinkageId(bean2.getId());
                        }
                        columnComponentAttributeDOList.addAll(list);
                    }
                }
            }
            if(!columnComponentAttributeDOList.isEmpty()){
                // 新增组件属性
                columnComponentAttributeMapper.insertOrUpdateBatch(columnComponentAttributeDOList);
                // 删除没有的联动配置
                List<Long> ids = columnComponentAttributeDOList.stream().map(ColumnComponentAttributeDO::getId).collect(Collectors.toList());
                columnComponentAttributeMapper.delete(new QueryWrapper<ColumnComponentAttributeDO>()
                        .in("linkage_id", columnComponentAttributeDOList.stream().map(ColumnComponentAttributeDO::getLinkageId).collect(Collectors.toList()))
                        .notIn("id", ids)
                );
            }else{
                columnComponentAttributeMapper.delete(new QueryWrapper<ColumnComponentAttributeDO>()
                        .in("linkage_id", pageLinkageRespVOS.stream().map(PageLinkageRespVO::getId).collect(Collectors.toList())));
            }
        }else {
            if(CollUtil.isNotEmpty(set)) {
                if(set.size()>=1000){
                    Set<Long> tempSet = new HashSet<>();
                    for(Long id : set){
                        tempSet.add(id);
                        if(tempSet.size()>=999){
                            pageLinkageMapper.delete(new QueryWrapper<PageLinkageDO>().in("page_config_id", tempSet));
                            tempSet.clear();
                        }
                    }
                    if(!tempSet.isEmpty()){
                        pageLinkageMapper.delete(new QueryWrapper<PageLinkageDO>().in("page_config_id", tempSet));
                        tempSet.clear();
                    }
                }else{
                    pageLinkageMapper.delete(new QueryWrapper<PageLinkageDO>().in("page_config_id", set));
                }
            }
        }
        if (!eventConfigRespVOS.isEmpty()) {
            List<EventConfigDO> bean1 = BeanUtils.toBean(eventConfigRespVOS, EventConfigDO.class);
            eventConfigMapper.insertOrUpdateBatch(bean1);

            // 删除没有的事件配置
            List<Long> longs1 = bean1.stream().map(EventConfigDO::getId).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(set)) {
                if(set.size()>=1000){
                    Set<Long> tempSet = new HashSet<>();
                    for(Long id : set){
                        tempSet.add(id);
                        if(tempSet.size()>=999){
                            eventConfigMapper.delete(new QueryWrapper<EventConfigDO>()
                                    .in("page_config_id", tempSet)
                                    .notIn("id", longs1)
                            );
                            tempSet.clear();
                        }
                    }
                    if(!tempSet.isEmpty()){
                        eventConfigMapper.delete(new QueryWrapper<EventConfigDO>()
                                .in("page_config_id", tempSet)
                                .notIn("id", longs1)
                        );
                        tempSet.clear();
                    }
                }else{
                    eventConfigMapper.delete(new QueryWrapper<EventConfigDO>()
                            .in("page_config_id", set)
                            .notIn("id", longs1)
                    );
                }
            }
        }else {
            if(CollUtil.isNotEmpty(set)) {
                if(set.size()>=1000){
                    Set<Long> tempSet = new HashSet<>();
                    for(Long id : set){
                        tempSet.add(id);
                        if(tempSet.size()>=999){
                            eventConfigMapper.delete(new QueryWrapper<EventConfigDO>().in("page_config_id", tempSet));
                            tempSet.clear();
                        }
                    }
                    if(!tempSet.isEmpty()){
                        eventConfigMapper.delete(new QueryWrapper<EventConfigDO>().in("page_config_id", tempSet));
                        tempSet.clear();
                    }
                }else{
                    eventConfigMapper.delete(new QueryWrapper<EventConfigDO>().in("page_config_id", set));
                }
            }
        }

        if (!dataConversionRespVOS.isEmpty()) {
            // 新增数据转换规则
            List<DataConversionDO> bean1 = BeanUtils.toBean(dataConversionRespVOS, DataConversionDO.class);
            dataConversionMapper.insertOrUpdateBatch(bean1);
        }

        if (!longList1.isEmpty()) {
            // 删除没有的数据转换规则
            if(longList1.size()>=1000){
                List<Long> tempList = new ArrayList<>();
                for(Long id : longList1){
                    tempList.add(id);
                    if(tempList.size()>=999){
                        dataConversionMapper.delete(new QueryWrapper<DataConversionDO>()
                                .in("list_config_id", tempList)
                        );
                        tempList.clear();
                    }
                }
                if(!tempList.isEmpty()){
                    dataConversionMapper.delete(new QueryWrapper<DataConversionDO>()
                            .in("list_config_id", tempList)
                    );
                    tempList.clear();
                }
            }else{
                dataConversionMapper.delete(new QueryWrapper<DataConversionDO>()
                        .in("list_config_id", longList1)
                );
            }
        }

        if (!dataFormatRespVOS.isEmpty()) {
            List<DataFormatDO> bean1 = BeanUtils.toBean(dataFormatRespVOS, DataFormatDO.class);
            dataFormatMapper.insertOrUpdateBatch(bean1);
        }

        if (!longList2.isEmpty()) {
            // 删除没有的数据格式化
            if(longList2.size()>=1000){
                List<Long> tempList = new ArrayList<>();
                for(Long id : longList2){
                    tempList.add(id);
                    if(tempList.size()>=999){
                        dataFormatMapper.delete(new QueryWrapper<DataFormatDO>()
                                .in("list_config_id", tempList)
                        );
                        tempList.clear();
                    }
                }
                if(!tempList.isEmpty()){
                    dataFormatMapper.delete(new QueryWrapper<DataFormatDO>()
                            .in("list_config_id", tempList)
                    );
                    tempList.clear();
                }
            }else{
                dataFormatMapper.delete(new QueryWrapper<DataFormatDO>()
                        .in("list_config_id", longList2)
                );
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
            columnComponentAttributeMapper.delete(new QueryWrapper<ColumnComponentAttributeDO>()
                    .eq("page_id", bean.get(0).getPageId())
                    .isNotNull("column_id")
                    .notIn("id", longs1)
            );
        }else {
            List<Long> ids = componentAttributeDOS.stream().map(ColumnComponentAttributeDO::getPageId).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(ids)) {
                columnComponentAttributeMapper.delete(new QueryWrapper<ColumnComponentAttributeDO>()
                        .in("page_id", ids).isNotNull("column_id"));
            }
        }
    }
}
