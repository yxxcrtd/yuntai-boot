package com.joyintech.yuntai.module.cfg.dal.mysql.datasetconfighttp;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpPageReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfighttp.DataSetConfigHttpDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据集-http请求内容 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface DataSetConfigHttpMapper extends BaseMapperX<DataSetConfigHttpDO> {

    default PageResult<DataSetConfigHttpDO> selectPage(DataSetConfigHttpPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DataSetConfigHttpDO>()
                .eqIfPresent(DataSetConfigHttpDO::getDataId, reqVO.getDataId())
                .eqIfPresent(DataSetConfigHttpDO::getType, reqVO.getType())
                .eqIfPresent(DataSetConfigHttpDO::getKeyCode, reqVO.getKeyCode())
                .eqIfPresent(DataSetConfigHttpDO::getValue, reqVO.getValue())
                .betweenIfPresent(DataSetConfigHttpDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DataSetConfigHttpDO::getId));
    }

}