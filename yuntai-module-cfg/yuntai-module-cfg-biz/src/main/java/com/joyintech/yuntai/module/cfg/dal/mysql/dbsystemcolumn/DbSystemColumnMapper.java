package com.joyintech.yuntai.module.cfg.dal.mysql.dbsystemcolumn;

import org.apache.ibatis.annotations.Mapper;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.dbsystemcolumn.vo.DbSystemColumnPageReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbsystemcolumn.DbSystemColumnDO;

import java.util.List;

/**
 * 数据库系统字段 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface DbSystemColumnMapper extends BaseMapperX<DbSystemColumnDO> {

    default PageResult<DbSystemColumnDO> selectPage(DbSystemColumnPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DbSystemColumnDO>()
                .likeIfPresent(DbSystemColumnDO::getColumnName, reqVO.getColumnName())
                .eqIfPresent(DbSystemColumnDO::getColumnComment, reqVO.getColumnComment())
                .eqIfPresent(DbSystemColumnDO::getColumnPosition, reqVO.getColumnPosition())
                .eqIfPresent(DbSystemColumnDO::getDataDomainId, reqVO.getDataDomainId())
                .eqIfPresent(DbSystemColumnDO::getTypeSource, reqVO.getTypeSource())
                .eqIfPresent(DbSystemColumnDO::getColumnLength, reqVO.getColumnLength())
                .eqIfPresent(DbSystemColumnDO::getColumnScale, reqVO.getColumnScale())
                .eqIfPresent(DbSystemColumnDO::getDefaultValue, reqVO.getDefaultValue())
                .eqIfPresent(DbSystemColumnDO::getIsPrimaryKey, reqVO.getIsPrimaryKey())
                .eqIfPresent(DbSystemColumnDO::getIsNotNull, reqVO.getIsNotNull())
                .eqIfPresent(DbSystemColumnDO::getIsAutoIncrement, reqVO.getIsAutoIncrement())
                .betweenIfPresent(DbSystemColumnDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DbSystemColumnDO::getRemark, reqVO.getRemark())
                .orderByAsc(DbSystemColumnDO::getTypeSource)
                .orderByAsc(DbSystemColumnDO::getColumnPosition));
    }

    List<DbSystemColumnDO> selectSystemFieldCache();
}
