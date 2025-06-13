package com.joyintech.yuntai.module.cfg.pageinfo.dto;

import lombok.*;

/**
 * 页面基本信息 DO
 *
 * @author 兆尹云台
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoDTO {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 页面名称
     */
    private String pageName;
    /**
     * 页面编码
     */
    private String pageCode;
    /**
     * 页面类型;1:列表 2-表单
     */
    private String pageType;
    /**
     * 数据模型
     */
    private Long moduleId;
    /**
     * 服务ID
     */
    private Long serverId;
    /**
     * 页面风格
     */
    private String pageStyle;
    /**
     * 页面状态
     */
    private String pageState;
    /**
     * 默认查询
     */
    private String defaultQuery;
    /**
     * 页面模板
     */
    private String pageTemplate;
    /**
     * 页面模版下服务ID
     */
    private String treeServerId;
    /**
     *  页面模版下数据模型
     */
    private String treeModuleId;
    /**
     *  子表格
     */
    private String subTable;
    /**
     *  子表格下数据模型
     */
    private String tableModuleId;
    /**
     *  子表格下服务ID
     */
    private String tableServerId;
    /**
     * 父页面套壳
     */
    private String parentPage;
    /**
     * 内部插槽
     */
    private String innerSlot;
    /**
     * 页头插槽
     */
    private String headerSlot;
    /**
     * 中间插槽
     */
    private String middleSlot;
    /**
     * 尾部插槽
     */
    private String tailSlot;
    /**
     * 备注
     */
    private String remark;

    /**
     * 页面版本
     */
    private String pageVersion;
}
