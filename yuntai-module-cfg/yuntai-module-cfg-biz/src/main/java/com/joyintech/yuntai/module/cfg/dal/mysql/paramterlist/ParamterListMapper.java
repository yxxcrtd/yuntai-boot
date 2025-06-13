package com.joyintech.yuntai.module.cfg.dal.mysql.paramterlist;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.paramterlist.ParamterListDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.paramterlist.vo.*;

/**
 * 页面路由参数 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ParamterListMapper extends BaseMapperX<ParamterListDO> {

    default PageResult<ParamterListDO> selectPage(ParamterListPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ParamterListDO>()
                .eqIfPresent(ParamterListDO::getFieldId, reqVO.getFieldId())
                .likeIfPresent(ParamterListDO::getParamName, reqVO.getParamName())
                .eqIfPresent(ParamterListDO::getParamField, reqVO.getParamField())
                .eqIfPresent(ParamterListDO::getIsRequire, reqVO.getIsRequire())
                .eqIfPresent(ParamterListDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ParamterListDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ParamterListDO::getId));
    }

}