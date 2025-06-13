package com.joyintech.yuntai.module.system.sysuser.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.system.sysuser.model.SysDepart;

@Mapper
@DS("newsystem")
public interface SysDeptMapper extends BaseMapperX<SysDepart> {

}
