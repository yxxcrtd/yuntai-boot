package com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 外部系统关联 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_out_system_table")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutSystemTableDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 页面ID
     */
    private Long pageId;
    /**
     * 流程ID
     */
    private String flowId;
    /**
     * 实例ID
     */
    private String flowInstanceId;
    /**
     * 类型
     */
    private String flowType;
    /**
     * 业务数据ID
     */
    private String businessId;
    /**
     * 表单系统ID
     */
    private String pageDataId;

    /**
     * 流程状态 0:成功 1：失败 3：再次失败 4：再次发起
     */
    private Integer resultStatus;

}