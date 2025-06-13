package com.joyintech.yuntai.module.cfg.dal.dataobject.processdata;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 流程Log日志 DO
 *
 * @author 兆尹云台
 */
@TableName("log_process_data")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDataDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 页面id
     */
    private Long pageId;
    /**
     * 流程id
     */
    private String flowId;
    /**
     * 流程实例
     */
    private String flowRequestId;
    /**
     * 流程节点id
     */
    private String nodeId;
    /**
     * 数据id
     */
    private String formId;
    /**
     * 数据明细
     */
    private String formData;

}