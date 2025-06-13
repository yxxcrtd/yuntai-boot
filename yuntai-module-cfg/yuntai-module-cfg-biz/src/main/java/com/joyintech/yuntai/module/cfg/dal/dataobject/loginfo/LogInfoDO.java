package com.joyintech.yuntai.module.cfg.dal.dataobject.loginfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 日志记录 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_log_info")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogInfoDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 表单ID
     */
    private Long formId;

    /**
     * 节点ID
     */
    private String nodeId;

    /**
     * 流程ID
     */
    private String workFlowId;
    /**
     * 实例ID
     */
    private String requestId;
    /**
     * 业务流程主键
     */
    private String serialNum;
    /**
     * 表单内容
     */
    private String content;

}