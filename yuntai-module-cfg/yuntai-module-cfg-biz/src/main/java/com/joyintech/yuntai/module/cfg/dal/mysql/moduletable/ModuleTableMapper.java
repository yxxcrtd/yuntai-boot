package com.joyintech.yuntai.module.cfg.dal.mysql.moduletable;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleTableRelation;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleTableSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleTableVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduletable.ModuleTableDO;
import com.joyintech.yuntai.module.cfg.openapi.dto.ViewAssetMemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 模型关联 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ModuleTableMapper extends BaseMapperX<ModuleTableDO> {

    /**
     * 检索 配置条件参数
     */
    List<String> listParameterByModuleId(@Param("moduleId") Long moduleId);
    /**
     * 物理删除
     * @param moduleId
     */
    void deleteByModuleId(@Param("moduleId") Long moduleId);

    /**
     * 根据模型查表
     * @param moduleId
     * @return
     */
    List<ModuleTableRelation> selectByModuleId(@Param("moduleId") Long moduleId);


    /**
     * 根据模型查表
     * @param moduleId
     * @return
     */
    List<ModuleTableSaveReqVO> selectTable(@Param("moduleId") Collection<Long> moduleId);

    List<ModuleTableVO> findTableByModuleId(@Param("moduleId") Long moduleId);

    ViewAssetMemberVO findViewAssetMember(@Param("ywbm") String ywbm);
}



