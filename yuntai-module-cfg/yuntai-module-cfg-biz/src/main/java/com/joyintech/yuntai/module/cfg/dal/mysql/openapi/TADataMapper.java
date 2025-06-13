package com.joyintech.yuntai.module.cfg.dal.mysql.openapi;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.openapi.dto.TADataBeftInfoResVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.TADataInvPropertyResVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
@DS("slave")
public interface TADataMapper extends BaseMapperX<TADataInvPropertyResVO> {

    List<TADataInvPropertyResVO> getTADataInvProperty(@Param("projCode") String fundCode);

    List<TADataBeftInfoResVO> getTADataBeftInfo(@Param("projCode") String fundCode);
}
