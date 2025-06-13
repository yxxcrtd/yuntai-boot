package com.joyintech.yuntai.module.cfg.dal.mysql.componentattribute;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentattribute.ComponentAttributeDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.*;

/**
 * 组件属性 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ComponentAttributeMapper extends BaseMapperX<ComponentAttributeDO> {

    default PageResult<ComponentAttributeDO> selectPage(ComponentAttributePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ComponentAttributeDO>()
                .eqIfPresent(ComponentAttributeDO::getComponentId, reqVO.getComponentId())
                .likeIfPresent(ComponentAttributeDO::getAttributeName, reqVO.getAttributeName())
                .eqIfPresent(ComponentAttributeDO::getAttributeType, reqVO.getAttributeType())
                .eqIfPresent(ComponentAttributeDO::getAttributeValue, reqVO.getAttributeValue())
                .betweenIfPresent(ComponentAttributeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ComponentAttributeDO::getId));
    }

}