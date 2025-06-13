package com.joyintech.yuntai.module.cfg.dal.mysql.moduleinfo;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleInfoPageReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleinfo.ModuleInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 模型信息 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ModuleInfoMapper extends BaseMapperX<ModuleInfoDO> {

    default PageResult<ModuleInfoDO> selectPage(ModuleInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ModuleInfoDO>()
                .likeIfPresent(ModuleInfoDO::getModuleName, reqVO.getModuleName())
                .eqIfPresent(ModuleInfoDO::getModuleCode, reqVO.getModuleCode())
                .eqIfPresent(ModuleInfoDO::getModuleType, reqVO.getModuleType())
                .eqIfPresent(ModuleInfoDO::getModuleSql, reqVO.getModuleSql())
                .eqIfPresent(ModuleInfoDO::getModuleBean, reqVO.getModuleBean())
                .eqIfPresent(ModuleInfoDO::getModuleMethod, reqVO.getModuleMethod())
                .eqIfPresent(ModuleInfoDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ModuleInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ModuleInfoDO::getMenuId, reqVO.getMenuId())
                .orderByDesc(ModuleInfoDO::getId));
    }

    /**
     * 物理删除
     * @param moduleId
     */
    void deleteByModuleId(@Param("moduleId") Long moduleId);

}
