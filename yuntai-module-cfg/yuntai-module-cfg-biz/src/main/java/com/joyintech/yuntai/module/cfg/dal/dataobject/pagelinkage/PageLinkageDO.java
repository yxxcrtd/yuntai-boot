package com.joyintech.yuntai.module.cfg.dal.dataobject.pagelinkage;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 页面联动配置 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_linkage")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageLinkageDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 页面ID
     */
    private Long pageConfigId;
    /**
     * 配置名称
     */
    private String linkageName;
    /**
     * 配置类型1->初始化;2->运行时;
     */
    private String linkageType;
    /**
     * 配置图标
     */
    private String icon;
    /**
     * 配置方式1->配置;2->脚本;
     */
    private String linkageWay;
    /**
     * 配置内容
     */
    private String linkageContent;

    /**
     * 复制数据的id
     */
    private Long oldId;

}