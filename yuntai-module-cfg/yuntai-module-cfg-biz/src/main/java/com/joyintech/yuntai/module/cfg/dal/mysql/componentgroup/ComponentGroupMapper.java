package com.joyintech.yuntai.module.cfg.dal.mysql.componentgroup;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentgroup.ComponentGroupDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.componentgroup.vo.*;

/**
 * 组件分组 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ComponentGroupMapper extends BaseMapperX<ComponentGroupDO> {

    default PageResult<ComponentGroupDO> selectPage(ComponentGroupPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ComponentGroupDO>()
                .likeIfPresent(ComponentGroupDO::getGroupName, reqVO.getGroupName())
                .eqIfPresent(ComponentGroupDO::getNumSort, reqVO.getNumSort())
                .betweenIfPresent(ComponentGroupDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ComponentGroupDO::getId));
    }

}