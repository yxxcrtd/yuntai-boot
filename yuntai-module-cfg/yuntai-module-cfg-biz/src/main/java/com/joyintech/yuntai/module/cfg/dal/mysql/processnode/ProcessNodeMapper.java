package com.joyintech.yuntai.module.cfg.dal.mysql.processnode;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnode.ProcessNodeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程设计节点 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ProcessNodeMapper extends BaseMapperX<ProcessNodeDO> {

    /**
     * 物理删除记录
     * @param processId
     * @return
     */
    int deletePhysical(@Param("processId") Long processId);

}
