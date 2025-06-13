package com.joyintech.yuntai.module.cfg.dal.mysql.processnodepermissions;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnodepermissions.ProcessNodePermissionsDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.processnodepermissions.ProcessNodePermissionsMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程节点操作权限配置 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ProcessNodePermissionsMapper extends BaseMapperX<ProcessNodePermissionsDO> {

    /**
     * 物理删除记录
     * @param processId
     * @return
     */
    int deletePhysical(@Param("processId") Long processId);
}
