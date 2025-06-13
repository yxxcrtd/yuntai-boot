package com.joyintech.yuntai.module.cfg.service.validaterules;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.validaterules.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.validaterules.ValidateRulesDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面校验规则 Service 接口
 *
 * @author 兆尹云台
 */
public interface ValidateRulesService {

    /**
     * 创建页面校验规则
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createValidateRules(@Valid ValidateRulesSaveReqVO createReqVO);

    /**
     * 更新页面校验规则
     *
     * @param updateReqVO 更新信息
     */
    void updateValidateRules(@Valid ValidateRulesSaveReqVO updateReqVO);

    /**
     * 删除页面校验规则
     *
     * @param id 编号
     */
    void deleteValidateRules(Long id);

    /**
     * 获得页面校验规则
     *
     * @param id 编号
     * @return 页面校验规则
     */
    ValidateRulesDO getValidateRules(Long id);

    /**
     * 获得页面校验规则分页
     *
     * @param pageReqVO 分页查询
     * @return 页面校验规则分页
     */
    PageResult<ValidateRulesDO> getValidateRulesPage(ValidateRulesPageReqVO pageReqVO);

}