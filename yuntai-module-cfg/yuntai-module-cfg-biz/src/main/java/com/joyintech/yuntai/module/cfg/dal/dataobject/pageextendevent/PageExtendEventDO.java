package com.joyintech.yuntai.module.cfg.dal.dataobject.pageextendevent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 页面事件扩展配置 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_extend_event")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageExtendEventDO extends BaseDO {

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
     * 页面apiID
     */
    private Long pageApiId;
    /**
     * 服务id
     */
    private Long serviceId;
    /**
     * api编码
     */
    private String apiCode;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 接口地址
     */
    private String interfaceUrl;
    /**
     * 接口参数
     */
    private String interfaceParam;
    /**
     * 执行时间点
     */
    private String runTime;
    /**
     * 是否拦截主操作
     */
    private Boolean isIntercept;
    /**
     * 事件类型：1-前端,2-后端
     */
    private String eventType;
    /**
     * 调用类型1-调用方法,2-调用接口,3-执行代码
     */
    private String callType;
    /**
     * 接口
     */
    private String interfaceName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 代码内容
     */
    private String executableCode;

    /**
     * 复制数据的id
     */
    private Long oldId;
}
