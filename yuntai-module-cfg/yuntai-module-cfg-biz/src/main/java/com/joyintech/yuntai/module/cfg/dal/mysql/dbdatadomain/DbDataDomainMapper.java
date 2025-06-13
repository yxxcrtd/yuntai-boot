package com.joyintech.yuntai.module.cfg.dal.mysql.dbdatadomain;

import org.apache.ibatis.annotations.Mapper;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain.vo.DbDataDomainPageReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbdatadomain.DbDataDomainDO;

/**
 * 数据库字段类型表(数据域) Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface DbDataDomainMapper extends BaseMapperX<DbDataDomainDO> {

    default PageResult<DbDataDomainDO> selectPage(DbDataDomainPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DbDataDomainDO>()
                .eqIfPresent(DbDataDomainDO::getDataType, reqVO.getDataType())
                .eqIfPresent(DbDataDomainDO::getDbType, reqVO.getDbType())
                .eqIfPresent(DbDataDomainDO::getDataLength, reqVO.getDataLength())
                .eqIfPresent(DbDataDomainDO::getDataScale, reqVO.getDataScale())
                .eqIfPresent(DbDataDomainDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(DbDataDomainDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DbDataDomainDO::getId));
    }

}