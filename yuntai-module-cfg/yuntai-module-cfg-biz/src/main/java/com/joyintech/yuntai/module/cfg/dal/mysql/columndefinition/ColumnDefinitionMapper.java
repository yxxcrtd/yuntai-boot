package com.joyintech.yuntai.module.cfg.dal.mysql.columndefinition;

import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.TableField;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition.ColumnDefinitionWithTableDO;
import org.apache.ibatis.annotations.Mapper;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition.ColumnDefinitionDO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 字段定义 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ColumnDefinitionMapper extends BaseMapperX<ColumnDefinitionDO> {

    default PageResult<ColumnDefinitionDO> selectPage(ColumnDefinitionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ColumnDefinitionDO>()
                .eqIfPresent(ColumnDefinitionDO::getTableId, reqVO.getTableId())
                .likeIfPresent(ColumnDefinitionDO::getColumnName, reqVO.getColumnName())
                .eqIfPresent(ColumnDefinitionDO::getColumnComment, reqVO.getColumnComment())
                .eqIfPresent(ColumnDefinitionDO::getDataDomainId, reqVO.getDataDomainId())
                .eqIfPresent(ColumnDefinitionDO::getJdbcType, reqVO.getJdbcType())
                .eqIfPresent(ColumnDefinitionDO::getColumnLength, reqVO.getColumnLength())
                .eqIfPresent(ColumnDefinitionDO::getColumnScale, reqVO.getColumnScale())
                .eqIfPresent(ColumnDefinitionDO::getDefaultValue, reqVO.getDefaultValue())
                .eqIfPresent(ColumnDefinitionDO::getIsPrimaryKey, reqVO.getIsPrimaryKey())
                .eqIfPresent(ColumnDefinitionDO::getIsNotNull, reqVO.getIsNotNull())
                .eqIfPresent(ColumnDefinitionDO::getIsAutoIncrement, reqVO.getIsAutoIncrement())
                .betweenIfPresent(ColumnDefinitionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ColumnDefinitionDO::getIsSys, reqVO.getIsSys())
                .eqIfPresent(ColumnDefinitionDO::getStatus, reqVO.getStatus())
                .orderByAsc(ColumnDefinitionDO::getSort));
    }

    /**
     * 物理删除表字段
     * @param tableId
     */
    void deletePhysicsByTableId(@Param("tableId") Long tableId, @Param("ids") List<Long> ids);

    /**
     * 更新java字段类型
     * @param columnDefinitionDO
     */
    void updateJavaType(ColumnDefinitionDO columnDefinitionDO);

    /**
     * 根据表获取所有列
     * @param tableIds
     * @return
     */
    List<TableField> selectListWithTable(@Param("tableIds") Collection<Long> tableIds);

    ColumnDefinitionDO findColumnByModuleTableId(@Param("moduleTableId") Long moduleTableId, @Param("columnName") String columnName);
}
