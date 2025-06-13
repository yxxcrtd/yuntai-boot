package com.joyintech.yuntai.module.cfg.dal.dataobject.processnodeevent;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 流程节点事件监听 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_process_node_event")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessNodeEventDO extends BaseDO {

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
     * 事件类型
     */
    private String event;
    /**
     * 创建/指派/完成/删除
     */
    private String implementationType;
    /**
     * 监听器
     */
    private String implementation;

}
