package com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfig;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfighttp.DataSetConfigHttpDO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 数据集管理 DO
 *
 * @author 兆尹云台
 */
@TableName("infra_data_set_config")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataSetConfigDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 数据集类型
     */
    private String type;
    /**
     * 数据集名称
     */
    private String name;
    /**
     * 数据集编码
     */
    private String code;
    /**
     * 数据源
     */
    private Long sourceCode;
    /**
     * 描述
     */
    private String remark;
    /**
     * JSON数据
     */
    private String jsonData;
    /**
     * SQL语句
     */
    private String sqlData;

    /**
     * 调用方式
     */
    private String callMethod;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求地址
     */
    private String requestMethods;

    /**
     * JSON数据、SQL语句
     */
    @TableField(exist = false)
    private List<Map> data;

    @TableField(exist = false)
    private List<DataSetConfigHttpDO> headerList;

    @TableField(exist = false)
    private List<DataSetConfigHttpDO> configList;
}