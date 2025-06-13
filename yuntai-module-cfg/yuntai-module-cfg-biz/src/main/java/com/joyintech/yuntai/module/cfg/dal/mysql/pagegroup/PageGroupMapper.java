package com.joyintech.yuntai.module.cfg.dal.mysql.pagegroup;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagegroup.PageGroupDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pagegroup.vo.*;

/**
 * 页面分组 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageGroupMapper extends BaseMapperX<PageGroupDO> {

    default PageResult<PageGroupDO> selectPage(PageGroupPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageGroupDO>()
                .eqIfPresent(PageGroupDO::getPageId, reqVO.getPageId())
                .likeIfPresent(PageGroupDO::getGroupName, reqVO.getGroupName())
                .betweenIfPresent(PageGroupDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PageGroupDO::getId));
    }

}