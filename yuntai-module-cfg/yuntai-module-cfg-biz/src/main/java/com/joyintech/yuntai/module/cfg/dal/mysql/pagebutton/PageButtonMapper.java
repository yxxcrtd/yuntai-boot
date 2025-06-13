package com.joyintech.yuntai.module.cfg.dal.mysql.pagebutton;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagebutton.PageButtonDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pagebutton.vo.*;

/**
 * 页面操作按钮 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageButtonMapper extends BaseMapperX<PageButtonDO> {

    default PageResult<PageButtonDO> selectPage(PageButtonPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageButtonDO>()
                .eqIfPresent(PageButtonDO::getButtonType, reqVO.getButtonType())
                .eqIfPresent(PageButtonDO::getOperationType, reqVO.getOperationType())
                .likeIfPresent(PageButtonDO::getButtonName, reqVO.getButtonName())
                .eqIfPresent(PageButtonDO::getPageId, reqVO.getPageId())
                .eqIfPresent(PageButtonDO::getButtonStyle, reqVO.getButtonStyle())
                .eqIfPresent(PageButtonDO::getButtonIcon, reqVO.getButtonIcon())
                .eqIfPresent(PageButtonDO::getOpenWay, reqVO.getOpenWay())
                .eqIfPresent(PageButtonDO::getRelevancePage, reqVO.getRelevancePage())
                .betweenIfPresent(PageButtonDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PageButtonDO::getActionDefaultParams, reqVO.getActionDefaultParams())
                .eqIfPresent(PageButtonDO::getModuleTableId, reqVO.getModuleTableId())
                .orderByDesc(PageButtonDO::getId));
    }

}