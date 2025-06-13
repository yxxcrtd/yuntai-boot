package com.joyintech.yuntai.module.cfg.dal.mysql.processnodecondition;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnodecondition.ProcessNodeConditionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程节点条件配置 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ProcessNodeConditionMapper extends BaseMapperX<ProcessNodeConditionDO> {

    /**
     * 物理删除记录
     * @param processId
     * @return
     */
    int deletePhysical(@Param("processId") Long processId);

}
