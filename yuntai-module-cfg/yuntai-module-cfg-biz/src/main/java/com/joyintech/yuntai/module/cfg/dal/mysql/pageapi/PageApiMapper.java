package com.joyintech.yuntai.module.cfg.dal.mysql.pageapi;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageapi.PageApiDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pageapi.vo.*;

/**
 * 页面api Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageApiMapper extends BaseMapperX<PageApiDO> {

    default PageResult<PageApiDO> selectPage(PageApiPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageApiDO>()
                .eqIfPresent(PageApiDO::getPageId, reqVO.getPageId())
                .eqIfPresent(PageApiDO::getModuleId, reqVO.getModuleId())
                .eqIfPresent(PageApiDO::getServerId, reqVO.getServerId())
                .eqIfPresent(PageApiDO::getApiCode, reqVO.getApiCode())
                .likeIfPresent(PageApiDO::getApiName, reqVO.getApiName())
                .betweenIfPresent(PageApiDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PageApiDO::getId));
    }

}