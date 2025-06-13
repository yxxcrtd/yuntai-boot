package com.joyintech.yuntai.module.system.dal.mysql.dictnew;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.joyintech.yuntai.module.system.controller.admin.dict.vo.type.DictTypePageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.dict.vo.type.DictTypeRespVO;
import com.joyintech.yuntai.module.system.controller.admin.dictnew.vo.DictPageReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dict.DictTypeDO;
import com.joyintech.yuntai.module.system.dal.dataobject.dictnew.DictDO;

/**
 * 字典主表 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface DictMapper extends BaseMapperX<DictDO> {

    default PageResult<DictDO> selectPage(DictPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DictDO>()
                .eqIfPresent(DictDO::getSystemType, reqVO.getSystemType())
                .eqIfPresent(DictDO::getDictGroup, reqVO.getDictGroup())
                .likeIfPresent(DictDO::getDictName, reqVO.getDictName())
                .eqIfPresent(DictDO::getDictCode, reqVO.getDictCode())
                .eqIfPresent(DictDO::getCreateBy, reqVO.getCreateBy())
                .eqIfPresent(DictDO::getUpdateBy, reqVO.getUpdateBy())
                .eqIfPresent(DictDO::getType, reqVO.getType())
                .eqIfPresent(DictDO::getBankType, reqVO.getBankType())
                .eqIfPresent(DictDO::getSortIndex, reqVO.getSortIndex())
                .eqIfPresent(DictDO::getEditState, reqVO.getEditState())
                .eqIfPresent(DictDO::getDescription, reqVO.getDescription())
                .eqIfPresent(DictDO::getDelFlag, reqVO.getDelFlag())
                .eqIfPresent(DictDO::getDataType, reqVO.getDataType())
                .eqIfPresent(DictDO::getDictClassify, reqVO.getDictClassify())
                .eqIfPresent(DictDO::getDictType, reqVO.getDictType())
                .orderByDesc(DictDO::getId));
    }

    IPage<DictTypeDO> selectPageUnion(Page<DictTypeDO> page, @Param("reqVO") DictTypePageReqVO pageReqVO);

    List<DictTypeDO> selectListNew(@Param("dictType") List<String> dictType);

    DictTypeDO selectOne(@Param("dictTypeRespVO") DictTypeRespVO dictTypeRespVO);
}