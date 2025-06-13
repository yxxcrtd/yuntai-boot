package com.joyintech.yuntai.module.cfg.dal.mysql.SubTableSetting;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.SubTableSetting.SubTableSettingDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.SubTableSetting.vo.*;

/**
 * 子表设置 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface SubTableSettingMapper extends BaseMapperX<SubTableSettingDO> {

    default PageResult<SubTableSettingDO> selectPage(SubTableSettingPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SubTableSettingDO>()
                .eqIfPresent(SubTableSettingDO::getEditMode, reqVO.getEditMode())
                .eqIfPresent(SubTableSettingDO::getIsRequired, reqVO.getIsRequired())
                .eqIfPresent(SubTableSettingDO::getIsShowIndex, reqVO.getIsShowIndex())
                .eqIfPresent(SubTableSettingDO::getIsShowTotal, reqVO.getIsShowTotal())
                .eqIfPresent(SubTableSettingDO::getPageId, reqVO.getPageId())
                .eqIfPresent(SubTableSettingDO::getPageApiId, reqVO.getPageApiId())
                .eqIfPresent(SubTableSettingDO::getTableId, reqVO.getTableId())
                .eqIfPresent(SubTableSettingDO::getTableTitle, reqVO.getTableTitle())
                .eqIfPresent(SubTableSettingDO::getDefaultData, reqVO.getDefaultData())
                .eqIfPresent(SubTableSettingDO::getTableSort, reqVO.getTableSort())
                .eqIfPresent(SubTableSettingDO::getTableBeginIndex, reqVO.getTableBeginIndex())
                .eqIfPresent(SubTableSettingDO::getTableStripes, reqVO.getTableStripes())
                .eqIfPresent(SubTableSettingDO::getIsShowCheckBox, reqVO.getIsShowCheckBox())
                .eqIfPresent(SubTableSettingDO::getTableFixedAction, reqVO.getTableFixedAction())
                .eqIfPresent(SubTableSettingDO::getTableActionPostion, reqVO.getTableActionPostion())
                .eqIfPresent(SubTableSettingDO::getIsPageList, reqVO.getIsPageList())
                .eqIfPresent(SubTableSettingDO::getTableDefPageSize, reqVO.getTableDefPageSize())
                .betweenIfPresent(SubTableSettingDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SubTableSettingDO::getId));
    }

}