package com.joyintech.yuntai.module.cfg.dal.mysql.pageextendevent;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageextendevent.PageExtendEventDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pageextendevent.vo.*;

/**
 * 页面事件扩展配置 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageExtendEventMapper extends BaseMapperX<PageExtendEventDO> {

    default PageResult<PageExtendEventDO> selectPage(PageExtendEventPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageExtendEventDO>()
                .eqIfPresent(PageExtendEventDO::getPageId, reqVO.getPageId())
                .eqIfPresent(PageExtendEventDO::getSort, reqVO.getSort())
                .eqIfPresent(PageExtendEventDO::getInterfaceUrl, reqVO.getInterfaceUrl())
                .eqIfPresent(PageExtendEventDO::getInterfaceParam, reqVO.getInterfaceParam())
                .betweenIfPresent(PageExtendEventDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PageExtendEventDO::getRunTime, reqVO.getRunTime())
                .eqIfPresent(PageExtendEventDO::getIsIntercept, reqVO.getIsIntercept())
                .eqIfPresent(PageExtendEventDO::getEventType, reqVO.getEventType())
                .eqIfPresent(PageExtendEventDO::getCallType, reqVO.getCallType())
                .likeIfPresent(PageExtendEventDO::getInterfaceName, reqVO.getInterfaceName())
                .likeIfPresent(PageExtendEventDO::getMethodName, reqVO.getMethodName())
                .eqIfPresent(PageExtendEventDO::getExecutableCode, reqVO.getExecutableCode())
                .orderByDesc(PageExtendEventDO::getId));
    }

}
