package com.joyintech.yuntai.module.cfg.dal.mysql.lowcode;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joyintech.yuntai.framework.common.pojo.LowCodeParam;
import com.joyintech.yuntai.framework.common.pojo.QueryField;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 低码通用 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface LowCodeMapper extends BaseMapperX<Map<String,Object>> {

    /**
     * 低码通用分页查询
     * @param page
     * @param sql
     * @return
     */
    List<Map<String,Object>> getPage(IPage<LowCodeParam> page, @Param("searchParams") List<QueryField> searchParams, @Param("sql") String sql);

}
