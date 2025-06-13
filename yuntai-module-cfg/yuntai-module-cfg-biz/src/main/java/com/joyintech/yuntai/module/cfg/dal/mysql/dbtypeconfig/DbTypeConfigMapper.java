package com.joyintech.yuntai.module.cfg.dal.mysql.dbtypeconfig;


import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbtypeconfig.DbTypeConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据库字段映射表,默认从mysql映射到其他数据库 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface DbTypeConfigMapper extends BaseMapperX<DbTypeConfigDO> {

    /**
     * 查询数据库字段映射表
     * @param typeSource
     * @return
     */
    List<DbTypeConfigDO> selectListByTypeSource(@Param("typeSource") String typeSource);
}
