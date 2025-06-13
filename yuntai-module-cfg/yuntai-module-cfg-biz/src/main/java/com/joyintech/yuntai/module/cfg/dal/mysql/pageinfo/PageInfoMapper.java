package com.joyintech.yuntai.module.cfg.dal.mysql.pageinfo;


import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageinfo.PageInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pageinfo.vo.*;

/**
 * 页面基本信息 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageInfoMapper extends BaseMapperX<PageInfoDO> {

    default PageResult<PageInfoDO> selectPage(PageInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageInfoDO>()
                .likeIfPresent(PageInfoDO::getPageName, reqVO.getPageName())
                .eqIfPresent(PageInfoDO::getPageCode, reqVO.getPageCode())
                .eqIfPresent(PageInfoDO::getPageType, reqVO.getPageType())
                .eqIfPresent(PageInfoDO::getModuleId, reqVO.getModuleId())
                .eqIfPresent(PageInfoDO::getServerId, reqVO.getServerId())
                .eqIfPresent(PageInfoDO::getPageStyle, reqVO.getPageStyle())
                .eqIfPresent(PageInfoDO::getDefaultQuery, reqVO.getDefaultQuery())
                .eqIfPresent(PageInfoDO::getPageTemplate, reqVO.getPageTemplate())
                .eqIfPresent(PageInfoDO::getParentPage, reqVO.getParentPage())
                .eqIfPresent(PageInfoDO::getInnerSlot, reqVO.getInnerSlot())
                .eqIfPresent(PageInfoDO::getHeaderSlot, reqVO.getHeaderSlot())
                .eqIfPresent(PageInfoDO::getMiddleSlot, reqVO.getMiddleSlot())
                .eqIfPresent(PageInfoDO::getTailSlot, reqVO.getTailSlot())
                .eqIfPresent(PageInfoDO::getMenuId, reqVO.getMenuId())
                .betweenIfPresent(PageInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PageInfoDO::getRemark, reqVO.getRemark())
                .orderByDesc(PageInfoDO::getId));
    }

}
