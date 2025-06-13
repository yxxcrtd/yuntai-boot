package com.joyintech.yuntai.module.system.dal.mysql.dictitem;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;

import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.system.controller.admin.dictitem.vo.DictItemPageReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dictitem.DictItemDO;

/**
 * 字典子表 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface DictItemMapper extends BaseMapperX<DictItemDO> {

    default PageResult<DictItemDO> selectPage(DictItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DictItemDO>()
                .eqIfPresent(DictItemDO::getDictId, reqVO.getDictId())
                .eqIfPresent(DictItemDO::getDictCode, reqVO.getDictCode())
                .eqIfPresent(DictItemDO::getItemText, reqVO.getItemText())
                .eqIfPresent(DictItemDO::getItemValue, reqVO.getItemValue())
                .eqIfPresent(DictItemDO::getFilterType, reqVO.getFilterType())
                .eqIfPresent(DictItemDO::getDescription, reqVO.getDescription())
                .eqIfPresent(DictItemDO::getSortOrder, reqVO.getSortOrder())
                .eqIfPresent(DictItemDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DictItemDO::getCreateBy, reqVO.getCreateBy())
                .betweenIfPresent(DictItemDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DictItemDO::getUpdateBy, reqVO.getUpdateBy())
                .eqIfPresent(DictItemDO::getDelFlag, reqVO.getDelFlag())
                .eqIfPresent(DictItemDO::getTenantCode, reqVO.getTenantCode())
                .eqIfPresent(DictItemDO::getExtText1, reqVO.getExtText1())
                .orderByDesc(DictItemDO::getId));
    }

    default List<DictItemDO> selectListByStatusAndDictType(Integer status, String dictType) {
        return selectList(new LambdaQueryWrapperX<DictItemDO>()
                .eqIfPresent(DictItemDO::getStatus, status)
                .eqIfPresent(DictItemDO::getDictCode, dictType));
    }

    List<DictItemDO> selectDictItemList();
}