package com.joyintech.yuntai.module.cfg.service.SubTableSetting;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.module.cfg.dal.dataobject.conditionaltable.ConditionalTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagegroup.PageGroupDO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import com.joyintech.yuntai.module.cfg.controller.admin.SubTableSetting.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.SubTableSetting.SubTableSettingDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.SubTableSetting.SubTableSettingMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.conditionaltable.ConditionalTableMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.SUB_TABLE_SETTING_NOT_EXISTS;

/**
 * 子表设置 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class SubTableSettingServiceImpl implements SubTableSettingService {

    @Resource
    private SubTableSettingMapper subTableSettingMapper;

    @Resource
    private ConditionalTableMapper conditionalTableMapper;

    @Override
    public Long createSubTableSetting(SubTableSettingSaveReqVO createReqVO) {
        // 插入
        SubTableSettingDO subTableSetting = BeanUtils.toBean(createReqVO, SubTableSettingDO.class);
        subTableSettingMapper.insert(subTableSetting);
        // 返回
        return subTableSetting.getId();
    }

    @Override
    public void updateSubTableSetting(SubTableSettingSaveReqVO updateReqVO) {
        // 校验存在
        validateSubTableSettingExists(updateReqVO.getId());
        // 更新
        SubTableSettingDO updateObj = BeanUtils.toBean(updateReqVO, SubTableSettingDO.class);
        subTableSettingMapper.updateById(updateObj);
    }

    @Override
    public void deleteSubTableSetting(Long id) {
        // 校验存在
        validateSubTableSettingExists(id);
        // 删除
        subTableSettingMapper.deleteById(id);
    }

    private void validateSubTableSettingExists(Long id) {
        if (subTableSettingMapper.selectById(id) == null) {
            throw exception(SUB_TABLE_SETTING_NOT_EXISTS);
        }
    }

    @Override
    public SubTableSettingDO getSubTableSetting(Long id) {
        return subTableSettingMapper.selectById(id);
    }

    @Override
    public PageResult<SubTableSettingDO> getSubTableSettingPage(SubTableSettingPageReqVO pageReqVO) {
        return subTableSettingMapper.selectPage(pageReqVO);
    }

    @Override
    public void addSubTableSettingList(List<SubTableSettingSaveReqVO> subTableSettingSaveReqVOS) {
        List<SubTableSettingDO> bean = BeanUtils.toBean(subTableSettingSaveReqVOS, SubTableSettingDO.class);
        subTableSettingMapper.insertOrUpdateBatch(bean);

        subTableSettingSaveReqVOS.forEach(s ->{
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
            List<Long> list = bean.stream().map(SubTableSettingDO::getId).collect(Collectors.toList());
            subTableSettingMapper.delete(new QueryWrapper<SubTableSettingDO>()
                    .eq("page_id", bean.get(0).getPageId())
                    .notIn("id", list)
            );
        }
    }

}