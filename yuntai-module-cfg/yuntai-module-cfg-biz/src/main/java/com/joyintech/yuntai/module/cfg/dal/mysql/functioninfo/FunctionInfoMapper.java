package com.joyintech.yuntai.module.cfg.dal.mysql.functioninfo;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.functioninfo.FunctionInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.functioninfo.vo.*;

/**
 * 开发平台功能管理 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface FunctionInfoMapper extends BaseMapperX<FunctionInfoDO> {

    default PageResult<FunctionInfoDO> selectPage(FunctionInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FunctionInfoDO>()
                .eqIfPresent(FunctionInfoDO::getParentId, reqVO.getParentId())
                .likeIfPresent(FunctionInfoDO::getFunctionName, reqVO.getFunctionName())
                .eqIfPresent(FunctionInfoDO::getFunctionCode, reqVO.getFunctionCode())
                .eqIfPresent(FunctionInfoDO::getFunctionIcon, reqVO.getFunctionIcon())
                .eqIfPresent(FunctionInfoDO::getSort, reqVO.getSort())
                .eqIfPresent(FunctionInfoDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FunctionInfoDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(FunctionInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FunctionInfoDO::getId));
    }
}