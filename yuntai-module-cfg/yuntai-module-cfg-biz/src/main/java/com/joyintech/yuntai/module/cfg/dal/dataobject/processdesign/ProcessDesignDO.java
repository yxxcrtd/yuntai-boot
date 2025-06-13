package com.joyintech.yuntai.module.cfg.dal.dataobject.processdesign;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 流程设计 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_process_design")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDesignDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 流程名称
     */
    private String processName;
    /**
     * 关联表单
     */
    private Long pageId;
    /**
     * 流程类型(内外)
     */
    private String processType;
    /**
     * 备注
     */
    private String remark;
    /**
     * 流程ID
     */
    private String flowId;
}
