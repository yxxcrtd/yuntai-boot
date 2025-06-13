package com.joyintech.yuntai.module.cfg.dal.mysql.moduleapiparam;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleApiParamVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleapiparam.ModuleApiParamDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模型参数 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ModuleApiParamMapper extends BaseMapperX<ModuleApiParamDO> {


    /**
     * 物理删除
     * @param moduleId
     */
    void deleteByModuleId(@Param("moduleId") Long moduleId);


    /**
     * 根据apiId查询模型参数
     * @param apiId
     * @return
     */
    List<ModuleApiParamVO> selectByApiId(@Param("apiId") Long apiId);

    /**
     * 根据模型id查询模型参数
     * @param moduleId
     * @return
     */
    List<ModuleApiParamDO> selectByModuleId(@Param("moduleId") Long moduleId, @Param("serviceCode") String serviceCode);

}
