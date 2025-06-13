package com.joyintech.yuntai.module.cfg.dal.mysql.moduleapi;

import java.util.*;
import java.util.List;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleCache;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleapi.ModuleApiDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleapi.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 模型API Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ModuleApiMapper extends BaseMapperX<ModuleApiDO> {

    default PageResult<ModuleApiDO> selectPage(ModuleApiPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ModuleApiDO>()
                .in (ModuleApiDO::getModuleId, reqVO.getModuleIdSet())
                .likeIfPresent(ModuleApiDO::getServiceName, reqVO.getServiceName())
                .eqIfPresent(ModuleApiDO::getServiceCode, reqVO.getServiceCode())
                .eqIfPresent(ModuleApiDO::getModuleId, reqVO.getModuleId())
                .eqIfPresent(ModuleApiDO::getServiceType, reqVO.getServiceType())
                .eqIfPresent(ModuleApiDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ModuleApiDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ModuleApiDO::getId));
    }

    /**
     * 物理删除
     * @param moduleId
     */
    void deleteByModuleId(@Param("moduleId") Long moduleId);

    /**
     * 查询所有模块缓存
     * @return
     */
    List<ModuleCache> selectModuleCache(@Param("moduleId") Long moduleId);

    /**
     * 查询所有的映射模块
     * @return
     */
    List<ModuleCache> selectMappingModuleCache(@Param("moduleId") Long moduleId);
}
