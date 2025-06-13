package com.joyintech.yuntai.module.cfg.dal.mysql.modulerelationfield;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleRelationFieldCache;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleRelationField;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulerelationfield.ModuleRelationFieldDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模型关联字段 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ModuleRelationFieldMapper extends BaseMapperX<ModuleRelationFieldDO> {

    /**
     * 物理删除
     * @param moduleId
     */
    void deleteByModuleId(@Param("moduleId") Long moduleId);

    /**
     * 根据moduleId查询模型关联字段
     * @param moduleId
     * @return
     */
    List<ModuleRelationField> selectByModuleId(@Param("moduleId") Long moduleId);

    /**
     * 查询所有模型关联字段
     * @return
     */
    List<ModuleRelationFieldCache> selectRelationFieldCache(@Param("moduleId") Long moduleId);
}
