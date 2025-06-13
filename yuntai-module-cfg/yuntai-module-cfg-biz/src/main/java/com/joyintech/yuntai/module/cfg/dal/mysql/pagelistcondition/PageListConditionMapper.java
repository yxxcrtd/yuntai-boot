package com.joyintech.yuntai.module.cfg.dal.mysql.pagelistcondition;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistcondition.PageListConditionDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistcondition.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 表单页查询条件（待定） Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageListConditionMapper extends BaseMapperX<PageListConditionDO> {

    default PageResult<PageListConditionDO> selectPage(PageListConditionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageListConditionDO>()
                .eqIfPresent(PageListConditionDO::getPageId, reqVO.getPageId())
                .likeIfPresent(PageListConditionDO::getColumnName, reqVO.getColumnName())
                .eqIfPresent(PageListConditionDO::getColumnComment, reqVO.getColumnComment())
                .eqIfPresent(PageListConditionDO::getColumnNameAlias, reqVO.getColumnNameAlias())
                .eqIfPresent(PageListConditionDO::getColumnQueryOperator, reqVO.getColumnQueryOperator())
                .eqIfPresent(PageListConditionDO::getColumnDisplayComponent, reqVO.getColumnDisplayComponent())
                .eqIfPresent(PageListConditionDO::getColumnDefault, reqVO.getColumnDefault())
                .eqIfPresent(PageListConditionDO::getIsQueryColumn, reqVO.getIsQueryColumn())
                .eqIfPresent(PageListConditionDO::getIsColumnRequire, reqVO.getIsColumnRequire())
                .eqIfPresent(PageListConditionDO::getColumnSlot, reqVO.getColumnSlot())
                .betweenIfPresent(PageListConditionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PageListConditionDO::getId));
    }

    List<PageListConditionDO> selectByPageId(@Param("pageId") Long pageId);

}
