package com.joyintech.yuntai.module.cfg.dal.mysql.pagelinkage;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelinkage.PageLinkageDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo.*;

/**
 * 页面联动配置 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageLinkageMapper extends BaseMapperX<PageLinkageDO> {

    default PageResult<PageLinkageDO> selectPage(PageLinkagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageLinkageDO>()
                .likeIfPresent(PageLinkageDO::getLinkageName, reqVO.getLinkageName())
                .eqIfPresent(PageLinkageDO::getLinkageType, reqVO.getLinkageType())
                .eqIfPresent(PageLinkageDO::getLinkageWay, reqVO.getLinkageWay())
                .eqIfPresent(PageLinkageDO::getLinkageContent, reqVO.getLinkageContent())
                .betweenIfPresent(PageLinkageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PageLinkageDO::getId));
    }

}