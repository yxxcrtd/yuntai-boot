package com.joyintech.yuntai.module.cfg.service.validaterules;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.validaterules.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.validaterules.ValidateRulesDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.validaterules.ValidateRulesMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 页面校验规则 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ValidateRulesServiceImpl implements ValidateRulesService {

    @Resource
    private ValidateRulesMapper validateRulesMapper;

    @Override
    public Long createValidateRules(ValidateRulesSaveReqVO createReqVO) {
        // 插入
        ValidateRulesDO validateRules = BeanUtils.toBean(createReqVO, ValidateRulesDO.class);
        validateRulesMapper.insert(validateRules);
        // 返回
        return validateRules.getId();
    }

    @Override
    public void updateValidateRules(ValidateRulesSaveReqVO updateReqVO) {
        // 校验存在
        validateValidateRulesExists(updateReqVO.getId());
        // 更新
        ValidateRulesDO updateObj = BeanUtils.toBean(updateReqVO, ValidateRulesDO.class);
        validateRulesMapper.updateById(updateObj);
    }

    @Override
    public void deleteValidateRules(Long id) {
        // 校验存在
        validateValidateRulesExists(id);
        // 删除
        validateRulesMapper.deleteById(id);
    }

    private void validateValidateRulesExists(Long id) {
        if (validateRulesMapper.selectById(id) == null) {
            throw exception(VALIDATE_RULES_NOT_EXISTS);
        }
    }

    @Override
    public ValidateRulesDO getValidateRules(Long id) {
        return validateRulesMapper.selectById(id);
    }

    @Override
    public PageResult<ValidateRulesDO> getValidateRulesPage(ValidateRulesPageReqVO pageReqVO) {
        return validateRulesMapper.selectPage(pageReqVO);
    }

}