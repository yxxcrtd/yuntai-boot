package com.joyintech.yuntai.module.cfg.dal.dataobject.moduleapiparam;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 模型参数 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_module_api_param")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleApiParamDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * api的id
     */
    private Long apiId;
    /**
     * 表字段id
     */
    private Long fieldId;

    /**
     * 校验规则
     */
    private String validRule;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * cfg_module_id 的表id
     */
    private Long moduleTableId;

    /**
     * 参数类型：request/response
     */
    private String paramType;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段备注
     */
    private String fieldComment;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 复制数据的id
     */
    private Long oldId;
}
