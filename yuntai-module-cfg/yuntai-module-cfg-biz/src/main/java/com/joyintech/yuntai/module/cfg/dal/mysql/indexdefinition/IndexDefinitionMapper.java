package com.joyintech.yuntai.module.cfg.dal.mysql.indexdefinition;

import org.apache.ibatis.annotations.Mapper;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition.vo.IndexDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.indexdefinition.IndexDefinitionDO;

/**
 * 索引定义 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface IndexDefinitionMapper extends BaseMapperX<IndexDefinitionDO> {

    default PageResult<IndexDefinitionDO> selectPage(IndexDefinitionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IndexDefinitionDO>()
                .eqIfPresent(IndexDefinitionDO::getTableId, reqVO.getTableId())
                .likeIfPresent(IndexDefinitionDO::getIndexName, reqVO.getIndexName())
                .eqIfPresent(IndexDefinitionDO::getIndexColumns, reqVO.getIndexColumns())
                .eqIfPresent(IndexDefinitionDO::getIsUniqueKey, reqVO.getIsUniqueKey())
                .betweenIfPresent(IndexDefinitionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(IndexDefinitionDO::getId));
    }

}