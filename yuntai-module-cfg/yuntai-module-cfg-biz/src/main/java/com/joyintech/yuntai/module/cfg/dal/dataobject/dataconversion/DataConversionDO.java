package com.joyintech.yuntai.module.cfg.dal.dataobject.dataconversion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 数据转换 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_data_conversion")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataConversionDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 列表配置页ID
     */
    private Long listConfigId;
    /**
     * 数据转换类型
     */
    private String dataType;
    /**
     * 数据转换内容
     */
    private String dataContent;
    /**
     * 数据转换正则表达式
     */
    private String dataRegular;
    /**
     * 数据转换脚本
     */
    private String dataScript;

    /**
     * 复制数据的id
     */
    private Long oldId;
}