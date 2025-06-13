package com.joyintech.yuntai.module.cfg.dal.dataobject.paramterlist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 页面路由参数 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_paramter_list")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParamterListDO extends BaseDO {

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
     * 关联字段ID
     */
    private Long fieldId;
    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 参数字段
     */
    private String paramField;
    /**
     * 是否必填
     */
    private Integer isRequire;
    /**
     * 备注
     */
    private String remark;

    /**
     * 复制数据的id
     */
    private Long oldId;
}