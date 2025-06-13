package com.joyintech.yuntai.module.cfg.dal.dataobject.templateinfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 模版 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_template_info")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateInfoDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 模版分组ID
     */
    private Long groupId;
    /**
     * 模版code
     */
    private String templateCode;
    /**
     * 模版名称
     */
    private String templateName;
    /**
     * 模版地址
     */
    private String templateAddress;
    /**
     * 模版图片
     */
    private String templateImage;
    /**
     * 模版类型
     */
    private String templateType;

}
