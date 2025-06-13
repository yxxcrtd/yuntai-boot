package com.joyintech.yuntai.module.cfg.dal.mysql.dataformat;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataformat.DataFormatDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.dataformat.vo.*;

/**
 * 页面数据格式化 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface DataFormatMapper extends BaseMapperX<DataFormatDO> {

    default PageResult<DataFormatDO> selectPage(DataFormatPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DataFormatDO>()
                .eqIfPresent(DataFormatDO::getListConfigId, reqVO.getListConfigId())
                .eqIfPresent(DataFormatDO::getPrefix, reqVO.getPrefix())
                .eqIfPresent(DataFormatDO::getSuffix, reqVO.getSuffix())
                .eqIfPresent(DataFormatDO::getFormatType, reqVO.getFormatType())
                .eqIfPresent(DataFormatDO::getNumType, reqVO.getNumType())
                .eqIfPresent(DataFormatDO::getKeepDecimalPlaces, reqVO.getKeepDecimalPlaces())
                .eqIfPresent(DataFormatDO::getConversionRate, reqVO.getConversionRate())
                .eqIfPresent(DataFormatDO::getThousandth, reqVO.getThousandth())
                .eqIfPresent(DataFormatDO::getDateFormat, reqVO.getDateFormat())
                .eqIfPresent(DataFormatDO::getPictureStyle, reqVO.getPictureStyle())
                .eqIfPresent(DataFormatDO::getLinkOpenMethod, reqVO.getLinkOpenMethod())
                .eqIfPresent(DataFormatDO::getLinkAddress, reqVO.getLinkAddress())
                .eqIfPresent(DataFormatDO::getRegularExpression, reqVO.getRegularExpression())
                .likeIfPresent(DataFormatDO::getComponentName, reqVO.getComponentName())
                .betweenIfPresent(DataFormatDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DataFormatDO::getId));
    }

}