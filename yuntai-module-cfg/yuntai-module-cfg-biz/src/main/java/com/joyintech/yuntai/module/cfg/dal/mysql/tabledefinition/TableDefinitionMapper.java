package com.joyintech.yuntai.module.cfg.dal.mysql.tabledefinition;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.TABLE_DEFINITION_NOT_EXISTS;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.tabledefinition.TableDefinitionDO;
import org.apache.ibatis.annotations.Param;

/**
 * 表定义 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface TableDefinitionMapper extends BaseMapperX<TableDefinitionDO> {

    default PageResult<TableDefinitionDO> selectPage(TableDefinitionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TableDefinitionDO>()
                .eqIfPresent(TableDefinitionDO::getDatasourceId, reqVO.getDatasourceId())
                .likeIfPresent(TableDefinitionDO::getTableName, reqVO.getTableName())
                .likeIfPresent(TableDefinitionDO::getTableComment, reqVO.getTableComment())
                .eqIfPresent(TableDefinitionDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TableDefinitionDO::getUpdateTime, reqVO.getUpdateTime())
                .eqIfPresent(TableDefinitionDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TableDefinitionDO::getTableType, reqVO.getTableType())
                .eqIfPresent(TableDefinitionDO::getTableSql, reqVO.getTableSql())
                .orderByDesc(TableDefinitionDO::getId));
    }
    default List<TableDefinitionDO> selectListByDataSourceConfigId(Long dataSourceConfigId) {
        return selectList(TableDefinitionDO::getDatasourceId, dataSourceConfigId);
    }

    default TableDefinitionDO getAndCheck(Long id) {
        TableDefinitionDO tableDefinitionDO = selectOne(TableDefinitionDO::getId, id);
        if (tableDefinitionDO == null) {
            throw exception(TABLE_DEFINITION_NOT_EXISTS);
        }
        return tableDefinitionDO;
    }

    void deletePhysics(@Param("id") Long id);

}
