package com.joyintech.yuntai.module.cfg.dal.dataobject.buttonaction;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 页面按钮动作 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_button_action")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ButtonActionDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 页面按钮ID
     */
    private Long pageButtonId;
    /**
     * 页面ID
     */
    private Long pageId;
    /**
     * 动作类型
     */
    private String actionType;
    /**
     * 关联服务
     */
    private String modelServerId;
    /**
     * 打开方式
     */
    private String openWay;
    /**
     * 关联页面
     */
    private String relevancePage;
    /**
     * 页面参数
     */
    private String serverParams;
    /**
     * 脚本
     */
    private String dataScript;
    /**
     * 自定义方法
     */
    private String customMethod;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 关联页面类型
     */
    private String pageType;

    /**
     * 跳转地址
     */
    private String relevanceUrl;

    /**
     *新增方式
     */
    private String newAddType;
}