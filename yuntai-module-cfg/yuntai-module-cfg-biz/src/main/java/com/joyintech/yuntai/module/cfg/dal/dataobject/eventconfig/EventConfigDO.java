package com.joyintech.yuntai.module.cfg.dal.dataobject.eventconfig;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 页面事件配置 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_event_config")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventConfigDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 页面ID
     */
    private Long pageConfigId;
    /**
     * 事件名称
     */
    private String eventName;
    /**
     * 事件类型
     */
    private String eventType;
    /**
     * 配置图标
     */
    private String icon;
    /**
     * 事件内容
     */
    private String script;

    /**
     * 复制数据的id
     */
    private Long oldId;
}