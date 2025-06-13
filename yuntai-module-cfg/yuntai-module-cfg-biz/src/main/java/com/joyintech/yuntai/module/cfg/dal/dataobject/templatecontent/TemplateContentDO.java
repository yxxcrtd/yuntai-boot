package com.joyintech.yuntai.module.cfg.dal.dataobject.templatecontent;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 模版内容 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_template_content")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateContentDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 模版ID
     */
    private Long templateId;
    /**
     * 类型
     */
    private Integer type;
    /**
     * api编码
     */
    private String apiCode;
    /**
     * api名称
     */
    private String apiName;
    /**
     * api类型
     */
    private String apiType;
    /**
     * 参数名称
     */
    private String parameterName;
    /**
     * 参数编码
     */
    private String parameterCode;
    /**
     * 参数类型
     */
    private String parameterType;
    /**
     * 备注
     */
    private String remark;

}