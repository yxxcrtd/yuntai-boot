package com.joyintech.yuntai.module.cfg.dal.mysql.buttonaction;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.buttonaction.ButtonActionDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.buttonaction.vo.*;

/**
 * 页面按钮动作 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ButtonActionMapper extends BaseMapperX<ButtonActionDO> {

    default PageResult<ButtonActionDO> selectPage(ButtonActionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ButtonActionDO>()
                .eqIfPresent(ButtonActionDO::getPageButtonId, reqVO.getPageButtonId())
                .eqIfPresent(ButtonActionDO::getPageId, reqVO.getPageId())
                .eqIfPresent(ButtonActionDO::getActionType, reqVO.getActionType())
                .eqIfPresent(ButtonActionDO::getModelServerId, reqVO.getModelServerId())
                .eqIfPresent(ButtonActionDO::getOpenWay, reqVO.getOpenWay())
                .eqIfPresent(ButtonActionDO::getRelevancePage, reqVO.getRelevancePage())
                .eqIfPresent(ButtonActionDO::getServerParams, reqVO.getServerParams())
                .eqIfPresent(ButtonActionDO::getDataScript, reqVO.getDataScript())
                .eqIfPresent(ButtonActionDO::getCustomMethod, reqVO.getCustomMethod())
                .betweenIfPresent(ButtonActionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ButtonActionDO::getPageType, reqVO.getPageType())
                .eqIfPresent(ButtonActionDO::getRelevanceUrl, reqVO.getRelevanceUrl())
                .orderByDesc(ButtonActionDO::getId));
    }

}