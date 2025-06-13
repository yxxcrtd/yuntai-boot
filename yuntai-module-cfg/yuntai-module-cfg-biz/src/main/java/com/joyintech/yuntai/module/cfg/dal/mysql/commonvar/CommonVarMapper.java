package com.joyintech.yuntai.module.cfg.dal.mysql.commonvar;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.commonvar.CommonVarDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.commonvar.vo.*;

/**
 * 公共变量 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface CommonVarMapper extends BaseMapperX<CommonVarDO> {

    default PageResult<CommonVarDO> selectPage(CommonVarPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CommonVarDO>()
                .eqIfPresent(CommonVarDO::getType, reqVO.getType())
                .orderByDesc(CommonVarDO::getId));
    }

}