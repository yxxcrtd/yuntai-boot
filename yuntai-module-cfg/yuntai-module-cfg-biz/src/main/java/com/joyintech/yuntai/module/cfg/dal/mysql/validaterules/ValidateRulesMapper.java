package com.joyintech.yuntai.module.cfg.dal.mysql.validaterules;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.validaterules.ValidateRulesDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.validaterules.vo.*;

/**
 * 页面校验规则 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ValidateRulesMapper extends BaseMapperX<ValidateRulesDO> {

    default PageResult<ValidateRulesDO> selectPage(ValidateRulesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ValidateRulesDO>()
                .likeIfPresent(ValidateRulesDO::getCheckName, reqVO.getCheckName())
                .eqIfPresent(ValidateRulesDO::getToolTips, reqVO.getToolTips())
                .likeIfPresent(ValidateRulesDO::getRulesName, reqVO.getRulesName())
                .betweenIfPresent(ValidateRulesDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ValidateRulesDO::getId));
    }

}