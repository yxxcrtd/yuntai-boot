package com.joyintech.yuntai.module.cfg.dal.mysql.dataconversion;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataconversion.DataConversionDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.dataconversion.vo.*;

/**
 * 数据转换 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface DataConversionMapper extends BaseMapperX<DataConversionDO> {

    default PageResult<DataConversionDO> selectPage(DataConversionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DataConversionDO>()
                .eqIfPresent(DataConversionDO::getListConfigId, reqVO.getListConfigId())
                .eqIfPresent(DataConversionDO::getDataType, reqVO.getDataType())
                .eqIfPresent(DataConversionDO::getDataContent, reqVO.getDataContent())
                .eqIfPresent(DataConversionDO::getDataScript, reqVO.getDataScript())
                .betweenIfPresent(DataConversionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DataConversionDO::getId));
    }

}