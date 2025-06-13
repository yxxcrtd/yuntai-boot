package com.joyintech.yuntai.module.cfg.dal.dataobject.commonmodel;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 公共模型 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_common_model")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonModelDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 模型编码
     */
    private String modelCode;
    /**
     * 模型名称
     */
    private String modelName;
    /**
     * 备注
     */
    private String remark;

}