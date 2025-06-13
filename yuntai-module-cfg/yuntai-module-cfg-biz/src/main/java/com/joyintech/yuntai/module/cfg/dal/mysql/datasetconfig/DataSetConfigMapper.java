package com.joyintech.yuntai.module.cfg.dal.mysql.datasetconfig;

import java.util.List;
import java.util.Map;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig.vo.DataSetConfigPageReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfig.DataSetConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 数据集管理 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface DataSetConfigMapper extends BaseMapperX<DataSetConfigDO> {

    default PageResult<DataSetConfigDO> selectPage(DataSetConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DataSetConfigDO>()
                .eqIfPresent(DataSetConfigDO::getType, reqVO.getType())
                .likeIfPresent(DataSetConfigDO::getName, reqVO.getName())
                .eqIfPresent(DataSetConfigDO::getCode, reqVO.getCode())
                .eqIfPresent(DataSetConfigDO::getSourceCode, reqVO.getSourceCode())
                .eqIfPresent(DataSetConfigDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DataSetConfigDO::getJsonData, reqVO.getJsonData())
                .eqIfPresent(DataSetConfigDO::getSqlData, reqVO.getSqlData())
                .eqIfPresent(DataSetConfigDO::getCallMethod, reqVO.getCallMethod())
                .eqIfPresent(DataSetConfigDO::getUrl, reqVO.getUrl())
                .eqIfPresent(DataSetConfigDO::getRequestMethods, reqVO.getRequestMethods())
                .betweenIfPresent(DataSetConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DataSetConfigDO::getId));
    }

    /**
     * SQL语句查询结果
     *
     * @param sql
     * @return
     */
    List<Map> findMapList(@Param("sql") String sql);
}