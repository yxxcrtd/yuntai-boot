package com.joyintech.yuntai.module.cfg.dal.dataobject.SubTableSetting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 子表设置 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_sub_table_setting")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubTableSettingDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 编辑模式
     */
    private String editMode;
    /**
     * 是否必填
     */
    private String isRequired;
    /**
     * 是否显示序号
     */
    private String isShowIndex;
    /**
     * 是否显示合计
     */
    private String isShowTotal;
    /**
     * 页面设置ID
     */
    private String pageId;
    /**
     * 页面apiID
     */
    private String pageApiId;
    /**
     * 表格id
     */
    private String tableId;

    /**
     * 子表标题
     */
    private String tableTitle;

    /**
     * 默认数据
     */
    private String defaultData;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 显示行号
     */
    private String tableSort;

    /**
     * 每页行号默认起始
     */
    private String tableBeginIndex;

    /**
     * 斑马纹
     */
    private String tableStripes;

    /**
     * 是否显示复选框
     */
    private String isShowCheckBox;

    /**
     * 固定操作列
     */
    private String tableFixedAction;

    /**
     * 操作列位置
     */
    private String tableActionPostion;

    /**
     * 是否支持分页
     */
    private String isPageList;

    /**
     * 默认分页大小
     */
    private String tableDefPageSize;

    private String defTableFieldName;

    @TableField("IS_ALLOW_ADD")
    private String isNotAllowAdd;

    /**
     * 是否隐藏操作列
     */
    private String isHiddenAction;
}