package com.joyintech.yuntai.module.cfg.dal.dataobject.pageparameter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 页面参数 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_parameter")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageParameterDO extends BaseDO {

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
     * 页面apiID
     */
    private Long pageApiId;
    /**
     * 模版ID
     */
    private Long templateId;
    /**
     * 参数名称
     */
    private String parameterName;
    /**
     * 参数值
     */
    private String parameterValue;
    /**
     * 参数编码
     */
    private String parameterCode;
    /**
     * 参数类型
     */
    private String parameterType;

    /**
     * 复制数据的id
     */
    private Long oldId;
}