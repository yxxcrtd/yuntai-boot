package com.joyintech.yuntai.module.infra.dal.mysql.db;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.infra.controller.admin.db.vo.DataSourceConfigRespVO;
import com.joyintech.yuntai.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {

//    default PageResult<DataSourceConfigDO> selectPage(DataSourceConfigRespVO reqVO) {
//        return selectPage(reqVO, new LambdaQueryWrapperX<DataSourceConfigDO>()
//                .likeIfPresent(DataSourceConfigDO::getName, reqVO.getName())
//                .orderByDesc(DataSourceConfigDO::getId));
//    }
}
