package com.joyintech.yuntai.module.cfg.dal.mysql.pagelistconfig;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistconfig.PageListConfigDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.*;

/**
 * 列表页配置 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageListConfigMapper extends BaseMapperX<PageListConfigDO> {

    default PageResult<PageListConfigDO> selectPage(PageListConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageListConfigDO>()
                .eqIfPresent(PageListConfigDO::getPageId, reqVO.getPageId())
                .eqIfPresent(PageListConfigDO::getModuleTableId, reqVO.getModuleTableId())
                .likeIfPresent(PageListConfigDO::getColumnName, reqVO.getColumnName())
                .eqIfPresent(PageListConfigDO::getColumnComment, reqVO.getColumnComment())
                .eqIfPresent(PageListConfigDO::getColumnNameAlias, reqVO.getColumnNameAlias())
                .eqIfPresent(PageListConfigDO::getColumnFieldAlias, reqVO.getColumnFieldAlias())
//                .eqIfPresent(PageListConfigDO::getColumnGroupId, reqVO.getColumnGroupId())
                .eqIfPresent(PageListConfigDO::getColumnAlignment, reqVO.getColumnAlignment())
                .eqIfPresent(PageListConfigDO::getColumnFixedWidth, reqVO.getColumnFixedWidth())
                .eqIfPresent(PageListConfigDO::getColumnMinWidth, reqVO.getColumnMinWidth())
                .eqIfPresent(PageListConfigDO::getIsVisible, reqVO.getIsVisible())
                .eqIfPresent(PageListConfigDO::getIsColumnFixed, reqVO.getIsColumnFixed())
                .eqIfPresent(PageListConfigDO::getColumnSlot, reqVO.getColumnSlot())
                .eqIfPresent(PageListConfigDO::getIsTotalColumn, reqVO.getIsTotalColumn())
                .eqIfPresent(PageListConfigDO::getIsColumnSort, reqVO.getIsColumnSort())
                .eqIfPresent(PageListConfigDO::getColumnHeadTips, reqVO.getColumnHeadTips())
                .eqIfPresent(PageListConfigDO::getColumnHeadSlot, reqVO.getColumnHeadSlot())
                .betweenIfPresent(PageListConfigDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PageListConfigDO::getColumnHeadWidth, reqVO.getColumnHeadWidth())
                .eqIfPresent(PageListConfigDO::getColumnTagConfig, reqVO.getColumnTagConfig())
                .orderByDesc(PageListConfigDO::getId));
    }

}