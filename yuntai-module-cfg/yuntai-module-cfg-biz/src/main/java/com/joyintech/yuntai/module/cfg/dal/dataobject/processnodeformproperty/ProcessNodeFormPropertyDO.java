package com.joyintech.yuntai.module.cfg.dal.dataobject.processnodeformproperty;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 流程节点表单字段属性配置 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_process_node_form_property")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessNodeFormPropertyDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 节点id
     */
    private String nodeId;
    /**
     * 节点id
     */
    private String fieldId;
    /**
     * 事件类型
     */
    private String name;
    /**
     * 是否只读
     */
    private Boolean readonly;
    /**
     * 是否隐藏
     */
    private Boolean hidden;
    /**
     * 是否必填
     */
    private Boolean required;

    private String fieldAlias;

}
