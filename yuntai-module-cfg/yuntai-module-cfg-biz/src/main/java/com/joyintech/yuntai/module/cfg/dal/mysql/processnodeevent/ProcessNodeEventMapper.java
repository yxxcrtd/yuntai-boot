package com.joyintech.yuntai.module.cfg.dal.mysql.processnodeevent;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnodeevent.ProcessNodeEventDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程节点事件监听 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ProcessNodeEventMapper extends BaseMapperX<ProcessNodeEventDO> {
    /**
     * 物理删除记录
     * @param processId
     * @return
     */
    int deletePhysical(@Param("processId") Long processId);

}
