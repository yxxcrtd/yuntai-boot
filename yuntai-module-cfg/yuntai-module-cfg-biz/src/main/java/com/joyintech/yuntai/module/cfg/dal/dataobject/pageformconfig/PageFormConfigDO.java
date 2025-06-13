package com.joyintech.yuntai.module.cfg.dal.dataobject.pageformconfig;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 表单页配置 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_form_config")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageFormConfigDO extends BaseDO {

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
     * 字段名
     */
    private String columnName;
    /**
     * 标题
     */
    private String columnTitle;
    /**
     * 所属模型
     */
    private String columnAlignment;
    /**
     * 显示组件
     */
    private String columnComponent;
    /**
     * 是否必填
     */
    private String isRequired;
    /**
     * 占位符
     */
    private String columnPlaceholder;
    /**
     * tips
     */
    private String columnTips;
    /**
     * 宽度
     */
    private String columnWidth;
    /**
     * 插槽
     */
    private String columnSlot;
    /**
     * 组件配置;暂定，若实际编码时需要可设计为独立表
     */
    private String componentConfig;
    /**
     * 校验规则;暂定，若实际编码时需要可设计为独立表
     */
    private String checkRuleConfig;
    /**
     * 联动配置;暂定，若实际编码时需要可设计为独立表
     */
    private String linkedConfig;
    /**
     * 事件配置;暂定，若实际编码时需要可设计为独立表
     */
    private String eventConfig;
    /**
     * 备注
     */
    private String remark;

}