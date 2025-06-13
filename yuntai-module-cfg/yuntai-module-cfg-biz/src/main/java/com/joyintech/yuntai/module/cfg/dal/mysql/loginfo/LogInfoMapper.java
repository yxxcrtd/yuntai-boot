package com.joyintech.yuntai.module.cfg.dal.mysql.loginfo;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.loginfo.LogInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.loginfo.vo.*;

/**
 * 日志记录 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface LogInfoMapper extends BaseMapperX<LogInfoDO> {

    default PageResult<LogInfoDO> selectPage(LogInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LogInfoDO>()
                .eqIfPresent(LogInfoDO::getFormId, reqVO.getFormId())
                .eqIfPresent(LogInfoDO::getWorkFlowId, reqVO.getWorkFlowId())
                .eqIfPresent(LogInfoDO::getRequestId, reqVO.getRequestId())
                .eqIfPresent(LogInfoDO::getSerialNum, reqVO.getSerialNum())
                .eqIfPresent(LogInfoDO::getContent, reqVO.getContent())
                .betweenIfPresent(LogInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(LogInfoDO::getId));
    }

}