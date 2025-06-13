package com.joyintech.yuntai.module.cfg.dal.mysql.componenttable;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componenttable.ComponentTableDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.componenttable.vo.*;

/**
 * 组件 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ComponentTableMapper extends BaseMapperX<ComponentTableDO> {

    default PageResult<ComponentTableDO> selectPage(ComponentTablePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ComponentTableDO>()
                .eqIfPresent(ComponentTableDO::getGroupId, reqVO.getGroupId())
                .inIfPresent(ComponentTableDO::getGroupId, reqVO.getGroupIds())
                .likeIfPresent(ComponentTableDO::getComponentName, reqVO.getComponentName())
                .eqIfPresent(ComponentTableDO::getComponentCode, reqVO.getComponentCode())
                .eqIfPresent(ComponentTableDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ComponentTableDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ComponentTableDO::getId));
    }

}
