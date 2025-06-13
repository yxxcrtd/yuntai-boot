package com.joyintech.yuntai.module.cfg.dal.mysql.processnodeformproperty;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnodeformproperty.ProcessNodeFormPropertyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程节点表单字段属性配置 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ProcessNodeFormPropertyMapper extends BaseMapperX<ProcessNodeFormPropertyDO> {


    /**
     * 物理删除记录
     * @param processId
     * @return
     */
    int deletePhysical(@Param("processId") Long processId);
}
