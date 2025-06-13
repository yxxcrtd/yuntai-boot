package com.joyintech.yuntai.module.cfg.dal.mysql.pageformconfig;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageformconfig.PageFormConfigDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pageformconfig.vo.*;

/**
 * 表单页配置 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageFormConfigMapper extends BaseMapperX<PageFormConfigDO> {

    default PageResult<PageFormConfigDO> selectPage(PageFormConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageFormConfigDO>()
                .eqIfPresent(PageFormConfigDO::getPageId, reqVO.getPageId())
                .likeIfPresent(PageFormConfigDO::getColumnName, reqVO.getColumnName())
                .eqIfPresent(PageFormConfigDO::getColumnTitle, reqVO.getColumnTitle())
                .eqIfPresent(PageFormConfigDO::getColumnAlignment, reqVO.getColumnAlignment())
                .eqIfPresent(PageFormConfigDO::getColumnComponent, reqVO.getColumnComponent())
                .eqIfPresent(PageFormConfigDO::getIsRequired, reqVO.getIsRequired())
                .eqIfPresent(PageFormConfigDO::getColumnPlaceholder, reqVO.getColumnPlaceholder())
                .eqIfPresent(PageFormConfigDO::getColumnTips, reqVO.getColumnTips())
                .eqIfPresent(PageFormConfigDO::getColumnWidth, reqVO.getColumnWidth())
                .eqIfPresent(PageFormConfigDO::getColumnSlot, reqVO.getColumnSlot())
                .eqIfPresent(PageFormConfigDO::getComponentConfig, reqVO.getComponentConfig())
                .eqIfPresent(PageFormConfigDO::getCheckRuleConfig, reqVO.getCheckRuleConfig())
                .eqIfPresent(PageFormConfigDO::getLinkedConfig, reqVO.getLinkedConfig())
                .eqIfPresent(PageFormConfigDO::getEventConfig, reqVO.getEventConfig())
                .betweenIfPresent(PageFormConfigDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PageFormConfigDO::getRemark, reqVO.getRemark())
                .orderByDesc(PageFormConfigDO::getId));
    }

}