package com.joyintech.yuntai.module.cfg.service.conditionaltable;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.conditionaltable.ConditionalTableDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.conditionaltable.ConditionalTableMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 条件 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ConditionalTableServiceImpl implements ConditionalTableService {

    @Resource
    private ConditionalTableMapper conditionalTableMapper;

    @Override
    public Long createConditionalTable(ConditionalTableSaveReqVO createReqVO) {
        // 插入
        ConditionalTableDO conditionalTable = BeanUtils.toBean(createReqVO, ConditionalTableDO.class);
        conditionalTableMapper.insert(conditionalTable);
        // 返回
        return conditionalTable.getId();
    }

    @Override
    public void updateConditionalTable(ConditionalTableSaveReqVO updateReqVO) {
        // 校验存在
        validateConditionalTableExists(updateReqVO.getId());
        // 更新
        ConditionalTableDO updateObj = BeanUtils.toBean(updateReqVO, ConditionalTableDO.class);
        conditionalTableMapper.updateById(updateObj);
    }

    @Override
    public void deleteConditionalTable(Long id) {
        // 校验存在
        validateConditionalTableExists(id);
        // 删除
        conditionalTableMapper.deleteById(id);
    }

    private void validateConditionalTableExists(Long id) {
        if (conditionalTableMapper.selectById(id) == null) {
            throw exception(CONDITIONAL_TABLE_NOT_EXISTS);
        }
    }

    @Override
    public ConditionalTableDO getConditionalTable(Long id) {
        return conditionalTableMapper.selectById(id);
    }

    @Override
    public PageResult<ConditionalTableDO> getConditionalTablePage(ConditionalTablePageReqVO pageReqVO) {
        return conditionalTableMapper.selectPage(pageReqVO);
    }

}