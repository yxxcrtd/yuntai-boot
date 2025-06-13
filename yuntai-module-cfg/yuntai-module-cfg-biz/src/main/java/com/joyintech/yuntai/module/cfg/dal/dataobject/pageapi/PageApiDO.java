package com.joyintech.yuntai.module.cfg.dal.dataobject.pageapi;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 页面api DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_api")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageApiDO extends BaseDO {

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
     * apiID
     */
    private Long apiId;
    /**
     * 数据模型
     */
    private Long moduleId;
    /**
     * 服务ID
     */
    private Long serverId;
    /**
     * api编码
     */
    private String apiCode;
    /**
     * api名称
     */
    private String apiName;

    /**
     * 复制数据的id
     */
    private Long oldId;
}