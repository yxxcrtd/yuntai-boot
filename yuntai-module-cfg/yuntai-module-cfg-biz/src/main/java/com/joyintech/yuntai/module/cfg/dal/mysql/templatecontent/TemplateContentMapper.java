package com.joyintech.yuntai.module.cfg.dal.mysql.templatecontent;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templatecontent.TemplateContentDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.templatecontent.vo.*;

/**
 * 模版内容 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface TemplateContentMapper extends BaseMapperX<TemplateContentDO> {

    default PageResult<TemplateContentDO> selectPage(TemplateContentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TemplateContentDO>()
                .eqIfPresent(TemplateContentDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(TemplateContentDO::getType, reqVO.getType())
                .eqIfPresent(TemplateContentDO::getApiCode, reqVO.getApiCode())
                .likeIfPresent(TemplateContentDO::getApiName, reqVO.getApiName())
                .eqIfPresent(TemplateContentDO::getApiType, reqVO.getApiType())
                .likeIfPresent(TemplateContentDO::getParameterName, reqVO.getParameterName())
                .eqIfPresent(TemplateContentDO::getParameterCode, reqVO.getParameterCode())
                .eqIfPresent(TemplateContentDO::getParameterType, reqVO.getParameterType())
                .eqIfPresent(TemplateContentDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TemplateContentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateContentDO::getId));
    }

}