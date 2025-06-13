package com.joyintech.yuntai.module.cfg.dal.dataobject.processnodepermissions;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 流程节点操作权限配置 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_process_node_permissions")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessNodePermissionsDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 节点id
     */
    private String nodeId;
    /**
     * 操作类型:refuse-拒绝,transfer-转交,minusMulti-减签,delegate-委派,complete-同意,back-退回,addMulti-加签
     */
    private String operation;
    /**
     * 操作是否开启
     */
    private Boolean operationValue;

}
