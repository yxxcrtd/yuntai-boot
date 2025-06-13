package com.joyintech.yuntai.module.cfg.dal.mysql.templateinfo;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templateinfo.TemplateInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.templateinfo.vo.*;

/**
 * 模版 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface TemplateInfoMapper extends BaseMapperX<TemplateInfoDO> {

    default PageResult<TemplateInfoDO> selectPage(TemplateInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TemplateInfoDO>()
                .eqIfPresent(TemplateInfoDO::getGroupId, reqVO.getGroupId())
                .inIfPresent(TemplateInfoDO::getGroupId, reqVO.getGroupIds())
                .eqIfPresent(TemplateInfoDO::getTemplateCode, reqVO.getTemplateCode())
                .eqIfPresent(TemplateInfoDO::getTemplateType, reqVO.getTemplateType())
                .likeIfPresent(TemplateInfoDO::getTemplateName, reqVO.getTemplateName())
                .eqIfPresent(TemplateInfoDO::getTemplateAddress, reqVO.getTemplateAddress())
                .eqIfPresent(TemplateInfoDO::getTemplateImage, reqVO.getTemplateImage())
                .betweenIfPresent(TemplateInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateInfoDO::getId));
    }

}
