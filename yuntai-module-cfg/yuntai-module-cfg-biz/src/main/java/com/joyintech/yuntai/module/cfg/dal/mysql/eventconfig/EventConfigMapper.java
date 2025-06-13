package com.joyintech.yuntai.module.cfg.dal.mysql.eventconfig;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.eventconfig.EventConfigDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.eventconfig.vo.*;

/**
 * 页面事件配置 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface EventConfigMapper extends BaseMapperX<EventConfigDO> {

    default PageResult<EventConfigDO> selectPage(EventConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EventConfigDO>()
                .likeIfPresent(EventConfigDO::getEventName, reqVO.getEventName())
                .eqIfPresent(EventConfigDO::getScript, reqVO.getScript())
                .betweenIfPresent(EventConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EventConfigDO::getId));
    }

}