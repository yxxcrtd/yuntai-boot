package com.joyintech.yuntai.module.cfg.dal.dataobject.pagebutton;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 页面操作按钮 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_button")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageButtonDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 按钮类型
     */
    private String buttonType;
    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 按钮名称
     */
    private String buttonName;
    /**
     * 列表页ID
     */
    private Long pageId;
    /**
     * 页面apiID
     */
    private Long pageApiId;
    /**
     * 关联服务ID
     */
    private Long modelServerId;
    /**
     * api编码
     */
    private String apiCode;
    /**
     * 服务参数编码
     */
    private String serverParamsCode;
    /**
     * 按钮样式
     */
    private String buttonStyle;
    /**
     * 按钮图标
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String buttonIcon;
    /**
     * 打开方式
     */
    private String openWay;
    /**
     * 关联页面
     */
    private String relevancePage;
    /**
     * 自定义方法
     */
    private String customMethod;
    /**
     * 备注
     */
    private String remark;
    /**
     * 按钮权限标识
     */
    private String permissionSign;
    /**
     * 按钮类型
     */
    private String buttonShape;

    /**
     * 按钮默认参数
     */
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private String actionDefaultParams;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 子表id
     */
    private Long moduleTableId;

    /**
     * 显示组件
     */
    private String columnDisplayComponent;

    /**
     * 按钮位置
     */
    private String btnPosition;

}
