package com.joyintech.yuntai.module.cfg.dal.dataobject.conditionaltable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute.ColumnComponentAttributeDO;

/**
 * 条件 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_conditional_table")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConditionalTableDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 页面/节点关联ID
     */
    private Long relevanceId;
    /**
     * 条件类型(1->初始化；2->运行时)
     */
    private String type;
    /**
     * 设置类型
     */
    private String setType;
    /**
     * 脚本
     */
    private String script;
    /**
     * 条件内容
     */
    private String content;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 显示类型
     */
    private String showPageTypeCode;

    /**
     * 赋值为:
     */
    private String fillValue;

    /**
     *切换为
     */
    private String columnDisplayComponent;

    /**
     *切换为
     */
    @TableField(exist = false)
    private String columnDisplayComponentName;

    @TableField(exist = false)
    @Schema(description = "赋值为—组件属性")
    private List<ColumnComponentAttributeDO> columnComponentAttributeDO;
}