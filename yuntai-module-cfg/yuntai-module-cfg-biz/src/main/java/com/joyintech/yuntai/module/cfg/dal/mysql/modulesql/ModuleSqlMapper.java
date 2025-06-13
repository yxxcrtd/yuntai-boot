package com.joyintech.yuntai.module.cfg.dal.mysql.modulesql;


import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleSqlCache;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulesql.ModuleSqlDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulesql.ModuleTableSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模型新建后生成的SQL Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ModuleSqlMapper extends BaseMapperX<ModuleSqlDO> {
    /**
     * 物理删除
     * @param moduleId
     */
    void deleteByModuleId(@Param("moduleId") Long moduleId);

    /**
     * 查询模型对应的sql
     * @param serviceId
     * @param actionTypes
     * @return
     */
    List<ModuleTableSql> selectByModuleId(@Param("serviceId") Long serviceId, @Param("actionTypes") List<String> actionTypes);

    /**
     * 查询模型对应的sql
     * @return
     */
    List<ModuleSqlCache> selectSqlCache(@Param("moduleId") Long moduleId);
}
