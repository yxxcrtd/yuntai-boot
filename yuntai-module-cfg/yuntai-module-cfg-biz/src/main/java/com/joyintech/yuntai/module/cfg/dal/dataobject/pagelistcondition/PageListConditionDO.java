package com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistcondition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 表单页查询条件（待定） DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_list_condition")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageListConditionDO extends BaseDO {

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
     * apiID
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
     * 列字段别名
     */
    private String columnNameAlias;
    /**
     * 查询操作符
     */
    private String columnQueryOperator;
    /**
     * 显示组件
     */
    private String columnDisplayComponent;
    /**
     * 显示组件
     */
    private String columnDisplayComponentName;
    /**
     * 数据库字典
     */
    private String columnDictType;
    /**
     * 字段排序
     */
    private String sort;
    /**
     * 默认值
     */
    private String columnDefault;
    /**
     * 是否查询列
     */
    private String isQueryColumn;
    /**
     * 是否必填
     */
    private String isColumnRequire;
    /**
     * 插槽
     */
    private String columnSlot;

    /**
     * jdbc类型
     */
    @TableField(exist = false)
    private String jdbcType;

    /**
     * 复制数据的id
     */
    private Long oldId;
}
