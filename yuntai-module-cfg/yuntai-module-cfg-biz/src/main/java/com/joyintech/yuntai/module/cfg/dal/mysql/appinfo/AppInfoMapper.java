package com.joyintech.yuntai.module.cfg.dal.mysql.appinfo;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.appinfo.AppInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.appinfo.vo.*;

/**
 * 多应用 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface AppInfoMapper extends BaseMapperX<AppInfoDO> {

    default PageResult<AppInfoDO> selectPage(AppInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AppInfoDO>()
                .likeIfPresent(AppInfoDO::getAppName, reqVO.getAppName())
                .eqIfPresent(AppInfoDO::getAppCode, reqVO.getAppCode())
                .eqIfPresent(AppInfoDO::getAppAddress, reqVO.getAppAddress())
                .eqIfPresent(AppInfoDO::getContainer, reqVO.getContainer())
                .eqIfPresent(AppInfoDO::getStatus, reqVO.getStatus())
                .eqIfPresent(AppInfoDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(AppInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AppInfoDO::getId));
    }

}