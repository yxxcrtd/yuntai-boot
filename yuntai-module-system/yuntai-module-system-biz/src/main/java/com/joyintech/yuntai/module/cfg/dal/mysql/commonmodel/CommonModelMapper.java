package com.joyintech.yuntai.module.cfg.dal.mysql.commonmodel;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.commonmodel.CommonModelDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.commonmodel.vo.*;

/**
 * 公共模型 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface CommonModelMapper extends BaseMapperX<CommonModelDO> {

    default PageResult<CommonModelDO> selectPage(CommonModelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CommonModelDO>()
                .eqIfPresent(CommonModelDO::getModelCode, reqVO.getModelCode())
                .eqIfPresent(CommonModelDO::getModelName, reqVO.getModelName())
                .eqIfPresent(CommonModelDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(CommonModelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CommonModelDO::getId));
    }

}