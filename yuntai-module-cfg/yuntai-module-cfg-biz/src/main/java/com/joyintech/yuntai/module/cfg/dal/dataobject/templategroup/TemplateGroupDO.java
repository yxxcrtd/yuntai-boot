package com.joyintech.yuntai.module.cfg.dal.dataobject.templategroup;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 模版分组 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_template_group")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateGroupDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 排序
     */
    private Integer numSort;
    /**
     * 父级ID
     */
    private Long parentId;

    @TableField(exist = false)
    private List<TemplateGroupDO> children;

}
