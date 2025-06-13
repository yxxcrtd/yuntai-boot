package com.joyintech.yuntai.module.cfg.dal.mysql.templategroup;

import java.util.*;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templategroup.TemplateGroupDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.templategroup.vo.*;

/**
 * 模版分组 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface TemplateGroupMapper extends BaseMapperX<TemplateGroupDO> {

    default PageResult<TemplateGroupDO> selectPage(TemplateGroupPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TemplateGroupDO>()
                .likeIfPresent(TemplateGroupDO::getGroupName, reqVO.getGroupName())
                .eqIfPresent(TemplateGroupDO::getNumSort, reqVO.getNumSort())
                .eqIfPresent(TemplateGroupDO::getParentId, reqVO.getParentId())
                .betweenIfPresent(TemplateGroupDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateGroupDO::getId));
    }

}