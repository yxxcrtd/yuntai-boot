package com.joyintech.yuntai.module.cfg.dal.mysql.modulefield;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleFieldSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleFieldVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.TableField;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulefield.ModuleFieldDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模型涉及字段 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ModuleFieldMapper extends BaseMapperX<ModuleFieldDO> {

    /**
     * 物理删除
     * @param moduleId
     */
    void deleteByModuleId(@Param("moduleId") Long moduleId);

    /**
     * 物理删除 field
     * @param columnIds 列ids
     */
    void deleteByColumnIds(@Param("columnIds") List<Long> columnIds);

    /**
     * 查询表字段
     * @param moduleId
     * @param mappingModuleId
     * @return
     */
    List<TableField> selectByModuleId(@Param("moduleId") Long moduleId,@Param("mappingModuleId") Long mappingModuleId);

    /**
     * 查询表字段
     * @param moduleId
     * @return
     */
    List<ModuleFieldSaveReqVO> selectField(@Param("moduleId") Long moduleId);

    List<ModuleFieldVO> findFieldByModuleId(@Param("moduleId") Long moduleId, @Param("moduleTableId") List<Long> moduleTableId);
}
