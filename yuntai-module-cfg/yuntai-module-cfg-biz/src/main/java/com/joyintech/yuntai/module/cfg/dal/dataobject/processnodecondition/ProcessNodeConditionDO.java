package com.joyintech.yuntai.module.cfg.dal.dataobject.processnodecondition;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 流程节点条件配置 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_process_node_condition")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessNodeConditionDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Boolean id;
    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 父分组id
     */
    private Long parentGroupId;

    /**
     * 0-条件,1-分组
     */
    private String type;
    /**
     * 字段
     */
    private String field;
    /**
     * 操作符号
     */
    private String operator;
    /**
     * 字段值
     */
    private String value;

}
