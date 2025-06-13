package com.joyintech.yuntai.module.cfg.dal.dataobject.commonvar;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 公共变量 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_common_var")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonVarDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段描述
     */
    private String fieldDescribe;
    /**
     * 缓存类型
     */
    private String cacheType;
    /**
     * 前端/后端类型
     */
    private String type;

}