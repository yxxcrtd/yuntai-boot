package com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistconfig;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 列表页配置 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_list_config")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageListConfigDO extends BaseDO {

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
     * 页面apiID
     */
    private Long pageApiId;
    /**
     * 模型关联表ID
     */
    private Long moduleTableId;
    /**
     * 字段ID
     */
    private Long fieldId;
    /**
     * 数据库表ID
     */
    private Long tableId;
    /**
     * 列字段
     */
    private String columnName;
    /**
     * 列名称
     */
    private String columnComment;

    /**
     * 默认值
     */
    private String defValue;

    /**
     * 占据列数
     */
    private Long columnSpan;

    /**
     * 字段名称别名
     */
    private String columnNameAlias;

    /**
     * 字段别名
     */
    private String columnFieldAlias;

    /**
     * 所属分组 - 删除字段
     */
//    private Long columnGroupId;
    /**
     * 对齐方式
     */
    private String columnAlignment;
    /**
     * 固定宽度
     */
    private String columnFixedWidth;
    /**
     * 最小宽度
     */
    private String columnMinWidth;
    /**
     * 字段排序
     */
    private Integer sort;
    /**
     * 是否显示
     */
    private Integer isVisible;
    /**
     * 是否固定列
     */
    private String isColumnFixed;
    /**
     * 插槽
     */
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private String columnSlot;
    /**
     * 是否合计列
     */
    private Integer isTotalColumn;
    /**
     * 是否支持排序
     */
    private Integer isColumnSort;
    /**
     * 是否必填
     */
    private Integer isRequire;
    /**
     * 是否置灰
     */
    private Integer isDisabled;
    /**
     * 默认隐藏
     */
    private Integer isHidden;
    /**
     * 表头tips
     */
    private String columnHeadTips;
    /**
     * 表头插槽
     */
    private String columnHeadSlot;
    /**
     * 显示组件
     */
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private String columnDisplayComponent;
    /**
     * 显示组件
     */
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private String columnDisplayComponentName;
    /**
     * api编码
     */
    private String apiCode;
    /**
     * 分组编码
     */
    private String groupCode;
    /**
     * 子表ID
     */
    private Long childTableId;
    /**
     * 数据库字典
     */
    private String columnDictType;
    /**
     * 字段标签
     */
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private String columnTagCode;

    /**
     * 标签长度
     */
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private Integer columnHeadWidth;

    /**
     * 字段标签配置
     */
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private String columnTagConfig;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 移动端展示
     */
    private Integer isShowApp;

    /**
     * 列表内是否展示
     */
    private Integer isShowInTable;
}
