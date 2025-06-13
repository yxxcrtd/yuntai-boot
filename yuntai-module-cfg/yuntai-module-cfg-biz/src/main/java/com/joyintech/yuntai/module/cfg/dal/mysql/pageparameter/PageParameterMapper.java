package com.joyintech.yuntai.module.cfg.dal.mysql.pageparameter;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageparameter.PageParameterDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pageparameter.vo.*;

/**
 * 页面参数 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageParameterMapper extends BaseMapperX<PageParameterDO> {

    default PageResult<PageParameterDO> selectPage(PageParameterPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageParameterDO>()
                .eqIfPresent(PageParameterDO::getPageApiId, reqVO.getPageApiId())
                .likeIfPresent(PageParameterDO::getParameterName, reqVO.getParameterName())
                .eqIfPresent(PageParameterDO::getParameterValue, reqVO.getParameterValue())
                .betweenIfPresent(PageParameterDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PageParameterDO::getId));
    }

}