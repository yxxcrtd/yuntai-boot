package com.joyintech.yuntai.module.cfg.dal.dataobject.pageinfo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 页面基本信息 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_info")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
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
     * 外部JS
     */
    private String externalJsFile;
    /**
     * 备注
     */
    private String remark;

    /**
     * 支持附件
     */
    private String isSupportAttachment;

    /**
     * 表单排版
     */
    private String columnSpan;

    /**
     * 标签长度
     */
    private Long labelWidth;

    /**
     * 标签位置
     */
    private String labelPosition;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 页面版本
     */
    private String pageVersion;
}