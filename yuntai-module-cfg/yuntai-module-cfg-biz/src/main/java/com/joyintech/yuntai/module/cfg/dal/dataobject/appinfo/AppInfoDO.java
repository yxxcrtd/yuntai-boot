package com.joyintech.yuntai.module.cfg.dal.dataobject.appinfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 多应用 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_app_info")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppInfoDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 应用编码
     */
    private String appCode;
    /**
     * 应用地址
     */
    private String appAddress;
    /**
     * 应用容器
     */
    private String container;
    /**
     * 状态(0->开启1>停用)
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}