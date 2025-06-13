package com.joyintech.yuntai.module.cfg.dal.sqlgen;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 执行共通ddl
 *
 * @author abator 2024/9/26
 */
@Mapper
public interface CommonDdlMapper {
    int executeSql(@Param("sql") String sql);
}
