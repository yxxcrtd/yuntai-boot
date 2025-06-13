package com.joyintech.yuntai.module.cfg.dal.dataobject.pagegroup;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 页面分组 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_group")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageGroupDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 列表页ID
     */
    private Long pageId;
    /**
     * 页面apiID
     */
    private Long pageApiId;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 分组编码
     */
    private String groupCode;
    /**
     * 分组排序
     */
    private String groupSort;

    /**
     * 分组槽位
     */
    private String groupSlot;

    /**
     * 是否隐藏
     */
    private Boolean isHidden;

    /**
     * 父分组ID
     */
    private String parentId;

    /**
     * 分组头部提示语
     */
    private String groupHeadTips;

    /**
     * 分组尾部提示语
     */
    private String groupFootTips;

    /**
     * 分组展示方式
     */
    private String groupDisplay;

    /**
     * 标题
     */
    private String groupTitle;
    /**
     * 提示文本
     */
    private String groupTips;
    /**
     * 链接地址
     */
    private String groupLinkAddress;
    /**
     * 链接名称
     */
    private String groupLinkName;

    /**
     * 链接类型
     */
    private String groupLinkType;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 尾部插槽
     */
    private String groupFailSlot;
}
