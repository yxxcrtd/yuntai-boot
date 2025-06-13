package com.joyintech.yuntai.module.cfg.dal.mysql.outsystemtable;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable.OutSystemTableDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.joyintech.yuntai.module.cfg.controller.admin.outsystemtable.vo.*;

/**
 * 外部系统关联 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface OutSystemTableMapper extends BaseMapperX<OutSystemTableDO> {

    default PageResult<OutSystemTableDO> selectPage(OutSystemTablePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OutSystemTableDO>()
                .eqIfPresent(OutSystemTableDO::getPageId, reqVO.getPageId())
                .eqIfPresent(OutSystemTableDO::getFlowId, reqVO.getFlowId())
                .eqIfPresent(OutSystemTableDO::getFlowInstanceId, reqVO.getFlowInstanceId())
                .eqIfPresent(OutSystemTableDO::getFlowType, reqVO.getFlowType())
                .eqIfPresent(OutSystemTableDO::getBusinessId, reqVO.getBusinessId())
                .eqIfPresent(OutSystemTableDO::getPageDataId, reqVO.getPageDataId())
                .betweenIfPresent(OutSystemTableDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OutSystemTableDO::getId));
    }

    List<Long> getPageId(@Param("flowId") String flowId);

    List<Long> getPageIdByPageDataId(@Param("pageDataId") Long pageDataId);
}