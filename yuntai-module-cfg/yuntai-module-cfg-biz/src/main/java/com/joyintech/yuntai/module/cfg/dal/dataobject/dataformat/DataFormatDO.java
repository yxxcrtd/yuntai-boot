package com.joyintech.yuntai.module.cfg.dal.dataobject.dataformat;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 页面数据格式化 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_data_format")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataFormatDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 列表配置页ID
     */
    private Long listConfigId;
    /**
     * 前缀
     */
    private String prefix;
    /**
     * 后缀
     */
    private String suffix;
    /**
     * 格式化类型
     */
    private String formatType;
    /**
     * 数字类型
     */
    private String numType;
    /**
     * 保留小数位数
     */
    private String keepDecimalPlaces;
    /**
     * 转换倍率
     */
    private String conversionRate;
    /**
     * 千分位符
     */
    private Boolean thousandth;
    /**
     * 日期格式
     */
    private String dateFormat;
    /**
     * 图片格式
     */
    private String pictureStyle;
    /**
     * 链接打开方式
     */
    private String linkOpenMethod;
    /**
     * 链接地址
     */
    private String linkAddress;
    /**
     * 正则表达式
     */
    private String regularExpression;
    /**
     * 组件名称
     */
    private String componentName;
    /**
     * 脚本
     */
    private String script;

    /**
     * 回显字段
     */
    private String transferLabelField;

    /**
     * 复制数据的id
     */
    private Long oldId;
}
