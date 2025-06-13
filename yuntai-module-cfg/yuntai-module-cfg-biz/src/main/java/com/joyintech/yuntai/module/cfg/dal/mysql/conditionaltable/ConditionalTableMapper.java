package com.joyintech.yuntai.module.cfg.dal.mysql.conditionaltable;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.conditionaltable.ConditionalTableDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.*;

/**
 * 条件 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ConditionalTableMapper extends BaseMapperX<ConditionalTableDO> {

    default PageResult<ConditionalTableDO> selectPage(ConditionalTablePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ConditionalTableDO>()
                .eqIfPresent(ConditionalTableDO::getRelevanceId, reqVO.getRelevanceId())
                .eqIfPresent(ConditionalTableDO::getContent, reqVO.getContent())
                .betweenIfPresent(ConditionalTableDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ConditionalTableDO::getShowPageTypeCode, reqVO.getShowPageTypeCode())
                .eqIfPresent(ConditionalTableDO::getFillValue, reqVO.getFillValue())
                .eqIfPresent(ConditionalTableDO::getColumnDisplayComponent, reqVO.getColumnDisplayComponent())
                .orderByDesc(ConditionalTableDO::getId));
    }

}